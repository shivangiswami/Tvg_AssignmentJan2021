package StepDefinations;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class Hooks {
    static WebDriver driver;
   @Before("@FetchCityWeather")
    public static WebDriver setWebDriverProperties() {

       System.setProperty("webdriver.chrome.driver", "src/test/java/Resources/ChromeDriver/chromedriver_win32/chromedriver.exe");
       driver=new ChromeDriver();
       driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
       return driver;

          }

    @After("@CompareTemperature")
    public void closeWebDriver()
    {
        driver.close();
    }
    }

