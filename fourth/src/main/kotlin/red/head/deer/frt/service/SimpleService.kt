package red.head.deer.frt.service

import mu.KotlinLogging.logger
import red.head.deer.frt.config.Config


class SimpleService(
    val config: Config
) {
    val log = logger("red.head.deer.frt.service")

    private val useDb: Boolean = config.useDB
    private val testSet: String = config.testSet

    fun start() {
        log.info("Start with params: test-set = $testSet, use db = $useDb")
        
    }
}