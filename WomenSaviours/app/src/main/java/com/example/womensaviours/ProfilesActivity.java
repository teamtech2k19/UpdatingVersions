package com.example.womensaviours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.womensaviours.RoomPack.Contacts;
import com.example.womensaviours.RoomPack.Rdb;

import java.util.List;

public class ProfilesActivity extends AppCompatActivity {
    Rdb rdb;
    List<Contacts> contacts;
    EditText name,mail,num,en1,en2,en3,en4,en5;
    ImageButton idloc;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gps = new GPSTracker(this);
        }
        rdb = Room.databaseBuilder(this,Rdb.class,"MYROOM").allowMainThreadQueries().build();
        contacts = rdb.myDao().getAllData();
        name  = findViewById(R.id.ename);
        idloc = findViewById(R.id.idloc);
        mail = findViewById(R.id.emmail);
        num = findViewById(R.id.emnum);
        en1  = findViewById(R.id.emnum1);
        en2  = findViewById(R.id.emnum2);
        en3  = findViewById(R.id.emnum3);
        en4  = findViewById(R.id.emnum4);
        en5  = findViewById(R.id.emnum5);
        idloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   double lat = gps.getLatitude();
                double lon= gps.getLongitude();
                Contacts d = new Contacts();

                d.setLat(lat);
                d.setLon(lon);
                rdb.myDao().insert(d);*/
                Toast.makeText(ProfilesActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void submit(View view) {
        Contacts d = new Contacts();
        String p="^[6-9][0-9]{9}$";
        String n = name.getText().toString().trim();
        String m = mail.getText().toString().trim();
        String nu = num.getText().toString().trim();
        String e1 = en1.getText().toString().trim();
        String e2 = en2.getText().toString().trim();
        String e3 = en3.getText().toString().trim();
        String e4 = en4.getText().toString().trim();
        String e5 = en5.getText().toString().trim();
        if(n.isEmpty()||m.isEmpty()||nu.isEmpty()||e1.isEmpty()||e2.isEmpty()||e3.isEmpty()||e4.isEmpty()||e5.isEmpty()){
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
        else if(!nu.matches(p)||!e1.matches(p)||!e2.matches(p)||!e3.matches(p)||!e4.matches(p)||!e5.matches(p)){
            Toast.makeText(this, "Enter valid numbers without country code or zero", Toast.LENGTH_SHORT).show();
        }else if(nu.equals(e1)){

        }
        else {
            d.setName(n);
            d.setMail(m);
            d.setNumber(nu);
            d.setEn1(e1);
            d.setEn2(e2);
            d.setEn3(e3);
            d.setEn4(e4);
            d.setEn5(e5);
            rdb.myDao().insert(d);
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }
}
