Synopsis:
This Cucumber BDD project fetches city temperature from UI and a weather api and then compare the difference with configurable allowed variance

Code Example:
@Given("Request payload is available with given {string} and {string}")
    public void request_payload_is_available_with_given_and(String Key, String City) throws IOException {
        res=given().spec(requestSpecification(Key,City));
    }

Motivation:
To run test cases on NDTV website and on weather API to maintain quality

Installation:
Have this project in any ide(Maven and java must be installed on the system as prerequisite)
POM.xml contains all the required dependencies

Test:
To run the test either run "TestRunner" file present inside cucumber.Options package or just write maven command on Terminal "mvn test"

All test cases are present in feature file "WeatherComparator.feature", We can also change the data from feature file itself
Variance configuration provided in "global.properties" file
Common utilities present in "Utils" class
mandatory Before and after method written in "Hooks" class, For eg setting up driver properties, Implicit wait 

Reporting:
API logs are stored in new print stream file "logging.txt"
Cucumber html reports are generated inside "target" folder, To generate run command "mvn test verify"
    
