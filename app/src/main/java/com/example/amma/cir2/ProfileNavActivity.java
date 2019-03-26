package com.example.amma.cir2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    EditText etFullName, etDOB, etAge, etFathersName, etPAddress, etPhone;
    Spinner spinGender, spinMarital, spinBloodGrp, spinNationality, spinReligion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.overridePendingTransition(0, 0);

        etFullName = findViewById(R.id.profileFullNameEditText);
        etDOB = findViewById(R.id.profileDateOfBirthEditText);
        etAge = findViewById(R.id.profileAgeEditText);
        etFathersName = findViewById(R.id.profileFathersNameEditText);
        etPAddress = findViewById(R.id.profileAddressEditText);
        etPhone = findViewById(R.id.profilePhoneNumberEditText);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                ProfileNavActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinGender = findViewById(R.id.profileGenderId);
        ArrayAdapter<String> arrayAdapterGender = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genderArray));
        arrayAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(arrayAdapterGender);

        spinMarital = findViewById(R.id.profileMaritalId);
        ArrayAdapter<String> arrayAdapterMarital = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.MaritalArray));
        arrayAdapterMarital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMarital.setAdapter(arrayAdapterMarital);

        spinBloodGrp = findViewById(R.id.profileBloodGrpId);
        ArrayAdapter<String> arrayAdapterBloodGrp = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BloodGrpArray));
        arrayAdapterBloodGrp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBloodGrp.setAdapter(arrayAdapterBloodGrp);

        spinNationality = findViewById(R.id.profileNationalityId);
        ArrayAdapter<String> arrayAdapterNationality = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.NationalityArray));
        arrayAdapterNationality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNationality.setAdapter(arrayAdapterNationality);

        spinReligion = findViewById(R.id.profileReligionId);
        ArrayAdapter<String> arrayAdapterReligion = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ReligionArray));
        arrayAdapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinReligion.setAdapter(arrayAdapterReligion);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(ProfileNavActivity.this, homeNav.class);
                startActivity(h);
                break;
            case R.id.nav_Profile:
                Intent p = new Intent(ProfileNavActivity.this, ProfileNavActivity.class);
                startActivity(p);
                break;
            case R.id.nav_Announcement:
                Intent a = new Intent(ProfileNavActivity.this, AnnouncementNavActivity.class);
                startActivity(a);
                break;
            case R.id.nav_Notifications:
                Intent n = new Intent(ProfileNavActivity.this, NotificationsNavActivity.class);
                startActivity(n);
                break;
            case R.id.nav_AptitudeQuiz:
                Intent ap = new Intent(ProfileNavActivity.this, AptitudeQuizNavActivity.class);
                startActivity(ap);
                break;
            case R.id.nav_Feedback:
                Intent f = new Intent(ProfileNavActivity.this, FeedbackNavActivity.class);
                startActivity(f);
                break;
            case R.id.nav_ContactUs:
                Intent c = new Intent(ProfileNavActivity.this, AboutUsNavActivity.class);
                startActivity(c);
                break;
            case R.id.nav_Logout:
                Intent l = new Intent(ProfileNavActivity.this, homeNav.class);
                startActivity(l);
                break;

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateButtonEvent(View view) {
        String sFullName, sdob, sAge, sFathersName, sPAddress, sPhone, sGender, sMartial, sBloodgrp, sNationality, sReligion;

        etFullName.setEnabled(true);
        etDOB.setEnabled(true);
        //etAge.setEnabled(true);
        etFathersName.setEnabled(true);
        etPAddress.setEnabled(true);
        etPhone.setEnabled(true);
        spinGender.setEnabled(true);
        spinMarital.setEnabled(true);
        spinBloodGrp.setEnabled(true);
        spinNationality.setEnabled(true);
        spinReligion.setEnabled(true);

        sFullName = etFullName.getText().toString();
        sdob = etDOB.getText().toString();
        sFathersName = etFathersName.getText().toString();
        sPAddress = etPAddress.getText().toString();
        sPhone = etPhone.getText().toString();
        sGender = spinGender.getSelectedItem().toString();
        sMartial = spinMarital.getSelectedItem().toString();
        sBloodgrp = spinBloodGrp.getSelectedItem().toString();
        sNationality = spinNationality.getSelectedItem().toString();
        sReligion = spinReligion.getSelectedItem().toString();

    }

    public void saveButtonEvent(View view) {

        etFullName.setEnabled(false);
        etDOB.setEnabled(false);
        etAge.setEnabled(false);
        etFathersName.setEnabled(false);
        etPAddress.setEnabled(false);
        etPhone.setEnabled(false);
        spinGender.setEnabled(false);
        spinMarital.setEnabled(false);
        spinBloodGrp.setEnabled(false);
        spinNationality.setEnabled(false);
        spinReligion.setEnabled(false);


    }
}

