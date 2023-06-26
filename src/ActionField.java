public class ActionField extends Field {
    private String info;

    public ActionField(String type, String info) {
        super(type);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}