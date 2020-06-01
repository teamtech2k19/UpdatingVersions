package com.example.safewomen.ui.feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safewomen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {


    RatingBar rb;
    Button b;
    DatabaseReference reference;
    FirebaseAuth auth;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        rb = root.findViewById(R.id.ratingBar);
        b = root.findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();
        editText = root.findViewById(R.id.suggest);
        reference = FirebaseDatabase.getInstance().getReference("Rating");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating=String.valueOf(rb.getRating());
                String sugg = editText.getText().toString();
                Toast.makeText(getContext(), "Feedback Sent.Thank you!!!", Toast.LENGTH_LONG).show();
                String userId = reference.push().getKey();
                Pojo p = new Pojo(rating,""+auth.getCurrentUser(),sugg);
                reference.child(userId).setValue(p);

            }
        });
        return root;
    }
}