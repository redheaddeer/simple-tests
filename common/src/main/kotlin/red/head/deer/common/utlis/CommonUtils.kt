package red.head.deer.common.utlis

fun <T> T.ifNotNull(newValue: T?): T = newValue ?: this
