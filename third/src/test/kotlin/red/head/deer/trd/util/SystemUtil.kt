package red.head.deer.trd.util

import mu.KLogging
import org.jetbrains.kotlin.com.google.gson.GsonBuilder
import org.jetbrains.kotlin.com.google.gson.JsonParser

class SystemUtil {
    companion object : KLogging()

    fun prettyJson(json: String): String {
        return GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(JsonParser().parse(json))
    }
}