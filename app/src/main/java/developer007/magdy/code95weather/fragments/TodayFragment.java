package developer007.magdy.code95weather.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.adapters.ForeCastAdapter;
import developer007.magdy.code95weather.data.API;
import developer007.magdy.code95weather.data.SharedPrefManager;

import developer007.magdy.code95weather.utilities.WeatherViewModel;
import pl.droidsonroids.gif.GifImageView;


public class TodayFragment extends Fragment {


    private TextView tvTodayMetric, tvTodayImperial, tvTodayDegree, tvTodayDesc,
            tvTodayDate, tvTodayFeelLike, tvTodayHum, tvTodayWind, tvTodayMinMax;
    private ImageButton imageSetting;
    private GifImageView progress;
    private ImageView imgToday;
    private RecyclerView recyclerTime;
    private String strTodayUnit, strTodayDegree, strTodayDesc,
            strCity, strCity1, strCity2, strCity3, strCity4, strAppId, strIconBase, strIconExt, strImageIcon, strSpeed, strMinMax;
    private long strTodayDate;
    private String feels_like, temp_min, temp_max, humidity;
    private WeatherViewModel weatherViewModel;
    private AppCompatActivity compatActivity;
    private static final String TAG = "TodayFragment";
    private ForeCastAdapter foreCastAdapter;
    private TextView tvSelectCity;
    private ArrayList<String> arrayList;
    private Dialog dialog;
    private EditText etCity;
    private ListView listItem;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        compatActivity = (AppCompatActivity) view.getContext();
        arrayList = new ArrayList<>();

        strTodayUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
        strCity = SharedPrefManager.getAuthPref(compatActivity).getString("location", "");
        strCity1 = SharedPrefManager.getAuthPref(compatActivity).getString("location1", "");
        strCity2 = SharedPrefManager.getAuthPref(compatActivity).getString("location2", "");
        strCity3 = SharedPrefManager.getAuthPref(compatActivity).getString("location3", "");
        strCity4 = SharedPrefManager.getAuthPref(compatActivity).getString("location4", "");

        API api = new API();
        strAppId = api.getApId();
        strIconBase = api.getImageURl();
        strIconExt = api.getImgExtUrl();

        tvSelectCity = view.findViewById(R.id.tvSelectCity);
        tvTodayMetric = view.findViewById(R.id.tvTodayMetric);
        tvTodayMinMax = view.findViewById(R.id.tvTodayMinMax);
        tvTodayWind = view.findViewById(R.id.tvTodayWind);
        tvTodayHum = view.findViewById(R.id.tvTodayHum);
        tvTodayFeelLike = view.findViewById(R.id.tvTodayFeelLike);
        tvTodayImperial = view.findViewById(R.id.tvTodayImperial);
        tvTodayDesc = view.findViewById(R.id.tvTodayDesc);
        tvTodayDegree = view.findViewById(R.id.tvTodayDegree);
        tvTodayDate = view.findViewById(R.id.tvTodayDate);

        imgToday = view.findViewById(R.id.imgToday);
        progress = view.findViewById(R.id.progress);
        imgToday = view.findViewById(R.id.imgToday);
        imageSetting = view.findViewById(R.id.imageSetting);
        recyclerTime = view.findViewById(R.id.recyclerTime);


        foreCastAdapter = new ForeCastAdapter();

        if (!TextUtils.isEmpty(strCity)) {
            arrayList.add(strCity);
            tvSelectCity.setText(strCity);
        }
        if (!TextUtils.isEmpty(strCity1)) {
            arrayList.add(strCity1);
        }
        if (!TextUtils.isEmpty(strCity2)) {
            arrayList.add(strCity2);
        }
        if (!TextUtils.isEmpty(strCity3)) {
            arrayList.add(strCity3);
        }
        if (!TextUtils.isEmpty(strCity4)) {
            arrayList.add(strCity4);
        }

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


            if (TextUtils.isEmpty(strCity)) {

                NavHostFragment.findNavController(TodayFragment.this)
                        .navigate(R.id.action_TodayFragment_to_SettingFragment);
            } else {
                recyclerTime.setAdapter(foreCastAdapter);
                handlingViewModelToday(strCity, strTodayUnit, strAppId);
                handlingViewModelNext(strCity, strTodayUnit, strAppId);
            }


        });
        tvTodayMetric.setOnClickListener(v -> {
            tvTodayMetric.setTextColor(Color.WHITE);
            tvTodayImperial.setTextColor(Color.DKGRAY);
            strTodayUnit = "metric";

            if (TextUtils.isEmpty(strCity)) {
                NavHostFragment.findNavController(TodayFragment.this)
                        .navigate(R.id.action_TodayFragment_to_SettingFragment);

            } else {
                recyclerTime.setAdapter(foreCastAdapter);
                handlingViewModelToday(strCity, strTodayUnit, strAppId);
                handlingViewModelNext(strCity, strTodayUnit, strAppId);
            }

        });


        imageSetting.setOnClickListener(v -> NavHostFragment.findNavController(TodayFragment.this)
                .navigate(R.id.action_TodayFragment_to_SettingFragment));

        if (TextUtils.isEmpty(strCity)) {
            NavHostFragment.findNavController(TodayFragment.this)
                    .navigate(R.id.action_TodayFragment_to_SettingFragment);

        } else {
            recyclerTime.setAdapter(foreCastAdapter);
            handlingViewModelToday(strCity, strTodayUnit, strAppId);
            handlingViewModelNext(strCity, strTodayUnit, strAppId);
        }
        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(compatActivity);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams windowManager = window.getAttributes();
                windowManager.gravity = Gravity.TOP;
                window.setAttributes(windowManager);
                dialog.show();
                etCity = dialog.findViewById(R.id.etCity);
                listItem = dialog.findViewById(R.id.listItem);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(compatActivity, android.R.layout.simple_list_item_1, arrayList);
                listItem.setAdapter(arrayAdapter);
                etCity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        arrayAdapter.getFilter().filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSelectCity.setText(arrayAdapter.getItem(position));
                        dialog.dismiss();
                        handlingViewModelToday(arrayAdapter.getItem(position), strTodayUnit, strAppId);
                        handlingViewModelNext(arrayAdapter.getItem(position), strTodayUnit, strAppId);
                    }
                });

            }
        });

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
            String date = getString(R.string.last_update_on) + " " + handlingDate(strTodayDate);
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
