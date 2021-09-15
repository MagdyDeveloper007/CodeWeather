package developer007.magdy.code95weather.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.data.SharedPrefManager;

public class SettingFragment extends Fragment {

    private EditText etCity, etCity1, etCity2, etCity3, etCity4;
    private TextView tvSettingMetric, tvSettingImperial;
    private LinearLayout layoutBackHome, layoutExit, layoutSet;
    private String strCity, strCity1, strCity2, strCity3, strCity4, strUnit;
    private SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        AppCompatActivity compatActivity = (AppCompatActivity) view.getContext();
        sharedPrefManager = new SharedPrefManager();


        etCity = view.findViewById(R.id.etCity);
        etCity1 = view.findViewById(R.id.etCity1);
        etCity2 = view.findViewById(R.id.etCity2);
        etCity3 = view.findViewById(R.id.etCity3);
        etCity4 = view.findViewById(R.id.etCity4);
        tvSettingMetric = view.findViewById(R.id.tvSettingMetric);
        tvSettingImperial = view.findViewById(R.id.tvSettingImperial);
        layoutBackHome = view.findViewById(R.id.layoutBackHome);
        layoutExit = view.findViewById(R.id.layoutExit);
        layoutSet = view.findViewById(R.id.layoutSet);

        strUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
        strCity = SharedPrefManager.getAuthPref(compatActivity).getString("location", "");
        strCity1 = SharedPrefManager.getAuthPref(compatActivity).getString("location1", "");
        strCity2 = SharedPrefManager.getAuthPref(compatActivity).getString("location2", "");
        strCity3 = SharedPrefManager.getAuthPref(compatActivity).getString("location3", "");
        strCity4 = SharedPrefManager.getAuthPref(compatActivity).getString("location4", "");
        etCity.setText(strCity);
        etCity1.setText(strCity1);
        etCity2.setText(strCity2);
        etCity3.setText(strCity3);
        etCity4.setText(strCity4);

        if (strUnit.equals("metric")) {
            tvSettingMetric.setTextColor(Color.WHITE);
            tvSettingImperial.setTextColor(Color.DKGRAY);
        } else {
            tvSettingMetric.setTextColor(Color.DKGRAY);
            tvSettingImperial.setTextColor(Color.WHITE);
        }


        // set to Imperial unit
        tvSettingImperial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSettingMetric.setTextColor(Color.DKGRAY);
                tvSettingImperial.setTextColor(Color.WHITE);
                strUnit = "imperial";
                SharedPrefManager.setAuthVal(compatActivity, "unit", strUnit);

            }
        });
        //set to metric unit
        tvSettingMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSettingMetric.setTextColor(Color.WHITE);
                tvSettingImperial.setTextColor(Color.DKGRAY);
                strUnit = "metric";
                SharedPrefManager.setAuthVal(compatActivity, "unit", strUnit);
            }
        });

        //check to go back home page
        layoutBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSet.setClickable(true);

                if (TextUtils.isEmpty(strCity)) {
                    new AlertDialog.Builder(compatActivity).setTitle(R.string.error).setMessage(R.string.enter_location)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etCity.setFocusable(true);

                                }
                            }).create().show();
                    layoutSet.setClickable(true);

                    return;
                }
                layoutSet.setClickable(false);

                NavHostFragment.findNavController(SettingFragment.this)
                        .navigate(R.id.action_SettingFragment_to_TodayFragment);
            }
        });
        //exit the app
        layoutExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

//set the desired setting for the default city
        layoutSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCity = etCity.getText().toString().trim();
                strCity1 = etCity1.getText().toString().trim();
                strCity2 = etCity2.getText().toString().trim();
                strCity3 = etCity3.getText().toString().trim();
                strCity4 = etCity4.getText().toString().trim();

                layoutSet.setClickable(true);
                if (TextUtils.isEmpty(strCity)) {
                    new AlertDialog.Builder(compatActivity).setTitle(R.string.error).setMessage(R.string.enter_location)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etCity.setFocusable(true);

                                }
                            }).create().show();
                    layoutSet.setClickable(true);

                    return;
                }

                if (!TextUtils.isEmpty(strCity1)) {
                    setCityToUC(strCity1, compatActivity, "1");
                }
                if (!TextUtils.isEmpty(strCity2)) {
                    setCityToUC(strCity2, compatActivity, "2");

                }
                if (!TextUtils.isEmpty(strCity3)) {
                    setCityToUC(strCity3, compatActivity, "3");

                }
                if (!TextUtils.isEmpty(strCity4)) {
                    setCityToUC(strCity4, compatActivity, "4");

                }

                layoutSet.setClickable(false);
//save city and unit
                strUnit = SharedPrefManager.getAuthPref(compatActivity).getString("unit", "metric");
                //fix the saved city by setting the first letter as an upper case letter for better view
                setCityToUC(strCity, compatActivity, "");
/*
                String toUCFirst = strCity.substring(0, 1).toUpperCase() + strCity.substring(1).toLowerCase();

                SharedPrefManager.setAuthVal(compatActivity, "location", toUCFirst);
*/
                SharedPrefManager.setAuthVal(compatActivity, "unit", strUnit);
                Toast.makeText(compatActivity, R.string.saved, Toast.LENGTH_SHORT).show();

            }
        });

//unfreeze any freezed button
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSet.setClickable(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCity1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSet.setClickable(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCity2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSet.setClickable(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCity3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSet.setClickable(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCity4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSet.setClickable(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void setCityToUC(String city, Context context, String key) {
        String toUCFirst = "";
        toUCFirst = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
        SharedPrefManager.setAuthVal(context, "location" + key, toUCFirst);

    }
}