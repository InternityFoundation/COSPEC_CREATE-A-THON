package tech.spirit.woshield;

import com.google.android.gms.maps.model.LatLng;

public class Help_Location {

    private String userName,userEmail,message;
//    private LatLng cordinates;
    private double lati,longi;
    private String dateTime;

    Help_Location(){
    }

    public Help_Location(String userName, String userEmail, String message, double lati, double longi, String dateTime) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.message = message;
        this.lati = lati;
        this.longi = longi;
        this.dateTime = dateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
