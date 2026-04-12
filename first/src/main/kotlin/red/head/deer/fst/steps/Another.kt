package red.head.deer.fst.steps

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.*
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.When
import red.head.deer.common.dto.TestJson
import red.head.deer.common.utlis.*

class Another {

    @When("Prepare parsed json")
    fun jsonTest(d: DataTable) {
        val m = d.asMaps(String::class.java, String::class.java)
        val fields = mutableMapOf<String, String>()

        for (row in m) {
            val field = row["field"] ?: continue
            val value = row["value"] ?: continue
            fields[field] = value
        }

        val jsonNode = setNestedField(fields)
        val r3 = om.treeToValue(jsonNode, TestJson::class.java)
        println("Final JSON: ${om.writeValueAsString(r3)}")
    }

    private fun setNestedField(fields: MutableMap<String, String>): ObjectNode {
        val jsonNode = om.createObjectNode()

        fields.forEach { (path, rawValue) ->
            setNestedValue(jsonNode, path, rawValue)
        }

        return jsonNode
    }
}