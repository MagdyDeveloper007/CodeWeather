package developer007.magdy.code95weather.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.data.DefaultCityDatabase;
import developer007.magdy.code95weather.data.SharedPrefManager;
import developer007.magdy.code95weather.modules.SavedModule.SavedWeatherDefaultCityModule;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class OfflineFragment extends Fragment {
    private static final String TAG = "OfflineFragment";

    private AppCompatActivity compatActivity;
    private FloatingActionButton floatingActionButton;
    private TextView tvOfflineDegree, tvOfflineDesc,
            tvOfflineDate, tvOfflineFeelLike, tvOfflineHum, tvOfflineWind, tvOfflineMinMax;
    private TextView tvSelectOfflineCity;
    private ImageView imgOffline;
    private String strOfflineDegree, strOfflineDesc, strDate, strImageIcon, strSpeed, strMinMax;
    private String selectedCity;
    private String feels_like, humidity;
    private RelativeLayout layoutOffline;
    private ImageView imgNoInternet;

    private DefaultCityDatabase defaultCityDatabase;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        compatActivity = (AppCompatActivity) view.getContext();
        imgNoInternet = view.findViewById(R.id.imgNoInternet);
        checkConnection();
        defaultCityDatabase = DefaultCityDatabase.getInstance(compatActivity);
        selectedCity = SharedPrefManager.getAuthPref(compatActivity).getString("saved", "");


        layoutOffline = view.findViewById(R.id.layoutOffline);
        tvSelectOfflineCity = view.findViewById(R.id.tvSelectOfflineCity);
        tvOfflineMinMax = view.findViewById(R.id.tvOfflineMinMax);
        tvOfflineWind = view.findViewById(R.id.tvOfflineWind);
        tvOfflineHum = view.findViewById(R.id.tvOfflineHum);
        tvOfflineFeelLike = view.findViewById(R.id.tvOfflineFeelLike);
        tvOfflineDesc = view.findViewById(R.id.tvOfflineDesc);
        tvOfflineDegree = view.findViewById(R.id.tvOfflineDegree);
        tvOfflineDate = view.findViewById(R.id.tvOfflineDate);
        imgOffline = view.findViewById(R.id.imgOffline);


        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });

        if (TextUtils.isEmpty(selectedCity)) {
            layoutOffline.setVisibility(View.GONE);
            imgNoInternet.setVisibility(View.VISIBLE);
        } else {
            imgNoInternet.setVisibility(View.GONE);
            layoutOffline.setVisibility(View.VISIBLE);
            tvSelectOfflineCity.setText(selectedCity);
            checkTheDatabase();
        }
        return view;
    }

    public void checkTheDatabase() {

        defaultCityDatabase.savedDefaultCity().getSavedForecast()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<SavedWeatherDefaultCityModule>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<SavedWeatherDefaultCityModule> savedWeatherDefaultCityModules) {

                        strDate = savedWeatherDefaultCityModules.get(0).getDate();
                        strImageIcon = savedWeatherDefaultCityModules.get(0).getStrImageIcon();
                        strOfflineDegree = savedWeatherDefaultCityModules.get(0).getStrOfflineDegree();
                        feels_like = savedWeatherDefaultCityModules.get(0).getFeels_like();
                        strMinMax = savedWeatherDefaultCityModules.get(0).getStrMinMax();
                        strOfflineDesc = savedWeatherDefaultCityModules.get(0).getStrOfflineDesc();
                        strSpeed = savedWeatherDefaultCityModules.get(0).getStrSpeed();
                        humidity = savedWeatherDefaultCityModules.get(0).getHumidity();

                        Picasso.get().load(strImageIcon).into(imgOffline);
                        tvOfflineDate.setText(strDate);
                        tvOfflineDegree.setText(strOfflineDegree);
                        tvOfflineFeelLike.setText(feels_like);
                        tvOfflineMinMax.setText(strMinMax);
                        tvOfflineDesc.setText(strOfflineDesc);
                        tvOfflineWind.setText(strSpeed);
                        tvOfflineHum.setText(humidity);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: mgd" + e.getMessage());
                    }
                });


    }

    public void checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) compatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            NavHostFragment.findNavController(OfflineFragment.this).navigate(R.id.action_OfflineFragment_to_TodayFragment);

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(compatActivity, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
    }


}

