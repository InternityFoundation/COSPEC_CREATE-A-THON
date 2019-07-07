package tech.spirit.woshield;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

public class Application_Class extends Application {
    public static double lat, longi;
    public static LatLng location=new LatLng(23.23,24.3);
    public static LatLng yourLocation=new LatLng(10.2,20.3);
    public static int COUNT=0;

public static FirebaseUser firebaseUser;
    @Override
    public void onCreate() {
        super.onCreate();



    }
}
