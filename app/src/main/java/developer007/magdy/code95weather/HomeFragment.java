package developer007.magdy.code95weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import developer007.magdy.code95weather.data.SharedPrefManager;
import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment {
    private TextView tvTodayMetric, tvTodayImperial, tvTodayDegree, tvTodayDesc,
            tvTodayDate, tvTodayFeelLike, tvTodayHum, tvTodayWind, tvTodayMinMax;
    private EditText etLocation;
    private ImageButton imageLocation;
    private GifImageView progress;
    private ImageView imgToday;
    private RecyclerView recyclerTime;
    private String strTodayUnit, strTodayDegree, strTodayDesc,
            strLocation, strAppId, strIconBase, strIconExt, strImageIcon, strSpeed, strMinMax;
    private long strTodayDate;
    private String feels_like, temp_min, temp_max, humidity;
    private SharedPrefManager sharedPrefManager;
    //todo private WeatherViewModel weatherViewModel;
    private AppCompatActivity compatActivity;
    private static final String TAG = "TodayFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        compatActivity = (AppCompatActivity) view.getContext();
        return view;
    }

/*    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_SettingFragment);
            }
        });
    }*/
}