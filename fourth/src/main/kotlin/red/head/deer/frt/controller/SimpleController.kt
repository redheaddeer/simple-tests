package red.head.deer.frt.controller

import mu.KotlinLogging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import red.head.deer.frt.config.Config
import red.head.deer.frt.service.SimpleService

@RestController
class SimpleController(
    val config: Config,
) {
    val log = logger("red.head.deer.frt.controller")

    @GetMapping("/start")
    fun start(
      @RequestParam("useDb") useDb: Boolean,
      @RequestParam("testSet") testSet: String
    ): ResponseEntity<String> {
        log.info("start")
        config.useDB = useDb ?: config.useDB
        config.testSet = testSet ?: config.testSet
        SimpleService(config).start()
        return ResponseEntity.ok("Simple start")
    }
}
