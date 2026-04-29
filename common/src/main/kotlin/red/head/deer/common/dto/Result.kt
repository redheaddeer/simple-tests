package red.head.deer.common.dto

data class Result(
    var status: String = "",
    var results: List<Step> = listOf()
) {
    data class Step(
        var name: String = "",
        var status: String = "",
        var result: String = "",
    )
}