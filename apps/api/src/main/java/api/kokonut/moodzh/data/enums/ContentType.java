package api.kokonut.moodzh.data.enums;

public enum ContentType {
    LOCAL,
    EXTERNAL;

    public String getContentType() {
        return this.name().toLowerCase();
    }
}
