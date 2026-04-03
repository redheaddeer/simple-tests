package red.head.deer.fst.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import mu.KLogging
import org.jetbrains.kotlin.com.google.gson.GsonBuilder
import org.jetbrains.kotlin.com.google.gson.JsonParser
import java.util.Properties

class SystemUtil {
    companion object : KLogging()

    fun prettyJson(json: String): String {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(JsonParser().parse(json))
    }
}