package com.example.safewomen.ui.contactus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.safewomen.R;
import com.example.safewomen.RoomPack.Contacts;
import com.example.safewomen.RoomPack.Rdb;

import java.util.List;

public class ContactusFragment extends Fragment {

    private ContactusViewModel shareViewModel;
    ImageButton ibutton;
    Rdb rdb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ContactusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        //ibutton = root.findViewById(R.id.ibutton);
        final TextView textView = root.findViewById(R.id.text_share);
        rdb = Room.databaseBuilder(getContext(), Rdb.class, "MYROOM").allowMainThreadQueries().build();
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        List<Contacts> dummy = rdb.myDao().getAllData();
        if (dummy.size()==0) {
            Toast.makeText(getContext(), "Please add the profile data first", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,.class));
        } else {

        }
            return root;
    }
}