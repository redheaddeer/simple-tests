package red.head.deer.trd.steps

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import red.head.deer.common.dto.KafkaRq
import red.head.deer.common.dto.KafkaRs
import red.head.deer.common.objects.Props
import red.head.deer.trd.util.KafkaUtil
import red.head.deer.trd.util.SystemUtil
import java.util.*
import mu.KLogging

class Kafka {

    var rqTopic = ""
    var rsTopic = ""
    var kafkaKey = ""
    var om = ObjectMapper()

    private fun send(topic: String, body: String) {
        KafkaUtil().send(topic, kafkaKey, body)
    }

    // отправляет ответ в ответный топик кафки
    private fun mockResponse(success: Boolean) {
        val rsBody = when (success) {
            true -> om.writeValueAsString(KafkaRs(KafkaRs.Data("test@email.me", "+09811271827")))
            false -> """{"email": "test@email.me", "phone": "+09811271827"}"""
        }
        send(rsTopic, SystemUtil().prettyJson(rsBody))
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun sendRequest(success: Boolean) {
        Props.kafkaBroker = "localhost:9092"
        rqTopic = "test.rq"
        rsTopic = "test.rs"
        kafkaKey = "${UUID.randomUUID()}"
        val rqBody = om.writeValueAsString(KafkaRq(KafkaRq.Data("${UUID.randomUUID()}")))
        send(rqTopic, SystemUtil().prettyJson(rqBody))
        mockResponse(success)
        val response = KafkaUtil().read(rsTopic, kafkaKey)
        val rsBody = response.values.first()
        KLogging().logger.info("Response headers: ${response.keys.first()}")
        KLogging().logger.info("body: ${SystemUtil().prettyJson(rsBody)}")
        try {
            om.readValue(rsBody, KafkaRs::class.java)
        } catch (e: Exception) {
            throw IllegalArgumentException("Parsing failed: ${e.message}")
        }

    }
}