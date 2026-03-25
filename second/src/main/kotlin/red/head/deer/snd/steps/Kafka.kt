package red.head.deer.snd.steps

import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.When
import red.head.deer.snd.dto.KafkaRq
import red.head.deer.snd.dto.KafkaRs
import red.head.deer.snd.objects.Props
import red.head.deer.snd.util.KafkaUtil
import red.head.deer.snd.util.SystemUtil
import java.util.*

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

    @When("Kafka request")
    fun sendRequest(d: DataTable) {
        val m = d.asLists()
        var success = true
        m.forEach {
            when (it[0]) {
                "kafka" -> Props.kafkaBroker = it[1]
                "rqTopic" -> rqTopic = it[1]
                "rsTopic" -> rsTopic = it[1]
                "success" -> success = it[1].toBoolean()
            }
        }
        val rqBody = om.writeValueAsString(KafkaRq(KafkaRq.Data("${UUID.randomUUID()}")))
        kafkaKey = "${UUID.randomUUID()}"
        send(rqTopic, SystemUtil().prettyJson(rqBody))
        mockResponse(success)
        val response = KafkaUtil().read(rsTopic, kafkaKey)
        val rsBody = response.values.first()
        println("Response headers: ${response.keys.first()}")
        println("body: ${SystemUtil().prettyJson(rsBody)}")
        try {
            om.readValue(rsBody, KafkaRs::class.java)
        } catch (e: Exception) {
            throw IllegalArgumentException("Parsing failed: ${e.message}")
        }
    }
}