package red.head.deer.ktqa.steps

import io.cucumber.java.After
import io.cucumber.java.en.When
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import red.head.deer.ktqa.objects.Props

class UiSteps {
    var driver: WebDriver = initDriver()

    @After
    private fun close() = driver.close()

    private fun initDriver(): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "G:\\projects\\qa\\src\\main\\resources\\webdriver\\chromedriver.exe")
        val chromeOptions = ChromeOptions()
        chromeOptions
            .addArguments("--remote-allow-origins=*")
            .setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe")
            .setCapability("browserName", "chrome")

        return ChromeDriver(chromeOptions)
    }

    @When("Open url '{}'")
    fun openUrl(url: String) {
        val uri = url.ifBlank { Props.uiUri }
        driver.get(uri)
        Thread.sleep(10000L)
    }

    @When("Click button '{}'")
    fun click(button: String) {
        driver
            .findElement(By.xpath("//*[text()='$button']"))
            .click()
        Thread.sleep(3000L)
    }

    @When("Close browser")
    fun closeBrowser() {
        driver.quit()
    }
}