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
    private Button btn;
    private AppCompatActivity compatActivity;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        compatActivity = (AppCompatActivity) view.getContext();
        checkConnection();

        btn = view.findViewById(R.id.btn);
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
/*        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OfflineFragment.this).navigate(R.id.action_OfflineFragment_to_TodayFragment);
            }
        });*/
        return view;
    }
    //handling the view model singular for today

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
