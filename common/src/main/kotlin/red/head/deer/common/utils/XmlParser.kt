package red.head.deer.common.utils

import org.w3c.dom.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.io.StringWriter

// вспомогательные функции
private fun NodeList.toList(): List<Element> =
    (0 until this.length).map { this.item(it) as Element }

private fun Element.getChildElement(tagName: String): Element? {
    val nodes = this.getElementsByTagName(tagName)
    return if (nodes.length > 0) nodes.item(0) as Element else null
}

// сборка xml
fun  buildXmlFromTable(fields: Map<String, String>): Document {
    val factory = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val document = factory.newDocument()

    val root = document.createElement("root")
    document.appendChild(root)

    fields.forEach { (path, value) ->
        setNestedElementXml(document, root, path, value)
    }

    return document
}

// парсер
private fun setNestedElementXml(
    doc: Document,
    parent: Element,
    path: String,
    rawValue: String
) {
    val regex = Regex("""([^.\[\]]+)(?:\[(\d+)])?\.?(.*)""")
    val matcher = regex.find(path) ?: throw IllegalArgumentException("Invalid path: $path")

    val (currentKey, indexStr, tail) = matcher.destructured
    val index = indexStr.toIntOrNull()

    if (index != null) {
        // Работаем с массивом элементов
        val elements = parent.getElementsByTagName(currentKey).toList()

        val targetElement = if (index < elements.size) {
            elements[index]
        } else {
            val newElement = doc.createElement(currentKey)
            parent.appendChild(newElement)
            newElement
        }

        if (tail.isNotEmpty()) {
            setNestedElementXml(doc, targetElement, tail, rawValue)
        } else {
            targetElement.textContent = rawValue
        }
    } else {
        // Простое поле
        if (tail.isNotEmpty()) {
            var child = parent.getChildElement(currentKey)
            if (child == null) {
                child = doc.createElement(currentKey)
                parent.appendChild(child)
            }
            setNestedElementXml(doc, child, tail, rawValue)
        } else {
            val element = doc.createElement(currentKey)
            element.textContent = rawValue
            parent.appendChild(element)
        }
    }
}

// сериализатор
fun documentToString(doc: Document): String {
    val transformer: Transformer = TransformerFactory.newInstance().newTransformer().apply {
        setOutputProperty(OutputKeys.INDENT, "yes")
        setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
    }

    val writer = StringWriter()
    transformer.transform(DOMSource(doc), StreamResult(writer))
    return writer.toString()
}
