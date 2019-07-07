package tech.spirit.woshield;

import androidx.fragment.app.FragmentActivity;


import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;



import static tech.spirit.woshield.Application_Class.location;
import static tech.spirit.woshield.Application_Class.yourLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {





    private GoogleMap mMap;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

      flag=getIntent().getIntExtra("show map",0);






    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (flag == 0) {

            if (mMap != null) {

                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.you))
                        .title("You")
                        .position(yourLocation));

                CameraPosition camera = new CameraPosition.Builder()
                        .target(yourLocation)
                        .zoom(15)
                        .bearing(0)
                        .tilt(30)
                        .build();

                // Add a marker in Sydney and move the camera
                //LatLng sydney = new LatLng(Application_Class.address_location.getLat(), Application_Class.address_location.getLang());
                //mMap.addMarker(new MarkerOptions().position(location).title("Location"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
            }
        } else if (flag == 1) {


            if (mMap != null) {

                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.you))
                        .title("You")
                        .position(yourLocation));

                mMap.addMarker(new MarkerOptions()
                        .title("Please Help Me")
                        .position(location));

                CameraPosition camera = new CameraPosition.Builder()
                        .target(location)
                        .zoom(15)
                        .bearing(0)
                        .tilt(30)
                        .build();

                // Add a marker in Sydney and move the camera
                //LatLng sydney = new LatLng(Application_Class.address_location.getLat(), Application_Class.address_location.getLang());
                //mMap.addMarker(new MarkerOptions().position(location).title("Location"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

            }
        }
    }



}
