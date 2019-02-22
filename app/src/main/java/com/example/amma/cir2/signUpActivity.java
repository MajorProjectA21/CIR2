package com.example.amma.cir2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {

    EditText registerNumber, password, confirmPassword, emailId;
    boolean setPType;
    TextView tvshowPassword, tvconfirmPassword;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        registerNumber = findViewById(R.id.etSignUpRegID);
        password = findViewById(R.id.etSignUpPassword);
        confirmPassword = findViewById(R.id.etSignUpConfirmPassword);
        emailId = findViewById(R.id.etSignUpEmail);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserEmailRegistrationTable");

        tvshowPassword = findViewById(R.id.TextViewSignUpShowPassword);
        tvconfirmPassword = findViewById(R.id.TextViewSignUpConfirmPassword);
        setPType = true;

        tvshowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setPType == true) {
                    setPType = false;
                    password.setTransformationMethod(null);
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                    tvshowPassword.setBackgroundResource(R.drawable.ic_hide_password);
                } else {
                    setPType = true;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                    tvshowPassword.setBackgroundResource(R.drawable.ic_show_password);
                }
            }
        });
        tvconfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setPType) {
                    setPType = false;
                    confirmPassword.setTransformationMethod(null);
                    if (confirmPassword.getText().length() > 0)
                        confirmPassword.setSelection(confirmPassword.getText().length());
                    tvconfirmPassword.setBackgroundResource(R.drawable.ic_hide_password);
                } else {
                    setPType = true;
                    confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    if (confirmPassword.getText().length() > 0)
                        confirmPassword.setSelection(confirmPassword.getText().length());
                    tvconfirmPassword.setBackgroundResource(R.drawable.ic_show_password);
                }
            }
        });
    }

    public void onClickSignUp(View view) {
        final String regId, pass, confirmPass, email;

        regId = registerNumber.getText().toString().trim();
        pass = password.getText().toString().trim();
        confirmPass = confirmPassword.getText().toString().trim();
        email = emailId.getText().toString().trim();
        if (isOnline(this)) {

            if (regId.isEmpty()) {
                //Toast.makeText(signUpActivity.this, "Register Number required", Toast.LENGTH_LONG).show();
                registerNumber.requestFocus();
                registerNumber.setError("Register Number required");
                return;
            }
            if (email.isEmpty()) {
                //Toast.makeText(signUpActivity.this, "Please enter the Email ", Toast.LENGTH_LONG).show();
                emailId.requestFocus();
                emailId.setError("Please enter the Email ");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailId.requestFocus();
                emailId.setError("Please enter a valid Email");
                return;
            }

            if (pass.isEmpty()) {
                Toast.makeText(signUpActivity.this, "Enter Password!!!", Toast.LENGTH_LONG).show();
                password.requestFocus();
                //password.setError("Password required");
                return;
            }
            if (pass.length() < 6) {
                Toast.makeText(signUpActivity.this, "Password must be atleast 6 characters long!!!", Toast.LENGTH_LONG).show();
                password.requestFocus();
                // password.setError("Password must be at least 6 characters long!!!");
                return;
            }
            if (confirmPass.isEmpty()) {
                Toast.makeText(signUpActivity.this, "Please enter the Confirmation Password", Toast.LENGTH_LONG).show();
                confirmPassword.requestFocus();
                // confirmPassword.setError("Please enter the Confirmation Password");
                return;
            }
            if (!confirmPass.equals(pass)) {
                Toast.makeText(signUpActivity.this, "Password dosent match,please re-enter", Toast.LENGTH_LONG).show();
                password.setText("");
                confirmPassword.setText("");
                password.requestFocus();
            }
            User user = new User(regId, email);
            databaseReference.setValue(user);
//            mAuth.createUserWithEmailAndPassword(email, pass)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()) {
//
//                                User user = new User(regId, email);
//
//                                FirebaseDatabase.getInstance().getReference("Users")
//                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                     //   progressBar.setVisibility(View.GONE);
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(signUpActivity.this, "registration_success", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(signUpActivity.this, homeActivity.class);
//                                            startActivity(intent);
//                                        } else {
//                                            Toast.makeText(signUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                                            Toast.makeText(signUpActivity.this,
//                                                    "Login unsuccessful: " + task.getException().getMessage(), //ADD THIS
//                                                    Toast.LENGTH_SHORT).show();
//                                           // progressBar.setVisibility(View.GONE);
//                                        }
//                                    }
//                                });
//
//                            } else {
//                                Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                              //  progressBar.setVisibility(View.GONE);
//                            }
//                        }
//                    });
//
//        }
//        else{
//            Toast.makeText(this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
//        }

        }
    }


    public void onClickSignIn(View view) {
        Intent i = new Intent(signUpActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }

    protected boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            int networkType = activeNetwork.getType();
            return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(signUpActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}

