package red.head.deer.frt.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "simple-service")
data class Config(
    var testSet: String = "",
    var useDB: Boolean = false,
    var googleUri: String = "",
    var githubUri: String = "",
    var uiUri: String = "",
    var wikiUri: String = "",
)