package com.example.safewomen;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.safewomen.RoomPack.Contacts;
import com.example.safewomen.RoomPack.Rdb;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener {


    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback callback;
    String CHANNEL_ID = "258";
    private FusedLocationProviderClient client;
    private Marker marker;
    private DatabaseReference reference;
    private MyReceiver mReceiver;
    private GeoFire fire;
    private List<LatLng> geoareas;
    private Boolean c;
    double l1,l2;
    Rdb rdb;
    private static final String ACTION_ALERT =
            BuildConfig.APPLICATION_ID + ".ALERT";
    private static final String ACTION_CANCEL =
            BuildConfig.APPLICATION_ID + ".CANCEL";
    private static final String ACTION_LATER =
            BuildConfig.APPLICATION_ID + ".LATER";
    List<Contacts> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mReceiver = new MyReceiver();
        c = false;
        list = new ArrayList<>();
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
                        registerReceiver(mReceiver,new IntentFilter(ACTION_ALERT));
                        registerReceiver(mReceiver,new IntentFilter(ACTION_LATER));
                        registerReceiver(mReceiver,new IntentFilter(ACTION_CANCEL));

                        rdb = Room.databaseBuilder(MapsActivity.this, Rdb.class, "MYROOM").allowMainThreadQueries().build();
                        List<Contacts> dummy = rdb.myDao().getAllData();
                        if (dummy.size() == 0) {
                            Toast.makeText(MapsActivity.this, "Please add the profile data first", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MapsActivity.this, ProfilesActivity.class));
                        } else {
                            l1 = dummy.get(0).getLat();
                            l2 = dummy.get(0).getLon();
                        }
                        buildLCallback();
                        buildLocation();
                        intiArea();
                        setgeofire();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MapsActivity.this, "You must enable permission", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    private void intiArea() {
        geoareas = new ArrayList<>();
        geoareas.add(new LatLng(18.5657103,84.1971195));
       geoareas.add(new LatLng(l1,l2));
        geoareas.add(new LatLng(18.789196, 84.429522));
      geoareas.add(new LatLng(18.4090455, 83.903707));
    }

    private void setgeofire() {
        reference = FirebaseDatabase.getInstance().getReference("MyLocations");
        fire = new GeoFire(reference);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (client != null) {
            client.requestLocationUpdates(locationRequest, callback, Looper.myLooper());
        }
        for (LatLng latLng : geoareas) {
            mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeColor(Color.GREEN).fillColor(0x2200ff00).strokeWidth(5.0f));
            GeoQuery geoQuery = fire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 1.0f);
            geoQuery.addGeoQueryEventListener(MapsActivity.this);

        }
    }

    private void buildLocation() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.removeLocationUpdates(callback);


    }

    private void buildLCallback() {
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if (mMap != null) {
                    fire.setLocation("You", new GeoLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (marker == null) {
                                //marker.remove();
                                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()))
                                        .title("You"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0f));
                            }
                        }
                    });

                }
            }
        };
    }


    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        sendNotification("Savior",String.format("%s Entered Safe area",key));
    }



    @Override
    public void onKeyExited(final String key) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification("Savior",String.format("%s Not in the Safe area from last 15 minutes",key));
            }
        },15*60*1000 );

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        if(c=false) {
            sendNotification("Savior", String.format("%s Moved from the Safe area", key));
            c = true;
        }

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
    private void sendNotification(String title, String content) {

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(CHANNEL_ID,"Notify",NotificationManager.IMPORTANCE_DEFAULT);
            nc.setDescription("Channel Description");
            nc.enableLights(true);
            nc.setLightColor(Color.RED);
            nc.setVibrationPattern(new long[]{0,1000,500,1000});
            nc.enableVibration(true);
            nm.createNotificationChannel(nc);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Intent i = new Intent(this,MapsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(false);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentIntent(pi);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));

        Intent a = new Intent(ACTION_ALERT);
        PendingIntent pia = PendingIntent.getBroadcast(this,0,a,PendingIntent.FLAG_ONE_SHOT);
        builder.addAction(R.drawable.ic_alert,"Alert",pia);

        Intent l = new Intent(ACTION_LATER);
        PendingIntent pil = PendingIntent.getBroadcast(this,0,l,PendingIntent.FLAG_ONE_SHOT);
        builder.addAction(R.drawable.ic_later,"Later",pil);

        Intent c = new Intent(ACTION_CANCEL);
        PendingIntent pic = PendingIntent.getBroadcast(this,0,c,PendingIntent.FLAG_ONE_SHOT);
        builder.addAction(R.drawable.ic_stop,"Cancel",pic);

        nm.notify(0,builder.build());

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();

    }
}
