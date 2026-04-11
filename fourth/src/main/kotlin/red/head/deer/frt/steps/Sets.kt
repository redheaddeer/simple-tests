package red.head.deer.frt.steps

import red.head.deer.frt.config.Config

class Sets(
    val config: Config,
)  {
    fun runAll() {
        runRest()
        runKafka()
        runUi()
    }

    fun runRest() {
        Rest(config).googleSearch()
        Rest(config).wikiGit()
        Rest(config).rest()
    }

    fun runKafka() {}

    fun runUi() {}
}