package developer007.magdy.code95weather.modules.forecast;

import java.util.List;

import developer007.magdy.code95weather.modules.weather.Main;
import developer007.magdy.code95weather.modules.weather.Weather;


public class ForeCastList {
    private long dt;
    private Main main;
    private List<Weather> weather;
    private String dt_txt;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
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

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
