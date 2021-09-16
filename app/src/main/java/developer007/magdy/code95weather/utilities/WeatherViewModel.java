package developer007.magdy.code95weather.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import developer007.magdy.code95weather.data.SharedPrefManager;
import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.activities.MainActivity;
import developer007.magdy.code95weather.fragments.TodayFragment;
import developer007.magdy.code95weather.modules.GeographicCoordinates.GeographicCoordinates;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;
import developer007.magdy.code95weather.modules.weather.TodayWeatherModule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    public MutableLiveData<TodayWeatherModule> todayWeatherModuleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ForeCastModule> foreCastModuleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<GeographicCoordinates> geographicCoordinatesMutableLiveData = new MutableLiveData<>();

    public void getTodayWeather(String city, String unit, String appId, Context context) {
        WeatherClient.getInstance().getTodayWeather(city, unit, appId)
                .enqueue(new Callback<TodayWeatherModule>() {
                    @Override
                    public void onResponse(Call<TodayWeatherModule> call, Response<TodayWeatherModule> response) {
                        if (response.isSuccessful()) {
                            todayWeatherModuleMutableLiveData.setValue(response.body());
                        } else {
                            new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(response.message())
                                    .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + response.message())).create().show();
                        }

                    }

                    @Override
                    public void onFailure(Call<TodayWeatherModule> call, Throwable t) {
                        Log.d(TAG, "onFailure: Mgd" + t.toString());
                        new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(t.getMessage())
                                .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + t.toString())).create().show();
                    }
                });
    }

    public void getForecast(String city, String unit, String appId, Context context) {
        WeatherClient.getInstance().getForecast(city, unit, appId)
                .enqueue(new Callback<ForeCastModule>() {
                    @Override
                    public void onResponse(Call<ForeCastModule> call, Response<ForeCastModule> response) {
                        if (response.isSuccessful()) {
                            foreCastModuleMutableLiveData.setValue(response.body());
                        } else {
                            new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(response.message())
                                    .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + response.message())).create().show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ForeCastModule> call, Throwable t) {
                        Log.d(TAG, "onFailure: Mgd" + t.toString());
                        new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(t.getMessage())
                                .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + t.toString())).create().show();
                    }
                });

    }

    public void getGeographicCoordinates(double lat, double lon, String unit, String appid, Context context) {

        WeatherClient.getInstance().getGeographicCoordinates(lat, lon, unit, appid)
                .enqueue(new Callback<GeographicCoordinates>() {
                    @Override
                    public void onResponse(Call<GeographicCoordinates> call, Response<GeographicCoordinates> response) {
                        if (response.isSuccessful()) {
                            geographicCoordinatesMutableLiveData.setValue(response.body());
                        } else {
                            new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(response.message())
                                    .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + response.message())).create().show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GeographicCoordinates> call, Throwable t) {
                        Log.d(TAG, "onFailure: Mgd" + t.toString());
                        new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(t.getMessage())
                                .setPositiveButton(R.string.ok, (dialog, which) -> Log.d(TAG, "onClick: Mgd" + t.toString())).create().show();
                    }
                });
    }
}
