package red.head.deer.fst

import io.cucumber.core.cli.Main
import mu.KLogging
import kotlin.system.exitProcess

class Runner {
    companion object : KLogging() {
        private fun getCucumber(args: List<String>): Byte =
            Main.run(args.toTypedArray(), Thread.currentThread().contextClassLoader)

        @JvmStatic
        fun main(args: Array<String>) {
            // значения по умолчанию для локального запуска
            val cucumberArgs = hashMapOf<String, String>(
                Pair("cucumber.glue", "red.head.deer.fst.steps"),
                Pair("cucumber.plugin", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"),
                Pair("cucumber.filter.tags", "@rest"),
                Pair("cucumber.features", "G:\\projects\\simple-tests\\first\\build\\libs\\features"),
            )
            // внешние значения при запуске из jar файла
            args.forEach { arg ->
                when {
                    arg.startsWith("tags") -> cucumberArgs["cucumber.filter.tags"] = arg.substringAfter('=')
                    arg.startsWith("features") -> cucumberArgs["cucumber.features"] = arg.substringAfter('=')
                }
            }
            setProps(cucumberArgs)
            val exitCode = getCucumber(listOf())
            exitProcess(exitCode.toInt())
        }

        private fun setProps(cucumberArgs: HashMap<String, String>) {
            System.setProperty("cucumber.features", cucumberArgs["cucumber.features"] ?: "")
            System.setProperty("cucumber.filter.tags", cucumberArgs["cucumber.filter.tags"] ?: "")
            System.setProperty("cucumber.glue", cucumberArgs["cucumber.glue"] ?: "")
            System.setProperty("cucumber.plugin", cucumberArgs["cucumber.plugin"] ?: "")
        }
    }
}
