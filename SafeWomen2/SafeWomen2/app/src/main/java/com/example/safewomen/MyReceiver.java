package com.example.safewomen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.example.safewomen.RoomPack.Contacts;
import com.example.safewomen.RoomPack.Rdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyReceiver extends BroadcastReceiver {
    private static final String ACTION_ALERT =
            BuildConfig.APPLICATION_ID + ".ALERT";
    private static final String ACTION_CANCEL =
            BuildConfig.APPLICATION_ID + ".CANCEL";
    private static final String ACTION_LATER =
            BuildConfig.APPLICATION_ID + ".LATER";

    GPSTracker gps;
    Rdb rdb;
    String en1,en2,en3,en4,en5;
    List<Contacts> list;
    @Override
    public void onReceive(Context context, Intent intent) {
        list = new ArrayList<>();
        rdb = Room.databaseBuilder(context, Rdb.class, "MYROOM").allowMainThreadQueries().build();
        List<Contacts> dummy = rdb.myDao().getAllData();
        if (dummy.size() == 0) {
            Toast.makeText(context, "Please add the profile data first", Toast.LENGTH_SHORT).show();
        } else {
            en1 = dummy.get(0).getEn1();
            en2 = dummy.get(0).getEn2();
            en3 = dummy.get(0).getEn3();
            en4 = dummy.get(0).getEn4();
            en5 = dummy.get(0).getEn5();
        }
       String action = intent.getAction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gps = new GPSTracker(context);
        }
        switch (action){
           case ACTION_ALERT:
               String num[] = {en1, en2, en3, en4, en5};
               String sms = "i am in danger :";
               double latitude = gps.getLatitude();
               double longitude = gps.getLongitude();
               SmsManager smsManager = SmsManager.getDefault();
               for (int j = 0; j < 5; j++) {
                   smsManager.sendTextMessage("" + num[j], null, sms + "https://www.google.com/maps/@" + latitude + "," + longitude + ",18z", null, null);
                   Toast.makeText(context, "Alert message sent!", Toast.LENGTH_SHORT).show();
               }
               break;
           case ACTION_CANCEL:
               NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
               manager.cancel(Integer.parseInt("258"));
               Toast.makeText(context, "Alert Cancelled", Toast.LENGTH_SHORT).show();
               break;
           case ACTION_LATER:
               Toast.makeText(context, "Alert Postponed", Toast.LENGTH_SHORT).show();
               break;
       }
    }
}
