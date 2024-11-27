package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/java/features/LocationAPI.feature",
        glue = "stepDefinition",
        monochrome = true,
        dryRun = false
        // tags = ""
)

public class Runner {
}
