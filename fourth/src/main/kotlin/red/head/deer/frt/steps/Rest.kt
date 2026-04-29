package red.head.deer.frt.steps

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import red.head.deer.common.dto.GitPage
import red.head.deer.common.objects.Endpoints
import red.head.deer.frt.config.Config
import red.head.deer.frt.service.RestSpec
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Rest(
    val config: Config,
) {
    val om = ObjectMapper()

    fun googleSearch() {
        val rs = RestSpec().spec(config.googleUri)
            .contentType(ContentType.ANY)
            .with()
            .header("client", "autotest")
            .param("q", "github")
            .get(Endpoints.GoogleSearch)

        val rsBody = rs.body.asString()
        val title = rsBody.substringAfter("<title>").substringBefore("</title>")

        assertEquals(rs.statusCode, 200, "Error status code, expected 200, but found ${rs.statusCode}")
        assertTrue(rs.body().asString().contains("<title>Google Search</title>"), "Unexpected title $title")
    }

    fun wikiGit() {
        val rs = RestSpec().spec(config.wikiUri)
            .contentType(ContentType.ANY)
            .with()
            .header("client", "autotest")
            .header(
                "user-agent",
                "Chrome/146.0.0.0"
            )
            .get(Endpoints.WikiApi + Endpoints.WikiGitPage)

        val rsBody = om.readValue(rs.body.asString(), GitPage::class.java)

        assertEquals(rs.statusCode, 200, "Error status code, expected 200, but found ${rs.statusCode}")
        assertEquals(rsBody.title, "Git", "Expected title Git, but found '${rsBody.title}'")
    }

    fun rest() {
        given()
            .`when`().get(config.uiUri)
            .then().statusCode(200)
    }
}