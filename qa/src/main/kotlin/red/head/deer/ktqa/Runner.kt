package red.head.deer.ktqa

import io.cucumber.core.cli.Main
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import mu.KLogging
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import red.head.deer.ktqa.util.SystemUtil
import kotlin.system.exitProcess

@RunWith(Cucumber::class)
@CucumberOptions( // Приоритет при запуске через JUnit, при этом тело класса игнорируется
    plugin = ["io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"],
    features = ["src/main/resources"],
    glue = ["red.head.deer.ktqa.steps"],
    tags = "@kafka"
)
class Runner {
    companion object : KLogging() {
        private fun getCucumber(args: List<String>): Byte =
            Main.run(args.toTypedArray(), Thread.currentThread().contextClassLoader)

        @JvmStatic
        fun main(args: Array<String>) {
            val cucumberOptions = listOf<String>()
            // VM Options при запуске как Application или через Kotlin плагин (аналогично запуску jar)
            val tags =
                if (System.getProperty("tags") != null) listOf("--tags", System.getProperty("tags")) else listOf()
            val glue =
                if (System.getProperty("glue") != null) listOf("--glue", System.getProperty("glue")) else listOf()
            val plugin =
                if (System.getProperty("plugin") != null) listOf("--plugin", System.getProperty("plugin")) else listOf()
            val features = System.getProperty("features") ?: ""
            cucumberOptions
                .plus(tags)
                .plus(glue)
                .plus(plugin)
                .plus(listOf(features))
            before()
            val exitCode = getCucumber(cucumberOptions)
            after()
            exitProcess(exitCode.toInt())
        }

        @BeforeClass
        @JvmStatic
        fun before() {
            SystemUtil().before()
        }

        @AfterClass
        @JvmStatic
        fun after() {
            SystemUtil().after()
        }
    }
}
