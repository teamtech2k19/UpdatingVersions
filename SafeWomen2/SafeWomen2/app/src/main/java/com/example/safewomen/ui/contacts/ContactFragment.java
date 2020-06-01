package com.example.safewomen.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.safewomen.ProfilesActivity;
import com.example.safewomen.R;
import com.example.safewomen.RoomPack.Contacts;
import com.example.safewomen.RoomPack.Rdb;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    Rdb rdb;
    TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    List<Contacts> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);
        list = new ArrayList<>();
        tv = root.findViewById(R.id.name);
        tv1 = root.findViewById(R.id.mail);
        tv2 = root.findViewById(R.id.num);
        tv3 = root.findViewById(R.id.enum1);
        tv4 = root.findViewById(R.id.enum2);
        tv5 = root.findViewById(R.id.enum3);
        tv6 = root.findViewById(R.id.enum4);
        tv7 = root.findViewById(R.id.enum5);
        rdb = Room.databaseBuilder(getContext(), Rdb.class, "MYROOM").allowMainThreadQueries().build();
        List<Contacts> dummy = rdb.myDao().getAllData();
        if (dummy.size()==0) {
            Toast.makeText(getContext(), "Please add the profile data first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), ProfilesActivity.class));
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
        return root;
    }
}