package red.head.deer.frt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleApp
fun main(args: Array<String>) {
    runApplication<SimpleApp>(*args)
}