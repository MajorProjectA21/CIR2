package com.example.amma.cir2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText registerNumber, password;
    TextView tvShowPassword;
    boolean setPType;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        registerNumber = findViewById(R.id.signInRegisterNumber);
        password = findViewById(R.id.signInPassword);

        mAuth = FirebaseAuth.getInstance();

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
            startActivity(intent);
        }
    }

    public void signUpFun(View view) {
        Intent i = new Intent(SignInActivity.this, signUpActivity.class);
        startActivity(i);
        finish();
    }

    public void signInFun(View view) {
        String regNo, pass, email;
        regNo = registerNumber.getText().toString().trim();
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
        email = fetchEmail(regNo);
            mAuth.signInWithEmailAndPassword(regNo, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), homeNav.class);
                        startActivity(i);
                        //checkEmail();
                    } else {
                        mAuth.signOut();
                        registerNumber.setText("");
                        password.setText("");
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignInActivity.this, "Invalid com.example.amma.cir2.User", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }
String fetchEmail(String regno){
        String email="";

        return email;

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
