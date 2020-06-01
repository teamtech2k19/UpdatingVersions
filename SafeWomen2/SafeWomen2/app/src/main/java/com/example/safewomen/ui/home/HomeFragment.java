package com.example.safewomen.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.safewomen.GPSTracker;
import com.example.safewomen.ProfilesActivity;
import com.example.safewomen.R;
import com.example.safewomen.RoomPack.Contacts;
import com.example.safewomen.RoomPack.Rdb;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ImageButton ib;
    GPSTracker gps;
    Rdb rdb;
    String en1, en2, en3, en4, en5;
    List<Contacts> list;

    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<>();
        gps = new GPSTracker(getContext());
        ib = root.findViewById(R.id.alert);
        rdb = Room.databaseBuilder(getContext(), Rdb.class, "MYROOM").allowMainThreadQueries().build();
        List<Contacts> dummy = rdb.myDao().getAllData();
        if (dummy.size() == 0) {
            Toast.makeText(getContext(), "Please add the profile data first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), ProfilesActivity.class));
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
                    Toast.makeText(getContext(), "Alert message sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;

    }
}