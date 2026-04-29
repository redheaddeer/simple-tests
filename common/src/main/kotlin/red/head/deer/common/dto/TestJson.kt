package red.head.deer.common.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TestJson(
    @get:JsonProperty
    var data: List<Data> = listOf(),
) {
    data class Data(
        @get:JsonProperty
        var email: String? = null,
        @get:JsonProperty
        var phone: String? = null,
        @get:JsonProperty
        var other: List<Other>? = listOf(),
    ) {
        data class Other(
            @get:JsonProperty
            var field: String? = null,
            @get:JsonProperty
            var value: String? = null,
        )
    }
}