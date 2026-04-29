package red.head.deer.fst.steps

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.When
import red.head.deer.common.dto.TestJson
import red.head.deer.common.utils.*

class Another {
    val om = ObjectMapper()
    val xm = XmlMapper()

    @When("Prepare parsed json")
    fun jsonTest(d: DataTable) {
        val fields = prepareMap(d)

        val jsonNode = setNestedField(fields)
        val r3 = om.treeToValue(jsonNode, TestJson::class.java)
        println("Final JSON: ${om.writeValueAsString(r3)}")
    }


    @When("Prepare parsed xml")
    fun xmlTest(d: DataTable) {
        val fields = prepareMap(d)

        val xml = buildXmlFromTable(fields)
        println("Final XML:\n${documentToString(xml)}")
    }

    private fun prepareMap(d: DataTable): Map<String, String> {
        val m = d.asMaps(String::class.java, String::class.java)
        val fields = mutableMapOf<String, String>()

        for (row in m) {
            val field = row["field"] ?: continue
            val value = row["value"] ?: continue
            fields[field] = value
        }

        return fields
    }

    private fun setNestedField(fields: Map<String, String>): ObjectNode {
        val jsonNode = om.createObjectNode()

        fields.forEach { (path, rawValue) ->
            setNestedValue(jsonNode, path, rawValue)
        }

        return jsonNode
    }
}