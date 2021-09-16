package developer007.magdy.code95weather.modules.GeographicCoordinates;

import java.util.List;

import developer007.magdy.code95weather.modules.weather.Main;
import developer007.magdy.code95weather.modules.weather.Weather;
import developer007.magdy.code95weather.modules.weather.Wind;

public class GeoWeather {
    Main main;
    List<Weather> weather;
    Wind wind;
    long dt;
    String dt_txt;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}
