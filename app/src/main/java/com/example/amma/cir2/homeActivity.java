package com.example.amma.cir2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onBackPressed() {
        Intent i = new Intent(homeActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
        //super.onBackPressed();
    }
}
