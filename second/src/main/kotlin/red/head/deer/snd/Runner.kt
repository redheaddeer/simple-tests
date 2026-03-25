package red.head.deer.snd

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

// Полное управление из Cucumber
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:features"],
    glue = ["red.head.deer.snd.steps"]
)
class Runner {
}
