package com.example.amma.cir2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ProfileNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
User user;
    private FirebaseAuth mAuth;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    FirebaseFirestore db;
    DatabaseReference mDatabase;


    EditText etFullName, etDOB, etAge, etFathersName, etPAddress, etPhone;
    Spinner spinGender, spinMarital, spinBloodGrp, spinNationality, spinReligion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.overridePendingTransition(0, 0);


        setTitle("Profile");

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

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder((this));
                builder.setMessage("Are you sure ,you want to Logout?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // ProfileNavActivity.this.finish();
                        finishAffinity();
                        mAuth.signOut();
                        Intent intent = new Intent(ProfileNavActivity.this, SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
    }

    public void saveButtonEvent(View view) throws Exception {
        String sFullName, sdob, sAge, sFathersName, sPAddress, sPhone, sGender, sMartial, sBloodgrp, sNationality, sReligion;
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

        Date calculateddob = new SimpleDateFormat("dd/MM/yyyy").parse(sdob);
        sAge = getAge(calculateddob.getDate(), calculateddob.getMonth(), calculateddob.getYear());


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

        addToDatabase(sFullName, sGender, sdob, sAge, sBloodgrp, sMartial, sFathersName, sPAddress, sReligion, sNationality, sPhone);
    }

    public void addToDatabase(String fullName, String gender, String DOB, String age, String bloodgrp, String martial_Status, String fathersName, String address, String religion, String nationality, String phone_Number) {
        studentProfileDetails stDetails = new studentProfileDetails(fullName, gender, DOB, age, bloodgrp, martial_Status, fathersName, address, religion, nationality, phone_Number);
        //String userEmail = mAuth.getCurrentUser().getEmail().toString();
        String regID = fetchRegistrationId();
        db.collection("Personal_Details").document(user.getEmail()).set(stDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileNavActivity.this, "User Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileNavActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public String fetchRegistrationId( ) {


        mDatabase.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e;
                user = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        return "";
    }

}

