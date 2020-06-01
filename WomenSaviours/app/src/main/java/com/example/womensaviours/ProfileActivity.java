package com.example.womensaviours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensaviours.RoomPack.Contacts;
import com.example.womensaviours.RoomPack.Rdb;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Rdb rdb;
    TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    List<Contacts> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
            list = new ArrayList<>();
            tv = findViewById(R.id.name);
            tv1 = findViewById(R.id.mail);
            tv2 = findViewById(R.id.num);
            tv3 = findViewById(R.id.enum1);
            tv4 = findViewById(R.id.enum2);
            tv5 = findViewById(R.id.enum3);
            tv6 = findViewById(R.id.enum4);
            tv7 = findViewById(R.id.enum5);
            rdb = Room.databaseBuilder(this, Rdb.class, "MYROOM").allowMainThreadQueries().build();
            List<Contacts> dummy = rdb.myDao().getAllData();
            if (dummy.size()==0) {
                Toast.makeText(this, "Please add the profile data first", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProfilesActivity.class));
            } else {
                tv.setText(dummy.get(0).getName());
                tv1.setText(dummy.get(0).getNumber());
                tv2.setText(dummy.get(0).getMail());
                tv3.setText(dummy.get(0).getEn1());
                tv4.setText(dummy.get(0).getEn2());
                tv5.setText(dummy.get(0).getEn3());
                tv6.setText(dummy.get(0).getEn4());
                tv7.setText(dummy.get(0).getEn5());

            }
    }
}
