package red.head.deer.fourth.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {
    @GetMapping("/start")
    fun start() {

    }
}
