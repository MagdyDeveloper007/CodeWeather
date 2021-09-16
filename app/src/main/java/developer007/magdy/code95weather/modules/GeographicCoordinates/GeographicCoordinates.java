package developer007.magdy.code95weather.modules.GeographicCoordinates;

import java.util.List;

import developer007.magdy.code95weather.modules.weather.Weather;

public class GeographicCoordinates {
    String cod;
    List<GeoWeather> list;
    City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<GeoWeather> getList() {
        return list;
    }

    public void setList(List<GeoWeather> list) {
        this.list = list;
    }
}
