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
import android.widget.Button;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


public class OfflineFragment extends Fragment {
    private static final String TAG = "OfflineFragment";

    private AppCompatActivity compatActivity;
    private FloatingActionButton floatingActionButton;
    private TextView tvOfflineDegree, tvOfflineDesc,
            tvOfflineDate, tvOfflineFeelLike, tvOfflineHum, tvOfflineWind, tvOfflineMinMax;
    private TextView tvSelectOfflineCity;
    private ImageView imgOffline;
    private String strOfflineUnit, strOfflineDegree, strOfflineDesc,
            strCity, strCity1, strCity2, strCity3, strCity4, strImageIcon, strSpeed, strMinMax;
    private String feels_like, temp_min, temp_max, humidity;
    private WeatherViewModel weatherViewModel;
    private ArrayList<String> arrayList;
    private Dialog dialog;
    private EditText etCity;
    private ListView listItem;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        compatActivity = (AppCompatActivity) view.getContext();
        checkConnection();
        arrayList = new ArrayList<>();

        strOfflineUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
        strCity = SharedPrefManager.getAuthPref(compatActivity).getString("location", "");
        strCity1 = SharedPrefManager.getAuthPref(compatActivity).getString("location1", "");
        strCity2 = SharedPrefManager.getAuthPref(compatActivity).getString("location2", "");
        strCity3 = SharedPrefManager.getAuthPref(compatActivity).getString("location3", "");
        strCity4 = SharedPrefManager.getAuthPref(compatActivity).getString("location4", "");

        tvSelectOfflineCity = view.findViewById(R.id.tvSelectOfflineCity);
        tvOfflineMinMax = view.findViewById(R.id.tvOfflineMinMax);
        tvOfflineWind = view.findViewById(R.id.tvOfflineWind);
        tvOfflineHum = view.findViewById(R.id.tvOfflineHum);
        tvOfflineFeelLike = view.findViewById(R.id.tvOfflineFeelLike);
        tvOfflineDesc = view.findViewById(R.id.tvOfflineDesc);
        tvOfflineDegree = view.findViewById(R.id.tvOfflineDegree);
        tvOfflineDate = view.findViewById(R.id.tvOfflineDate);
        imgOffline = view.findViewById(R.id.imgOffline);


        if (!TextUtils.isEmpty(strCity)) {
            arrayList.add(strCity);
            tvSelectOfflineCity.setText(strCity);
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

        //dddddd
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
        tvSelectOfflineCity.setOnClickListener(new View.OnClickListener() {
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
                        tvSelectOfflineCity.setText(arrayAdapter.getItem(position));
                        dialog.dismiss();

                    }
                });

            }
        });
        return view;
    }

    //handling the date format
    public String handlingDate(long dateTime) {
        String time = "";
        Date dateStart = new Date(dateTime * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("KK:mm a - yyyy/MM/dd");
        time = sdf.format(dateStart);
        return time;
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
