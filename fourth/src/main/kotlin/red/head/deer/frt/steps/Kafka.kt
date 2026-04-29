package red.head.deer.frt.steps

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging.logger
import red.head.deer.common.dto.KafkaRq
import red.head.deer.common.dto.KafkaRs
import red.head.deer.common.dto.Result
import red.head.deer.common.objects.Props
import red.head.deer.common.utils.*
import red.head.deer.frt.config.Config
import red.head.deer.frt.utils.*
import steps
import java.util.UUID

class Kafka(
    val config: Config,
) {
    var rqTopic = ""
    var rsTopic = ""
    var kafkaKey = ""
    var om = ObjectMapper()
    val log = logger("red.head.deer.frt.steps")

    private fun send(topic: String, body: String) {
        KafkaUtil().send(topic, kafkaKey, body)
    }

    // отправляет ответ в ответный топик кафки
    private fun mockResponse(success: Boolean) {
        val rsBody = when (success) {
            true -> om.writeValueAsString(KafkaRs(KafkaRs.Data("test@email.me", "+09811271827")))
            false -> """{"email": "test@email.me", "phone": "+09811271827"}"""
        }
        send(rsTopic, prettyJson(rsBody))
    }

    fun sendRequest(success: Boolean) {
        Props.kafkaBroker = "localhost:9092"
        rqTopic = "test.rq"
        rsTopic = "test.rs"
        kafkaKey = "${UUID.randomUUID()}"
        val rqBody = om.writeValueAsString(KafkaRq(KafkaRq.Data("${UUID.randomUUID()}")))
        send(rqTopic, prettyJson(rqBody))
        mockResponse(success)
        val response = KafkaUtil().read(rsTopic, kafkaKey)
        val rsBody = response.values.first()
        log.info("Response headers: ${response.keys.first()}")
        log.info("body: ${prettyJson(rsBody)}")
        try {
            om.readValue(rsBody, KafkaRs::class.java)
            if (!success) steps = steps.plus(Result.Step(
                "kafka error test",
                "FAIL",
                "Expected error, but it's success")
            ) else {
                steps = steps.plus(Result.Step(
                    "kafka success test",
                    "SUCCESS",
                    "It's OK")
                )
            }
        } catch (e: Exception) {
            if (success) steps = steps.plus(Result.Step(
                "kafka success test",
                "FAIL",
                "Got not valid response")
            ) else {
                steps = steps.plus(Result.Step(
                    "kafka error test",
                    "SUCCESS",
                    "It's OK")
                )
            }
        }

    }

}