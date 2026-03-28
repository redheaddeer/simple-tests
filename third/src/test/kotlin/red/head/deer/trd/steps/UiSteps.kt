package red.head.deer.trd.steps

import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import red.head.deer.common.objects.Props
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption


class UiSteps {
    var driver: WebDriver = initDriver()

    @Throws(IOException::class)
    fun getDriverPath(): File {
        val driverUrl: URL = javaClass.classLoader.getResource("webdriver/chromedriver.exe")
            ?: throw FileNotFoundException("Driver not found")

        val tempFile: Path = Files.createTempFile("chromedriver", ".exe")
        tempFile.toFile().deleteOnExit()

        driverUrl.openStream().use { `is` ->
            Files.copy(`is`, tempFile, StandardCopyOption.REPLACE_EXISTING)
        }
        return tempFile.toFile()
    }
    private fun initDriver(): ChromeDriver {
        System.setProperty(
            "webdriver.chrome.driver",
            getDriverPath().path
        )
        val chromeOptions = ChromeOptions()
        chromeOptions
            .addArguments("--remote-allow-origins=*")
            .setCapability("browserName", "chrome")

        return ChromeDriver(chromeOptions)
    }

    @Test
    fun openUrl() {
        driver.get(Props.uiUri)
        Thread.sleep(10000L)
        driver
            .findElement(By.xpath("//*[text()='НАЖМИ СЮДА']"))
            .click()
        Thread.sleep(3000L)
        driver.quit()
    }
}