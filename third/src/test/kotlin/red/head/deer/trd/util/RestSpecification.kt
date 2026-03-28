package red.head.deer.trd.util

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.*
import io.restassured.filter.log.*
import io.restassured.specification.RequestSpecification
import org.apache.http.entity.mime.HttpMultipartMode

/**
Описывать схему подключения к каждому URL лучше по отдельности. Разделение настроек по хостам, и иногда по
определённым апи сервисам, позволяет безопасно вносить правки в проверки конкретного сервиса, кастомизировать
настройки подключения (авторизация по сертификату, таймауты), более гибко управлять логированием.
Для упрощения можно добавить один публичный параметризованный метод, возвращающий RequestSpecification,
определяя спецификацию по краткому имени или привязаться к переменной enum-класса.
*/
class RestSpecification {
    val timeout: Int = 30000
    val config = RestAssuredConfig.config().httpClient(
        HttpClientConfig.httpClientConfig()
            .setParam("http.socket.timeout", timeout)
            .setParam("http.connection.timeout", timeout)
    )

    val gitConfig = RestAssuredConfig.config().httpClient(
        HttpClientConfig.httpClientConfig()
            .httpMultipartMode(HttpMultipartMode.RFC6532)
            .setParam("http.socket.timeout", 10000)
            .setParam("http.connection.timeout", 10000)
    )

    private val google: RequestSpecification by lazy {
        RequestSpecBuilder()
            .setBaseUri("https://www.google.com")
            .build()
            .config(config)
            .log().all().filter(ResponseLoggingFilter())
    }

    private val github: RequestSpecification by lazy {
        RequestSpecBuilder()
            .setBaseUri("https://github.com")
            .build()
            .config(gitConfig)
            .log().headers()
    }

    private val wiki: RequestSpecification by lazy {
        RequestSpecBuilder()
            .setBaseUri("https://ru.wikipedia.org")
            .build()
            .config(config)
            .log().all().filter(ResponseLoggingFilter())
    }

    val spec: RequestSpecification
        get() {
            return RestAssured.given().spec(google)
        }

    val googleSpec: RequestSpecification
        get() {
            return RestAssured.given().spec(google)
        }

    val githubSpec: RequestSpecification
        get() {
            return RestAssured.given().spec(github)
        }

    val wikiSpec: RequestSpecification
        get() {
            return RestAssured.given().spec(wiki)
        }
}