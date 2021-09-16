package developer007.magdy.code95weather.utilities;

import developer007.magdy.code95weather.modules.GeographicCoordinates.GeographicCoordinates;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;
import developer007.magdy.code95weather.modules.weather.TodayWeatherModule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("weather")
    public Call<TodayWeatherModule> getTodayWeather(@Query("q") String city,
                                                    @Query("units") String unit,
                                                    @Query("appid") String appId);

    @GET("forecast")
    public Call<ForeCastModule> getForecast(@Query("q") String city,
                                            @Query("units") String unit,
                                            @Query("appid") String appId);

    @GET("forecast")
    public Call<GeographicCoordinates> getGeographicCoordinates(@Query("lat") double lat,
                                                                @Query("lon") double lon,
                                                                @Query("units") String unit,
                                                                @Query("appid") String appid);


}
