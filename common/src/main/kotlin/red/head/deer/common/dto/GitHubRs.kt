package red.head.deer.common.dto

import com.fasterxml.jackson.annotation.JsonProperty

class Indicator(
    @get:JsonProperty
    val mode: String
)