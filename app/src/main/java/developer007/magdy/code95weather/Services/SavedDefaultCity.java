package developer007.magdy.code95weather.Services;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import developer007.magdy.code95weather.modules.SavedModule.SavedWeatherDefaultCityModule;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface SavedDefaultCity {


    @Insert()
    Completable insertData(SavedWeatherDefaultCityModule database);

    @Query("select * from default_table")
    Single<List<SavedWeatherDefaultCityModule>> getSavedForecast();

    @Query("delete from default_table")
    void deleteCurrentTable();
}
