package developer007.magdy.code95weather.Services;

public class API {
    String apId = "f13345c6a98d6840d6c8707ef3d1aff0";
    String baseUrl = "https://api.openweathermap.org/data/2.5/";
    String imgExtUrl = "@2x.png";
    String imageURl = "https://openweathermap.org/img/wn/";

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getImgExtUrl() {
        return imgExtUrl;
    }

    public void setImgExtUrl(String imgExtUrl) {
        this.imgExtUrl = imgExtUrl;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}
