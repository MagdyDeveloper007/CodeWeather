package developer007.magdy.code95weather.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static SharedPreferences getAuthPref(Context mContext) {

        return mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static void setAuthVal(Context mContext, String key, String value) {
        if (key != null) {
            SharedPreferences.Editor authEdit = getAuthPref(mContext).edit();
            authEdit.putString(key, value);
            authEdit.apply();
        }
    }

    public static void deleteAuthVal(Context mContext, String key) {
        SharedPreferences.Editor editor = getAuthPref(mContext).edit();
        editor.remove(key);
        editor.apply();
    }

}

/*
hint
1) unit: imperial or metric
2) location, location1, location2, location3, location4,
 */