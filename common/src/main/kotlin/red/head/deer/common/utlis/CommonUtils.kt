package red.head.deer.common.utlis

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.BooleanNode
import com.fasterxml.jackson.databind.node.DoubleNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.NullNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode

fun <T> T.ifNotNull(newValue: T?): T = newValue ?: this

val om = ObjectMapper()

fun setNestedValue(node: ObjectNode, path: String, rawValue: String) {
    val pathRegex = Regex("""([^.\[\]]+)(?:\[(\d+)])?\.?(.*)""")
    val pathMatcher = pathRegex.find(path) ?: throw IllegalArgumentException("Invalid path: $path")

    val (key, idx, tail) = pathMatcher.destructured
    val index = idx.toIntOrNull()

    if (index != null) {
        val arrayNode = when (val existing = node.get(key)) {
            null -> {
                val newArray = om.createArrayNode()
                node.set<ArrayNode>(key, newArray)
                newArray
            }
            is ArrayNode -> existing
            else -> throw IllegalStateException()
        }

        while (arrayNode.size() <= index) arrayNode.add(om.createObjectNode())

        val element = arrayNode.get(index)

        if (tail.isNotEmpty()) {
            if (element is ObjectNode) setNestedValue(element, tail, rawValue)
        } else {
            arrayNode.remove(index)
            arrayNode.insert(index, parseValue(rawValue))
        }
    } else {
        if (tail.isNotEmpty()) {
            val child = when (val existing = node.get(key)) {
                null -> {
                    val newObject = om.createObjectNode()
                    node.set<ObjectNode>(key, newObject)
                    newObject
                }
                is ObjectNode -> existing
                else -> throw IllegalStateException()
            }
            setNestedValue(child, tail, rawValue)
        } else {
            node.set(key, parseValue(rawValue))
        }
    }
}

// определение типа значения - логическое, пустое, целое число, вещественное число, текст
// ошибки игнорируются, т.к. всё неопределённое считаем текстом
fun parseValue(rawValue: String): JsonNode = when (rawValue.lowercase()) {
    "true" -> BooleanNode.TRUE
    "false" -> BooleanNode.FALSE
    "null" -> NullNode.instance
    else -> {
        try {
            IntNode(rawValue.toInt())
        } catch (_: NumberFormatException) {
            try {
                DoubleNode(rawValue.toDouble())
            } catch (_: NumberFormatException) {
                TextNode(rawValue)
            }
        }
    }
}