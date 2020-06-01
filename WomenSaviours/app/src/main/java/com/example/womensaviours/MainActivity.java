package com.example.womensaviours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    LinearLayout llayout,rlayout,wlayout,relayout;
    TextView lb,pb,rb;
    EditText uname,upass;
    FirebaseAuth auth;
    ProgressDialog pd;
    ImageButton signup,reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llayout = findViewById(R.id.llayout);
        rlayout = findViewById(R.id.rlayout);
        wlayout = findViewById(R.id.wlayout);
        relayout = findViewById(R.id.relayout);
        lb = findViewById(R.id.lb);
        rb = findViewById(R.id.rb);
        pb = findViewById(R.id.pb);
        uname = findViewById(R.id.lemail);
        upass = findViewById(R.id.lpass);
        signup = findViewById(R.id.signup);
        reset = findViewById(R.id.reset);
        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PhoneActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText uname, email, pass, repass;
                uname = findViewById(R.id.name);
                email = findViewById(R.id.email);
                pass = findViewById(R.id.pass);
                repass = findViewById(R.id.repass);
                if (isConnected()) {
                    final String name = uname.getText().toString().trim();
                    String mail = email.getText().toString().trim();
                    String password = pass.getText().toString().trim();
                    String repassword = repass.getText().toString().trim();
                    if (TextUtils.isEmpty(mail)) {
                        Toast.makeText(MainActivity.this, "Email Cant be Empty", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(MainActivity.this, "Password cant be Empty ", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (password.equals(repassword)) {
                        pd.setMessage("Registering please wait.....");
                        pd.show();
                        auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    i.putExtra("name", name);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "Registartion Failed", Toast.LENGTH_LONG).show();
                                }
                                finish();
                            }
                        });
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  EditText email;
                email = findViewById(R.id.reemail);
                if(isConnected()){
                    String rmail = email.getText().toString().trim();
                    if (TextUtils.isEmpty(rmail)) {
                        Toast.makeText(MainActivity.this,"Email cant be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.sendPasswordResetEmail(rmail).addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this,"Failed to Reset Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void login(View view) {
        rlayout.setVisibility(LinearLayout.GONE);
        llayout.setVisibility(LinearLayout.VISIBLE);
        wlayout.setVisibility(LinearLayout.GONE);
        relayout.setVisibility(LinearLayout.GONE);
        lb.setBackground(getResources().getDrawable(R.drawable.shapes));
        pb.setBackgroundResource(0);
        rb.setBackgroundResource(0);
    }
    public void register(View view) {
        llayout.setVisibility(LinearLayout.GONE);
        rlayout.setVisibility(LinearLayout.VISIBLE);
        wlayout.setVisibility(LinearLayout.GONE);
        relayout.setVisibility(LinearLayout.GONE);
        lb.setBackgroundResource(0);
        pb.setBackgroundResource(0);
        rb.setBackground(getResources().getDrawable(R.drawable.shapes));
    }
    public void reset(View view) {
        relayout.setVisibility(LinearLayout.VISIBLE);
        llayout.setVisibility(LinearLayout.GONE);
        wlayout.setVisibility(LinearLayout.GONE);
        rlayout.setVisibility(LinearLayout.GONE);
        lb.setBackgroundResource(0);
        rb.setBackgroundResource(0);
        pb.setBackground(getResources().getDrawable(R.drawable.shapes));
    }

    public void signin(View view) {
        String email = uname.getText().toString().trim();
        String password = upass.getText().toString().trim();

        if (isConnected()) {

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this,"Email can't be empty", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this,"Password can't be empty", Toast.LENGTH_LONG).show();
                return;
            }
            pd.setMessage("Please Wait.....");
            pd.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
        }

    }




     public boolean isConnected() {
                boolean connected = false;
                try {
                    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo nInfo = manager.getActiveNetworkInfo();
                    connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                    return connected;
                } catch (Exception e) {

                }
                return connected;
            }
}
    
