package red.head.deer.frt.controller

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging.logger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import red.head.deer.common.utils.ifNotNull
import red.head.deer.common.dto.DbActiveRs
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
        @RequestParam("useDb") useDb: Boolean?,
        @RequestParam("testSet") testSet: String
    ): ResponseEntity<String> {
        log.info("start")
        config.useDB = config.useDB.ifNotNull(useDb)
        config.testSet = config.testSet.ifNotNull(testSet)
        SimpleService(config).start()
        return ResponseEntity.ok("Simple start")
    }

    // получаем короткое дто со статусом
    @GetMapping("/status")
    fun status(
        @RequestParam("testSet") testSet: String
    ): ResponseEntity<Any> {

        if (config.useDB) {
        }
        return ResponseEntity.ok(" ")
    }

    // получаем полный результат сценария
    @GetMapping("/result")
    fun result(
        @RequestParam("testSet") testSet: String
    ): ResponseEntity<Any> {

        if (config.useDB) {
        }
        return ResponseEntity.ok(" ")
    }

    // /db?active=true включает режим бд, /db?active=false выключает
    @GetMapping("/db")
    fun useDb(
        @RequestParam("active") active: Boolean
    ): ResponseEntity<Any> {
        try {
            config.useDB = active
            return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ObjectMapper().writeValueAsString(DbActiveRs("Database profile active: $active")))
        } catch (e: Exception) {
            return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ObjectMapper().writeValueAsString(DbActiveRs(e.message)))
        }
    }
}
