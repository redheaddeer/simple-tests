package red.head.deer.common.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

fun <T> T.ifNotNull(newValue: T?): T = newValue ?: this

fun prettyJson(json: String): String {
    return GsonBuilder()
        .setPrettyPrinting()
        .create()
        .toJson(JsonParser().parse(json))
}