package red.head.deer.frt.controller

import mu.KotlinLogging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import red.head.deer.common.utlis.ifNotNull
import red.head.deer.frt.config.Config
import red.head.deer.frt.service.SimpleService

@RestController
class SimpleController(
    val config: Config,
) {
    val log = logger("red.head.deer.frt.controller")

//    curl -v 'http://localhost:8080/start?useDb=false&testSet=ALL'
    @GetMapping("/start")
    fun start(
        @RequestParam("useDb") useDb: Boolean,
        @RequestParam("testSet") testSet: String
    ): ResponseEntity<String> {
        log.info("start")
        config.useDB = config.useDB.ifNotNull(useDb)
        config.testSet = config.testSet.ifNotNull(testSet)
        SimpleService(config).start()
        return ResponseEntity.ok("Simple start")
    }
}