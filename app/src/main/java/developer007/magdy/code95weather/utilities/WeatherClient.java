package developer007.magdy.code95weather.utilities;

import developer007.magdy.code95weather.data.API;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;
import developer007.magdy.code95weather.modules.weather.TodayWeatherModule;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    API api = new API();

    String baseUrl = api.getBaseUrl();
    private WeatherInterface weatherInterface;
    private static WeatherClient instance;

    public WeatherClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherInterface = retrofit.create(WeatherInterface.class);

    }

    public static WeatherClient getInstance() {
        if (null == instance) {
            instance = new WeatherClient();
        }
        return instance;

    }

    public Call<TodayWeatherModule> getTodayWeather(String city, String unit, String appId) {
        return weatherInterface.getTodayWeather(city, unit, appId);
    }

    public Call<ForeCastModule> getForecast(String city, String unit, String appId) {
        return weatherInterface.getForecast(city, unit, appId);
    }
}
