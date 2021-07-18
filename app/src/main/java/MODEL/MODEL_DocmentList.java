package MODEL;

public class MODEL_DocmentList {
    public String getName() {
        return name;
    }

    public MODEL_DocmentList(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String name;
    String date;

}
