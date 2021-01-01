package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features/",
        glue = {"StepDefinations"},
        plugin ={"json:target/jsonReports/Cucumber-Resport.json"}
        //tags = {"@DeletePlace"}
)
public class TestRunner {

}
