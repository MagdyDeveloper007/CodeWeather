package developer007.magdy.code95weather.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import developer007.magdy.code95weather.R;

public class MainActivity extends AppCompatActivity {
    private TextView tvDisConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisConnected = findViewById(R.id.tvDisConnected);

        checkConnection();
        tvDisConnected.setOnClickListener(v -> {
            checkConnection();
            recreate();
        });

    }

    public void checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


            tvDisConnected.setVisibility(View.GONE);

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, R.string.disconnected, Toast.LENGTH_SHORT).show();
            tvDisConnected.setVisibility(View.VISIBLE);
        }
    }

}