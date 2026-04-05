package red.head.deer.frt.controller

import mu.KotlinLogging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import red.head.deer.frt.service.SimpleService

@RestController
class SimpleController {
    val log = logger("red.head.deer.frt.controller")
    @GetMapping("/start")
    fun start(): ResponseEntity<String> {
        log.info("start")
        SimpleService().start()
        return ResponseEntity.ok("Simple start")
    }
}
