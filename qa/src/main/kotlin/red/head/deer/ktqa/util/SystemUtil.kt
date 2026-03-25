package red.head.deer.ktqa.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import mu.KLogging
import org.jetbrains.kotlin.com.google.gson.GsonBuilder
import org.jetbrains.kotlin.com.google.gson.JsonParser
import java.util.Properties

class SystemUtil {
    companion object : KLogging()
    lateinit var systemProperties: Properties

    fun before() {
        try {
            logger.info("Saving system properties")
            systemProperties = System.getProperties()
            logger.info("Add custom properties by application.yml")
            val fileProps = {}.javaClass.classLoader.getResourceAsStream("application.yml")
                ?.bufferedReader().use { it?.readText() } ?: ""
            val props = ObjectMapper(YAMLFactory()).readValue(fileProps, Map::class.java) as Map<String, String>
            props.forEach { System.setProperty(it.key, it.value) }
            println(props)
        } catch (e: Exception) {
            logger.error("Error before starting tests: ${e.message}")
        }
    }

    fun after() {
        try {
            logger.info("Restoring system properties")
            System.setProperties(systemProperties)
        } catch (e: Exception) {
            logger.error("Error after completion tests: ${e.message}")
        }
    }

    fun prettyJson(json: String): String {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(JsonParser().parse(json))
    }
}