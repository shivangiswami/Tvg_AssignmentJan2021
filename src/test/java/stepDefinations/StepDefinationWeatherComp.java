package stepDefinations;

import resources.APIresources;
import resources.UnexpectedTemperatureDiff;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class StepDefinationWeatherComp extends Utils {
    ResponseSpecification responseSpecification;
    Response response;
    RequestSpecification requestSpecification;
    static String UItemperature;
    static String ApiTemperature;
    WebDriverWait wait;
    Float actualVariance;

    @Given("Website with url {string} is loaded with {string}")
    public void website_with_url_is_available(String url,String expectedTitle) {
        WebDriver driver = Hooks.setWebDriverProperties();
        driver.get(url);
        wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        driver.manage().window().maximize();
    }

    @When("User navigated to {string} page and pins the {string}")
    public void user_navigated_to_page_and_pins_the_city(String string,String city) throws InterruptedException, IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'No Thanks')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='topnavmore']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'WEATHER')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBox"))).sendKeys(city);
        String SearchCityCb="//input[@id='"+city+"' and @type='checkbox']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SearchCityCb))).click();
    }

    @Then("{string} should be available on the map")
    public void should_be_available_on_the_map(String city) {
        String cityLink="//div[@class='cityText' and text()='"+city+"']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cityLink))).click();
    }

    @Then("User is able to see weather details of selected {string}")
    public void user_is_able_to_see_weather_details_of_selected_city(String city) {
        String tempDetails = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Temp in Degrees:')]"))).getText();
        String[] actualTemp =  tempDetails.split(":");
        UItemperature=actualTemp[1].trim();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Condition :')]"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Wind:')]"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Humidity:')]"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Temp in Degrees:')]"))).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Temp in Fahrenheit:')]"))).isDisplayed());
    }

    @Given("Request payload is available with given {string} and {string}")
    public void request_payload_is_available_with_given_and(String Key, String City) throws IOException {
        requestSpecification = given().spec(requestSpecification(Key,City));
    }

    @When("User call weather api with {string} resource using GET http request")
    public void user_call_weather_api_using_GET_http_request(String resource) {
        APIresources apiR = APIresources.valueOf(resource);
        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
        {
           response = (Response) requestSpecification.when().get(apiR.getResource()).then().spec(responseSpecification).extract();
            ApiTemperature=jsonClass(response.asString(),"main.temp");
        }
    }
    @Then("Api call is success with status code {int}")
    public void api_call_is_success_with_status_code(Integer int1) {
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Given("UI tempreature and API temperatures are successfully fetched")
    public void ui_tempreature_and_API_temperatures_are_successfully_fetched() {
        Assert.assertTrue(UItemperature!=null);
        Assert.assertTrue(ApiTemperature!=null);
    }

    @When("Compares temp")
    public void compares_temp() {
        Float ApiTemp=Float.parseFloat(ApiTemperature);
        Float UITemp=Float.parseFloat(UItemperature);
        Float actualApiTemp=kelvinToCelciusConv(ApiTemp);
        if(actualApiTemp>UITemp)
        {
            actualVariance = actualApiTemp-UITemp;
        }
        else
        {
            actualVariance=UITemp - actualApiTemp;
        }
    }
    @Then("Validate if temperature difference is less than given variance")
    public void validate_if_temperature_difference_is_less_than_given_variance() throws IOException, UnexpectedTemperatureDiff {
        Float allowedVariance = Float.parseFloat(getGlobalProperties("Variation"));
        if(actualVariance > allowedVariance)
        {
            throw new UnexpectedTemperatureDiff("Temperature difference is more than allowed variance");
        }
    }
}
