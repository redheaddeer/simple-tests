package red.head.deer.ktqa.dto

import com.fasterxml.jackson.annotation.JsonProperty

class Indicator(
    @get:JsonProperty
    val mode: String
)