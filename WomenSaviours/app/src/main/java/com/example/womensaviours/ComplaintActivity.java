package com.example.womensaviours;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity{

    Spinner sp;
    EditText subject,complaint;
    String ctype,s,c;
    int p ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("complaint");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        subject = findViewById(R.id.subject);
        complaint = findViewById(R.id.complaint);
        sp = findViewById(R.id.spin);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }


    public void csubmit(View view) {
         s = subject.getText().toString();
        c = complaint.getText().toString();
        if (!(p == 0)) {
            Object item = sp.getItemAtPosition(p);
            ctype = item.toString();
            String uploadId = reference.push().getKey();
            Upload upload = new Upload(s,c,ctype);
            reference.child(uploadId).setValue(upload);
            Toast.makeText(ComplaintActivity.this, "Your Complaint Has Been Submitted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ComplaintActivity.this, "Please Select Crime Type", Toast.LENGTH_SHORT).show();
        }


    }


}
