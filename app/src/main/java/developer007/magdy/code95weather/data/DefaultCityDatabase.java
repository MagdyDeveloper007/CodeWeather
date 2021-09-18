package developer007.magdy.code95weather.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import developer007.magdy.code95weather.Services.SavedDefaultCity;
import developer007.magdy.code95weather.modules.SavedModule.SavedWeatherDefaultCityModule;


@Database(entities = SavedWeatherDefaultCityModule.class, version = 4)
public abstract class DefaultCityDatabase extends RoomDatabase {

    private static DefaultCityDatabase instance;

    public abstract SavedDefaultCity savedDefaultCity();


    public static synchronized DefaultCityDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DefaultCityDatabase.class,"default_table")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
