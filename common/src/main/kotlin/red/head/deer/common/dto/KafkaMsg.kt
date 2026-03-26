package red.head.deer.common.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KafkaRq(
    @get:JsonProperty
    var data: Data = Data()
) {
    data class Data(
        @get:JsonProperty
        var uuid: String? = null
    )
}


data class KafkaRs(
    @get:JsonProperty
    var data: Data = Data()
) {
    data class Data(
        @get:JsonProperty
        var email: String? = null,
        @get:JsonProperty
        var phone: String? = null
    )
}