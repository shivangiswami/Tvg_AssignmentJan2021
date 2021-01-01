package resources;

public enum  APIresources {

    fetchCityWeather("/data/2.5/weather");
    private String resource;
    APIresources(String s) {
        this.resource=s;
    }
    public String getResource()
    {
        return resource;
    }
}
