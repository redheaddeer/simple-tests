package red.head.deer.snd.steps

import io.cucumber.java.After
import io.cucumber.java.en.When
import org.jetbrains.kotlin.gradle.internal.name.JvmStandardClassIds.Annotations.Java
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import red.head.deer.snd.objects.Props

class UiSteps {
    var driver: WebDriver = initDriver()

    @After
    private fun close() = driver.close()

    private fun initDriver(): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", Java.javaClass.classLoader.getResource("webdriver/chromedriver.exe").path)
        val chromeOptions = ChromeOptions()
        chromeOptions
            .addArguments("--remote-allow-origins=*")
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