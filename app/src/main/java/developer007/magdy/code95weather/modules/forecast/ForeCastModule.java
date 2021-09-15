package developer007.magdy.code95weather.modules.forecast;

import java.util.List;

public class ForeCastModule {
    private String cod;
    private List<ForeCastList> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<ForeCastList> getList() {
        return list;
    }

    public void setList(List<ForeCastList> list) {
        this.list = list;
    }
}
