package red.head.deer.ktqa.steps

import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.en.When
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import red.head.deer.common.dto.GitPage
import red.head.deer.common.objects.Endpoints
import red.head.deer.ktqa.util.RestSpecification
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Rest {
    var googleSpec = RestSpecification().googleSpec
    var wikiSpec = RestSpecification().wikiSpec
    var githubSpec = RestSpecification().githubSpec
    val om = ObjectMapper()

    // если схема ответа не определена, но точно известно, какой уникальный текст в ответе надо найти
    @When("Google search")
    fun googleSearch() {
        val rs = googleSpec
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

    // если схема ответа заранее определена и можно описать дата-класс
    @When("Wiki git page")
    fun wikiGit() {
        val rs = wikiSpec
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

    // если нужно проверить, допустим, доступность стенда
    @When("Simple checker")
    fun rest() {
        given()
            .`when`().get("https://qaqateam.do.am/")
            .then().statusCode(200)
    }
}
