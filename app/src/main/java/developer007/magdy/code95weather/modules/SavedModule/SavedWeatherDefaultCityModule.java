package developer007.magdy.code95weather.modules.SavedModule;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "default_table")
public class SavedWeatherDefaultCityModule {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String strImageIcon;
    private String strOfflineDegree;
    private String strOfflineDesc;
    private String date;
    private String strSpeed;
    private String feels_like;
    private String strMinMax;
    private String humidity;

    public SavedWeatherDefaultCityModule(String strImageIcon,
                                         String strOfflineDegree, String strOfflineDesc,
                                         String date, String strSpeed, String feels_like,
                                         String strMinMax, String humidity) {
        this.strImageIcon = strImageIcon;
        this.strOfflineDegree = strOfflineDegree;
        this.strOfflineDesc = strOfflineDesc;
        this.date = date;
        this.strSpeed = strSpeed;
        this.feels_like = feels_like;
        this.strMinMax = strMinMax;
        this.humidity = humidity;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrImageIcon() {
        return strImageIcon;
    }

    public void setStrImageIcon(String strImageIcon) {
        this.strImageIcon = strImageIcon;
    }

    public String getStrOfflineDegree() {
        return strOfflineDegree;
    }

    public void setStrOfflineDegree(String strOfflineDegree) {
        this.strOfflineDegree = strOfflineDegree;
    }

    public String getStrOfflineDesc() {
        return strOfflineDesc;
    }

    public void setStrOfflineDesc(String strOfflineDesc) {
        this.strOfflineDesc = strOfflineDesc;
    }

    public String getStrSpeed() {
        return strSpeed;
    }

    public void setStrSpeed(String strSpeed) {
        this.strSpeed = strSpeed;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getStrMinMax() {
        return strMinMax;
    }

    public void setStrMinMax(String strMinMax) {
        this.strMinMax = strMinMax;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
