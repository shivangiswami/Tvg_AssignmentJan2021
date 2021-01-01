package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;

    public RequestSpecification requestSpecification(String Key,String City) throws IOException {
        if(req==null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalProperties("BaseUrl")).addQueryParam("q", City)
                    .addQueryParam("appid",Key)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return req;
    }

    public static String getGlobalProperties(String key) throws IOException {
        Properties properties=new Properties();
        FileInputStream fis=new FileInputStream("C:\\Testvagrant_Automation\\src\\test\\java\\Resources\\global.properties");
        properties.load(fis);
        return properties.getProperty(key);
    }

    public static String jsonClass(String response,String key)
    {
        JsonPath js=new JsonPath(response);
        return js.get(key).toString();
    }

    public Float kelvinToCelciusConv(Float tempInKelvin)
    {
        Float celsius = tempInKelvin - 273.15F;
        return celsius;
    }
}
