package com.example.amma.cir2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class signUpActivity extends AppCompatActivity {

    EditText registerNumber, password, confirmPassword, emailId;
    boolean setPType;
    TextView tvshowPassword, tvconfirmPassword;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseFirestore db;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        registerNumber = findViewById(R.id.etSignUpRegID);
        password = findViewById(R.id.etSignUpPassword);
        confirmPassword = findViewById(R.id.etSignUpConfirmPassword);
        emailId = findViewById(R.id.etSignUpEmail);
        progressBar = findViewById(R.id.SignUpProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
        validation();
    }

    public void validation() {
        registerNumber.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String arg = s.toString().toUpperCase();
                if (!Pattern.matches("[a-zA-Z0-9]+", arg)) {
                    registerNumber.setError("Wrong Register number. No Special Characters Allowed");
                    return;
                }

                if (arg.length() < 14 || !(arg.startsWith("KH"))) {
                    registerNumber.setError("Wrong Register number");
                    return;
                }

//                if(!str.contains("AR") ||
//                        !str.contains("SC") ||
//                        !str.contains("BU")){
//                    registerNumber.setError("Wrong Register number");
//                    return;
//                }
//                if (!str.matches(".*\\d")){
//                    registerNumber.setError("Wrong Register number");
//                    return;
//                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String str = registerNumber.getText().toString().toUpperCase();
                if (!str.matches(".*\\d")) {
                    registerNumber.setError("Wrong Register number");
                    return;
                }
            }
        });
    }

    public void onClickSignUp(View view) {
        final String regId, pass, confirmPass, email;

        regId = registerNumber.getText().toString().toUpperCase().trim();
        pass = password.getText().toString().trim();
        confirmPass = confirmPassword.getText().toString().trim();
        email = emailId.getText().toString().trim();
        if (isOnline(this)) {

            if (regId.isEmpty()) {
                registerNumber.requestFocus();
                registerNumber.setError("Register Number required1");
                return;
            }
            if (regId.length() < 14) {
                registerNumber.setText("");
                registerNumber.requestFocus();
                registerNumber.setError("Register Number Not Correct2");
                return;
            }
            if (!regId.matches(".*\\d")) {
                registerNumber.requestFocus();
                registerNumber.setError("Register Number Not Correct3");
                return;
            }
            if (!Pattern.matches("[a-zA-Z0-9]+", regId)) {
                registerNumber.setText("");
                registerNumber.requestFocus();
                registerNumber.setError("Wrong Register number. No Special Characters Allowed");
                return;
            }

            if (!(regId.startsWith("KH"))) {
                registerNumber.setText("");
                registerNumber.requestFocus();
                registerNumber.setError("Wrong Register number1");
                return;
            }
            if (!(regId.contains("AR")) &&
                    !(regId.contains("SC")) &&
                    !(regId.contains("BU")) &&
                    !(regId.contains("MCA")) &&
                    !(regId.contains("MAT")) &&
                    !(regId.contains("BBA")) &&
                    !(regId.contains("VM")))
            {
                registerNumber.setText("");
                registerNumber.requestFocus();
                registerNumber.setError("Wrong Register number2");
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
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                User user = new User(regId, email);
                                createUserIdRegNo(user);
                                db.collection("USER_EMAIL_REGISTRATION_ID").document(regId).set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(signUpActivity.this, "registration_success", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(signUpActivity.this, homeNav.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                                Toast.makeText(signUpActivity.this,
                                                        "Login unsuccessful: " + e.getMessage(), //ADD THIS
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            } else {
                                Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void createUserIdRegNo(User user) {
        db.collection("User_ID_Registration_ID").document(mAuth.getUid()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(signUpActivity.this, "User_ID_Registration_ID SUCCESS", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        Toast.makeText(signUpActivity.this,
                                "User_ID_Registration_ID unsuccessful: " + e.getMessage(), //ADD THIS
                                Toast.LENGTH_SHORT).show();
                    }
                });
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

