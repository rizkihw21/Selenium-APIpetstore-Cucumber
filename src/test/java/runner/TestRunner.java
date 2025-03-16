import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Lokasi file feature Gherkin
    glue = "steps", // Lokasi step definitions
    plugin = {"pretty", "html:target/cucumber-report.html"}, // Menyediakan laporan HTML
    // tags = "@GETpositiveFindpetID"
)
public class TestRunner {
}
