package red.head.deer.frt.service

import mu.KotlinLogging.logger
import red.head.deer.frt.config.Config
import red.head.deer.frt.steps.Sets
import kotlin.concurrent.thread


class SimpleService(
    val config: Config
) {
    val log = logger("red.head.deer.frt.service")

    private val useDb: Boolean = config.useDB
    private val testSet: String = config.testSet

    fun start() {
        log.info("Start with params: test-set = $testSet, use db = $useDb")
        thread {
            when (testSet) {
                TestSet.ALL.name -> Sets(config).runAll()
                TestSet.REST.name -> Sets(config).runRest()
                TestSet.KAFKA.name -> Sets(config).runKafka()
            }
        }

    }
}

enum class TestSet {
    ALL, REST, KAFKA
}