package tech.spirit.woshield;

public class Address_Location {

    private Double Lat,Lang;
    private String username;
    private String userEmail;

    public Address_Location(Double lat, Double lang, String username, String userEmail) {
        Lat = lat;
        Lang = lang;
        this.username = username;
        this.userEmail = userEmail;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLang() {
        return Lang;
    }

    public void setLang(Double lang) {
        Lang = lang;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
