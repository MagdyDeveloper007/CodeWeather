package developer007.magdy.code95weather.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    //authentication
    public static SharedPreferences getAuthPref(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);

        return pref;
    }

    //authentication
    public static void setAuthVal(Context mContext, String key, String value) {
        if (key != null) {
            SharedPreferences.Editor authEdit = getAuthPref(mContext).edit();
            authEdit.putString(key, value);
            authEdit.apply();
        }
    }
}

/*
hint
1) unit: imperial or metric
2) location, location1, location2, location3, location4,
 */