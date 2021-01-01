Feature: Compare Weather information
 @FetchCityWeather
  Scenario Outline: Verify if user can succefully fetch weather information
    Given Website with url "<URL>" is loaded with "<Title>"
    When User navigated to "<Section>" page and pins the "<City>"
    Then "<City>" should be available on the map
    And User is able to see weather details of selected "<City>"

    Examples:
      |City     |URL                  |Section|Title|
      |Pune|https://www.ndtv.com/|Weather|Get Latest News, India News, Breaking News, Today's News - NDTV.com|

   @FetchApiResponse
   Scenario Outline: Verify if user can succefully fetch weather information from API
     Given Request payload is available with given "<Key>" and "<City>"
     When User call weather api with "fetchCityWeather" resource using GET http request
     Then Api call is success with status code 200

     Examples:
     |Key                             |City|
     |c612f6d83d42fb74d06610204e0a9c3e|Pune|


    @CompareTemperature
    Scenario: Compare UI and API temperature
      Given UI tempreature and API temperatures are successfully fetched
      When Compares temp
      Then Validate if temperature difference is less than given variance



