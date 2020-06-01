package com.example.safewomen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthenticationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private ProgressDialog pd;
    EditText uname, upass,unumber,code;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        auth = FirebaseAuth.getInstance();
        uname = findViewById(R.id.uname);
        //unumber = findViewById(R.id.number);
        upass = findViewById(R.id.upass);
        pd = new ProgressDialog(this);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
       /* mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                   unumber.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        }

        ;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(AuthenticationActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                code.setError("Invalid code.");
                            }
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = unumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            unumber.setError("Invalid phone number.");
            return false;
        }
        return true;
    }
*/
    }
    public void login(View view) {
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
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            } else {
                                Toast.makeText(AuthenticationActivity.this, "Authenticatio Failed", Toast.LENGTH_SHORT).show();
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


   /* public void number(View view) {
        if (!validatePhoneNumber()){
            return;
        }
        //String n = unumber.getText().toString();
        startPhoneNumberVerification(unumber.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_phone, null);
        builder.setCancelable(false);
        builder.setView(v);
        code = v.findViewById(R.id.code);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String c = code.getText().toString().trim();
                if (TextUtils.isEmpty(c)) {
                    code.setError("cant be empty");
                } else {
                        code.setError("Cannot be empty.");
                        return;
                    }

                    verifyPhoneNumberWithCode(mVerificationId, c);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Resend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resendVerificationCode(unumber.getText().toString(), mResendToken);
            }
        });
        builder.show();
    }*/

    public void reset(View view) {
        final EditText email;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_reset, null);
        builder.setCancelable(false);
        builder.setView(v);
        email = v.findViewById(R.id.rmail);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if(isConnected()){
                    String rmail = email.getText().toString().trim();
                    if (TextUtils.isEmpty(rmail)) {
                        Toast.makeText(AuthenticationActivity.this,"Email cant be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.sendPasswordResetEmail(rmail).addOnCompleteListener(AuthenticationActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AuthenticationActivity.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(AuthenticationActivity.this,"Failed to Reset Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(AuthenticationActivity.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
                }}
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.register:
                final EditText uname, email, pass, repass;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.activity_register, null);
                builder.setCancelable(false);
                builder.setView(v);
                uname = v.findViewById(R.id.name);
                email = v.findViewById(R.id.email);
                pass = v.findViewById(R.id.pass);
                repass = v.findViewById(R.id.repass);
                builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnected()) {
                            final String name = uname.getText().toString().trim();
                            String mail = email.getText().toString().trim();
                            String password = pass.getText().toString().trim();
                            String repassword = repass.getText().toString().trim();
                            if (TextUtils.isEmpty(mail)) {
                                Toast.makeText(AuthenticationActivity.this, "Email Cant be Empty", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(AuthenticationActivity.this, "Password cant be Empty ", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (password.equals(repassword)) {
                                pd.setMessage("Registering please wait.....");
                                pd.show();
                                auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(AuthenticationActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            finish();
                                            Intent i = new Intent(AuthenticationActivity.this, HomeActivity.class);
                                            i.putExtra("name", name);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(AuthenticationActivity.this, "Registartion Failed", Toast.LENGTH_LONG).show();
                                        }
                                        finish();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(AuthenticationActivity.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
                        }
                    }});
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}

