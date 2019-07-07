package tech.spirit.woshield;

import com.google.android.gms.maps.model.LatLng;

public class Help_Location {

    private String userName,userEmail,message;
    private LatLng cordinates;

    public Help_Location(String userName, String userEmail, String message, LatLng cordinates) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.message = message;
        this.cordinates = cordinates;
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

    public LatLng getCordinates() {
        return cordinates;
    }

    public void setCordinates(LatLng cordinates) {
        this.cordinates = cordinates;
    }
}
