package com.example.amma.cir2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    public User user;
    public DatabaseReference databaseReference;
    EditText registerNumber, password;
    TextView tvShowPassword;
    boolean setPType;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        registerNumber = findViewById(R.id.signInRegisterNumber);
        password = findViewById(R.id.signInPassword);

        mAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        setPType = true;
        tvShowPassword = findViewById(R.id.TextViewSignInShowPassword);
        tvShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setPType == true) {
                    setPType = false;
                    password.setTransformationMethod(null);
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                    tvShowPassword.setBackgroundResource(R.drawable.ic_hide_password);
                } else {
                    setPType = true;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                    tvShowPassword.setBackgroundResource(R.drawable.ic_show_password);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_LONG).show();
            this.finish();
            Intent intent = new Intent(SignInActivity.this, homeNav.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void signUpFun(View view) {
        Intent i = new Intent(SignInActivity.this, signUpActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void signInFun(View view) {
        final String regNo, pass;
        regNo = registerNumber.getText().toString().toUpperCase().trim();
        pass = password.getText().toString().trim();
        if (isOnline(this)) {
            if (regNo.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter Register Number", Toast.LENGTH_LONG).show();
                registerNumber.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter Password ", Toast.LENGTH_LONG).show();
                password.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword("anonymous@gmail.com","123456")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                try {
                                    DocumentReference docRef = db.collection("USER_EMAIL_REGISTRATION_ID").document(regNo);
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            user = documentSnapshot.toObject(User.class);
                                            if (user != null){
                                                login(user.getEmail(), pass);}
                                            else{
                                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            mAuth.signOut();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(SignInActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    void login(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), homeNav.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    //checkEmail();
                } else {
                    mAuth.signOut();
                    registerNumber.setText("");
                    password.setText("");
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "User name or Password Wrong!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickForgotPassword(View view) {
        Intent i = new Intent(SignInActivity.this, forgotPasswordActivity.class);
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
}
