package com.example.amma.cir2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class forgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onClickBackForget(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(forgotPasswordActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
