package tech.spirit.woshield;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static tech.spirit.woshield.Application_Class.firebaseUser;


public class MainActivity extends AppCompatActivity implements Help_Adapter.ItemClicked{

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ChildEventListener childEventListener;

    private FusedLocationProviderClient fusedLocationClient;

    private static final int REQUEST_PERMISSIONS = 100;

    public final static int REQUEST_CODE = 10101;

    boolean shareLocation=false;

    boolean boolean_permission;
    TextView tv_latitude, tv_longitude, tv_address,tv_area,tv_locality;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;


    TimerTask timerTask;
    Button btnlocation,showOnMap;
    boolean flagI;
    String formattedDate;

public ArrayList<Help_Location> help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        Log.i("dATE ",formattedDate);
//        // formattedDate have current date/time
//        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

        help=new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Help_Adapter(MainActivity.this, help);
        recyclerView.setAdapter(adapter);


        btnlocation=findViewById(R.id.btnlocation);
        showOnMap=findViewById(R.id.show);



        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("messages");

        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Help_Location h=dataSnapshot.getValue(Help_Location.class);
                help.add(h);
                Log.i("data", ""+h.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);




        if (checkDrawOverlayPermission()) {
            startService(new Intent(this, PowerButtonService.class));
        }

        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();

        fn_permission();

        resheduleTime(5000);

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(boolean_permission) {
                    shareLocation=true;
                    Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                    startService(intent);
                }


//                    startActivity(new Intent(MainActivity.this,MapsActivity.class));
//                    if (mPref.getString("service", "").matches("")) {
////                        medit.putString("service", "service").commit();
//
//
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Service is already running", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
//                }
                }


        });


        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareLocation) {
                    Intent intent=new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("show map",0);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please first click on start sharing location ", Toast.LENGTH_LONG).show();
                }
            }

            });





    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {


            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            Application_Class.location=new LatLng(latitude,longitude);
            Application_Class.yourLocation=new LatLng(latitude,longitude);
            Log.d("location",""+latitude+longitude);
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
//
//                tv_area.setText(addresses.get(0).getAdminArea());
//                tv_locality.setText(stateName);
//                tv_address.setText(countryName);

                Log.d("Address", cityName + "" + countryName + "" + stateName);


            } catch (IOException e1) {
                e1.printStackTrace();
            }


//            tv_latitude.setText(latitude+"");
//            tv_longitude.setText(longitude+"");
//            tv_address.getText();


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }




    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, PowerButtonService.class));
            }
        }
    }


    public void sendData() {

        if (latitude != null && longitude != null) {

            Help_Location help_location = new Help_Location(firebaseUser.getDisplayName(), firebaseUser.getEmail(), "I am in trouble " +
                    " Please someone Help Me ", latitude, longitude, formattedDate);

            Toast.makeText(MainActivity.this, " Alert messsage has been sent", Toast.LENGTH_SHORT).show();

            databaseReference.push().setValue(help_location);
        }else{
            Toast.makeText(this, "First click on start sending location", Toast.LENGTH_SHORT).show();
        }
    }
    public void resheduleTime( int duration){
        Timer timer=new Timer();
        timerTask=new MyTimerTask();
        timer.schedule(timerTask,duration,duration);

    }



    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {


            if (Application_Class.COUNT >= 10) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        sendData();

                    }
                });
                flagI = true;
            } else {

                flagI = false;
            }
            Application_Class.COUNT=0;
        }
    }


    @Override
    public void onItemClicked(int index) {

        if (shareLocation) {
            Toast.makeText(this, "" + help.get(index).getMessage(), Toast.LENGTH_SHORT).show();
            Application_Class.location = new LatLng(help.get(index).getLati(), help.get(index).getLongi());
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("show map", 1);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please first click on start sharing location", Toast.LENGTH_SHORT).show();
        }
    }

}
