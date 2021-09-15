package developer007.magdy.code95weather.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import developer007.magdy.code95weather.data.SharedPrefManager;
import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.activities.MainActivity;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;
import developer007.magdy.code95weather.modules.weather.TodayWeatherModule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    SharedPrefManager sharedPrefManager = new SharedPrefManager();
    public MutableLiveData<TodayWeatherModule> todayWeatherModuleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ForeCastModule> foreCastModuleMutableLiveData = new MutableLiveData<>();

    public void getTodayWeather(String city, String unit, String appId, Context context) {
        WeatherClient.getInstance().getTodayWeather(city, unit, appId)
                .enqueue(new Callback<TodayWeatherModule>() {
                    @Override
                    public void onResponse(Call<TodayWeatherModule> call, Response<TodayWeatherModule> response) {
                        if (response.isSuccessful()) {
                            todayWeatherModuleMutableLiveData.setValue(response.body());
                        } else {
                            new AlertDialog.Builder(context).setTitle(R.string.error).setMessage("Result: " + response.message())
                                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                                        Log.d(TAG, "onClick: Mgd" + response.message());
                                        Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        SharedPrefManager.setAuthVal(context, "location", context.getString(R.string.shared_city));
                                        context.getApplicationContext().startActivity(i);

                                    }).create().show();
                        }

                    }

                    @Override
                    public void onFailure(Call<TodayWeatherModule> call, Throwable t) {
                        Log.d(TAG, "onFailure: Mgd" + t.toString());
                        new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(t.toString())
                                .setPositiveButton(R.string.ok, (dialog, which) -> {
                                    Log.d(TAG, "onClick: Mgd" + t.toString());

                                    Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.getApplicationContext().startActivity(i);

                                }).create().show();
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
                            new AlertDialog.Builder(context).setTitle(R.string.error).setMessage("Result: " + response.message())
                                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                                        Log.d(TAG, "onClick: Mgd" + response.message());
                                        Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        SharedPrefManager.setAuthVal(context, "location", context.getString(R.string.shared_city));
                                        context.getApplicationContext().startActivity(i);

                                    }).create().show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ForeCastModule> call, Throwable t) {
                        Log.d(TAG, "onFailure: Mgd" + t.toString());
                        new AlertDialog.Builder(context).setTitle(R.string.error).setMessage(t.toString())
                                .setPositiveButton(R.string.ok, (dialog, which) -> {
                                    Log.d(TAG, "onClick: Mgd" + t.toString());

                                    Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.getApplicationContext().startActivity(i);

                                }).create().show();
                    }
                });

    }
}
