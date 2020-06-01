package com.example.womensaviours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensaviours.RoomPack.Contacts;
import com.example.womensaviours.RoomPack.Rdb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{
    Button cp, ca, cf, cnen;
    ImageButton ib;
    GPSTracker gps;
    Rdb rdb;
    TextView uname;
    String en1, en2, en3, en4, en5;
    List<Contacts> list;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        FirebaseUser u = auth.getCurrentUser();
        String user = u.getEmail();
        uname = findViewById(R.id.uname);
        uname.setText(user);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.activity_ecall, null);
                b.setCancelable(true);
                b.setView(v);
                cp = v.findViewById(R.id.cpolice);
                ca = v.findViewById(R.id.cambulance);
                cf = v.findViewById(R.id.cfire);
                cnen = v.findViewById(R.id.cnen);
                cp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Calling Police", Toast.LENGTH_SHORT).show();
                        Uri up = Uri.parse("tel:" + "100");
                        Intent ip = new Intent(Intent.ACTION_CALL, up);
                        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(ip);
                    }
                });
                ca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Calling Ambulance", Toast.LENGTH_SHORT).show();
                        Uri up = Uri.parse("tel:" + "102");
                        Intent ip = new Intent(Intent.ACTION_CALL, up);
                        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(ip);
                    }
                });
                cf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Calling Fire Station", Toast.LENGTH_SHORT).show();
                        Uri up = Uri.parse("tel:" + "101");
                        Intent ip = new Intent(Intent.ACTION_CALL, up);
                        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(ip);
                    }
                });
                cnen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Calling National Emergency Number", Toast.LENGTH_SHORT).show();
                        Uri up = Uri.parse("tel:" + "112");
                        Intent ip = new Intent(Intent.ACTION_CALL, up);
                        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(ip);
                    }
                });

                b.show();
            }
        });
        list = new ArrayList<>();
        gps = new GPSTracker(this);
        ib = findViewById(R.id.alert);
        rdb = Room.databaseBuilder(this, Rdb.class, "MYROOM").allowMainThreadQueries().build();
        List<Contacts> dummy = rdb.myDao().getAllData();
        if (dummy.size() == 0) {
            Toast.makeText(this, "Please add the profile data first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ProfilesActivity.class));
        } else {
            en1 = dummy.get(0).getEn1();
            en2 = dummy.get(0).getEn2();
            en3 = dummy.get(0).getEn3();
            en4 = dummy.get(0).getEn4();
            en5 = dummy.get(0).getEn5();
        }

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num[] = {en1, en2, en3, en4, en5};
                String sms = "i am in danger :";
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                SmsManager smsManager = SmsManager.getDefault();
                for (int j = 0; j < 5; j++) {
                    smsManager.sendTextMessage("" + num[j], null, sms + "https://www.google.com/maps/@" + latitude + "," + longitude + ",18z", null, null);
                    Toast.makeText(HomeActivity.this, "Alert message sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loc(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void profile(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }


    public void complaint(View view) {
        startActivity(new Intent(this,ComplaintActivity.class));
    }

    public void amb(View view) {
        startActivity(new Intent(this,AmbulanceActivity.class));
    }

}
