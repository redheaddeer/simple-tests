package red.head.deer.snd.util

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.PartitionInfo
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.awaitility.Awaitility
import org.jetbrains.kotlin.konan.properties.Properties
import org.springframework.kafka.test.utils.KafkaTestUtils
import red.head.deer.common.objects.Props
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import kotlin.test.assertTrue
import java.time.Duration

class KafkaUtil {
    private lateinit var producer: KafkaProducer<String, String>
    val consumers: MutableMap<String, KafkaConsumer<String, String>> = mutableMapOf()

    fun send(rqTopic: String, kafkaKey: String, msgBody: String, headers: Map<String, String> = mapOf()) {
        producer = KafkaProducer<String, String>(
            Properties().apply {
                setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Props.kafkaBroker)
                setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
                setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
                // таймауты в миллисекундах, 10 секунд должно хватать, иначе считать недоступностью
                setProperty(ProducerConfig.SOCKET_CONNECTION_SETUP_TIMEOUT_MS_CONFIG, "10000")
                setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "10000")
                setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "10000")
                setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, "10000")
                setProperty(ProducerConfig.PARTITIONER_AVAILABILITY_TIMEOUT_MS_CONFIG, "10000")
            })
        val record = ProducerRecord(rqTopic, kafkaKey, msgBody)
        headers.forEach { (k, v) -> record.headers().add(k, v.toByteArray()) }
        producer.send(record).get(10, TimeUnit.SECONDS)
    }

    fun read(topic: String, key: String): HashMap<String, String> {
        var consumer = consumers[topic]
        if (consumer == null) {
            consumer = KafkaConsumer(
                KafkaTestUtils.consumerProps(
                    Props.kafkaBroker,
                    "red.head.deer",
                    "true"
                ),
                StringDeserializer(),
                StringDeserializer(),
            )
            val partitions = consumer.listTopics()
                .getOrDefault(topic, emptyList<PartitionInfo>())
                .stream()
                .map {
                    TopicPartition(it.topic(), it.partition())
                }.collect(Collectors.toList())
            check(!partitions.isNullOrEmpty()) { "partitions list is null" }
            consumer.assign(partitions)
        }

        var record: ConsumerRecord<String, String>? = null
        await {
            record = KafkaTestUtils.getRecords(consumer).firstOrNull {
                val found = it.key() == key
                if (!found) println("message with key ${it.key()} was skipped")
                found
            }
            record != null
        }
        return hashMapOf(
            Pair(
                record?.headers()?.joinToString(";") ?: "",
                record?.value() ?: "no body"
            )
        )
    }

    private fun await(condition: () -> Boolean) {
        try {
            Awaitility.await()
                .pollInterval(1, TimeUnit.SECONDS)
                .timeout(60, TimeUnit.SECONDS)
                .until(condition)
        } catch (_: Exception) {
            assertTrue(false, "Response not found")
        }
    }
}