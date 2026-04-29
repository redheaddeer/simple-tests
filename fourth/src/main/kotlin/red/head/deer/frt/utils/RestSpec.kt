package red.head.deer.frt.utils

import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.HttpClientConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification

class RestSpec {
    val timeout: Int = 30000
    val config = RestAssuredConfig.config().httpClient(
        HttpClientConfig.httpClientConfig()
            .setParam("http.socket.timeout", timeout)
            .setParam("http.connection.timeout", timeout)
    )

    fun spec(uri: String): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(uri)
            .build()
            .config(config)
            .log().all().filter(ResponseLoggingFilter())
    }

}