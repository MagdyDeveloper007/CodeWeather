package developer007.magdy.code95weather.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.adapters.ForeCastAdapter;
import developer007.magdy.code95weather.data.API;
import developer007.magdy.code95weather.data.SharedPrefManager;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;
import developer007.magdy.code95weather.modules.weather.TodayWeatherModule;
import developer007.magdy.code95weather.utilities.WeatherViewModel;
import pl.droidsonroids.gif.GifImageView;

public class TodayFragment extends Fragment {
    private TextView tvTodayMetric, tvTodayImperial, tvTodayDegree, tvTodayDesc,
            tvTodayDate, tvTodayFeelLike, tvTodayHum, tvTodayWind, tvTodayMinMax;
    private EditText etLocation;
    private ImageButton imageLocation, imageSetting;
    private GifImageView progress;
    private ImageView imgToday;
    private RecyclerView recyclerTime;
    private String strTodayUnit, strTodayDegree, strTodayDesc,
            strLocation, strAppId, strIconBase, strIconExt, strImageIcon, strSpeed, strMinMax;
    private long strTodayDate;
    private String feels_like, temp_min, temp_max, humidity;
    private WeatherViewModel weatherViewModel;
    private AppCompatActivity compatActivity;
    private static final String TAG = "TodayFragment";
    private ForeCastAdapter foreCastAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        compatActivity = (AppCompatActivity) view.getContext();


        strTodayUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
        strLocation = SharedPrefManager.getAuthPref(compatActivity).getString("location", "London");

        API api = new API();
        strAppId = api.getApId();
        strIconBase = api.getImageURl();
        strIconExt = api.getImgExtUrl();


        tvTodayMetric = view.findViewById(R.id.tvTodayMetric);
        tvTodayMinMax = view.findViewById(R.id.tvTodayMinMax);
        tvTodayWind = view.findViewById(R.id.tvTodayWind);
        tvTodayHum = view.findViewById(R.id.tvTodayHum);
        tvTodayFeelLike = view.findViewById(R.id.tvTodayFeelLike);
        tvTodayImperial = view.findViewById(R.id.tvTodayImperial);
        tvTodayDesc = view.findViewById(R.id.tvTodayDesc);
        tvTodayDegree = view.findViewById(R.id.tvTodayDegree);
        tvTodayDate = view.findViewById(R.id.tvTodayDate);
        etLocation = view.findViewById(R.id.etLocation);
        imageLocation = view.findViewById(R.id.imageLocation);
        imgToday = view.findViewById(R.id.imgToday);
        progress = view.findViewById(R.id.progress);
        imgToday = view.findViewById(R.id.imgToday);
        imageSetting = view.findViewById(R.id.imageSetting);
        recyclerTime = view.findViewById(R.id.recyclerTime);


        foreCastAdapter = new ForeCastAdapter();


        etLocation.setText(strLocation);
        if (strTodayUnit.equals("metric")) {
            tvTodayMetric.setTextColor(Color.WHITE);
            tvTodayImperial.setTextColor(Color.DKGRAY);
        } else {
            tvTodayMetric.setTextColor(Color.DKGRAY);
            tvTodayImperial.setTextColor(Color.WHITE);
        }
        tvTodayImperial.setOnClickListener(v -> {
            tvTodayMetric.setTextColor(Color.DKGRAY);
            tvTodayImperial.setTextColor(Color.WHITE);
            strTodayUnit = "imperial";

            handlingViewModelToday(strLocation, strTodayUnit, strAppId);
            handlingViewModelNext(strLocation, strTodayUnit, strAppId);


        });
        tvTodayMetric.setOnClickListener(v -> {
            tvTodayMetric.setTextColor(Color.WHITE);
            tvTodayImperial.setTextColor(Color.DKGRAY);
            strTodayUnit = "metric";

            handlingViewModelToday(strLocation, strTodayUnit, strAppId);
            handlingViewModelNext(strLocation, strTodayUnit, strAppId);

        });
        imageLocation.setOnClickListener(v -> {
            progress.setVisibility(View.VISIBLE);
            strLocation = "";
            strLocation = etLocation.getText().toString().trim();


            String todayUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
            handlingViewModelToday(strLocation, todayUnit, strAppId);
            handlingViewModelNext(strLocation, strTodayUnit, strAppId);


        });


        recyclerTime.setAdapter(foreCastAdapter);

        handlingViewModelToday(strLocation, strTodayUnit, strAppId);


        imageSetting.setOnClickListener(v -> NavHostFragment.findNavController(TodayFragment.this)
                .navigate(R.id.action_TodayFragment_to_SettingFragment));

        handlingViewModelNext(strLocation, strTodayUnit, strAppId);


        return view;
    }
    //handling the view model singular for today

    public void handlingViewModelToday(String location, String todayUnit, String appId) {


        weatherViewModel = new ViewModelProvider(compatActivity).get(WeatherViewModel.class);

        weatherViewModel.getTodayWeather(location, todayUnit, appId, compatActivity);

        weatherViewModel.todayWeatherModuleMutableLiveData.observe(compatActivity, todayWeatherModule -> {
            strImageIcon = strIconBase + todayWeatherModule.getWeather().get(0).getIcon() + strIconExt;
            strTodayDegree = todayWeatherModule.getMain().getTemp() + getString(R.string.degree);
            strTodayDesc = todayWeatherModule.getWeather().get(0).getDescription();
            strTodayDate = todayWeatherModule.getDt();
            String date = "Last Update on: " + handlingDate(strTodayDate);
            feels_like = getString(R.string.feel_like) + " " + todayWeatherModule.getMain().getFeels_like() + getString(R.string.degree);
            temp_min = todayWeatherModule.getMain().getTemp_min() + getString(R.string.degree);
            temp_max = todayWeatherModule.getMain().getTemp_max() + getString(R.string.degree);
            humidity = todayWeatherModule.getMain().getHumidity() + getString(R.string.percent);
            strSpeed = todayWeatherModule.getWind().getSpeed() + " " + getString(R.string.km_h);
            strMinMax = (int) (todayWeatherModule.getMain().getTemp_min() - 2) + getString(R.string.degree)
                    + "/" + (int) todayWeatherModule.getMain().getTemp_max() + getString(R.string.degree);

            tvTodayMinMax.setText(strMinMax);
            tvTodayHum.setText(humidity);
            tvTodayWind.setText(strSpeed);
            tvTodayFeelLike.setText(feels_like);
            tvTodayDate.setText(date);
            Picasso.get().load(strImageIcon).into(imgToday);
            tvTodayDegree.setText(strTodayDegree);
            tvTodayDesc.setText(strTodayDesc);
            progress.setVisibility(View.GONE);


        });
    }

    //handling the view model singular for next days
    public void handlingViewModelNext(String location, String todayUnit, String appId) {

        progress.setVisibility(View.VISIBLE);

        weatherViewModel = new ViewModelProvider(compatActivity).get(WeatherViewModel.class);

        weatherViewModel.getForecast(location, todayUnit, appId, compatActivity);
        weatherViewModel.foreCastModuleMutableLiveData.observe(compatActivity, foreCastModule -> {
            foreCastAdapter.setList(foreCastModule);
            foreCastAdapter.notifyDataSetChanged();
            progress.setVisibility(View.GONE);

        });

    }

    //handling the date format
    public String handlingDate(long dateTime) {
        String time = "";
        Date dateStart = new Date(dateTime * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("KK:mm a - yyyy/MM/dd");
        time = sdf.format(dateStart);
        return time;
    }

}