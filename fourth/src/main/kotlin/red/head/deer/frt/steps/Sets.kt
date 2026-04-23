package red.head.deer.frt.steps

import red.head.deer.frt.config.Config

class Sets(
    val config: Config,
)  {
    fun runAll() {
        runRest()
        runKafka()
    }

    fun runRest() {
        Rest(config).googleSearch()
        Rest(config).wikiGit()
        Rest(config).rest()
    }

    fun runKafka() {
        Kafka(config).sendRequest(true)
        Kafka(config).sendRequest(false)
    }

}