package com.example.amma.cir2;

import android.app.DatePickerDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;


public class ProfileNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public User user;
    public FirebaseAuth mAuth;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public Toolbar toolbar = null;
    Boolean flagFullName, flagFathersName, flagPAddress, flagPhone;

    public FirebaseFirestore db;


Button btUpdate,btSave,btAcademic;
    EditText etFullName, etDOB, etAge, etFathersName, etPAddress, etPhone;
    Spinner spinGender, spinMarital, spinBloodGrp, spinNationality, spinReligion;
    ArrayAdapter<String> arrayAdapterGender, arrayAdapterMarital, arrayAdapterBloodGrp, arrayAdapterNationality, arrayAdapterReligion;
    Calendar myCalendar;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.overridePendingTransition(0, 0);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btSave =findViewById(R.id.ProfileActivitySaveButton);
        btUpdate =findViewById(R.id.ProfileActivityUpdateButton);
        btAcademic =findViewById(R.id.ProfileActivityAcademicButton);
        progressBar = findViewById(R.id.ProfileProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        myCalendar = Calendar.getInstance();
        setTitle("Profile");
        fetchRegistrationId();
        etFullName = findViewById(R.id.profileFullNameEditText);
        flagFullName = false;
        etDOB = findViewById(R.id.profileDateOfBirthEditText);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(year, monthOfYear, dayOfMonth);
                updateLabel();
            }

        };

        etDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileNavActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etAge = findViewById(R.id.profileAgeEditText);
        etAge.setEnabled(false);


        etFathersName = findViewById(R.id.profileFathersNameEditText);
        flagFathersName = false;
        etPAddress = findViewById(R.id.profileAddressEditText);
        flagPAddress = false;
        etPhone = findViewById(R.id.profilePhoneNumberEditText);
        flagPhone = false;

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                ProfileNavActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinGender = findViewById(R.id.profileGenderId);
        arrayAdapterGender = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genderArray));
        arrayAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(arrayAdapterGender);

        spinMarital = findViewById(R.id.profileMaritalId);
        arrayAdapterMarital = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.MaritalArray));
        arrayAdapterMarital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMarital.setAdapter(arrayAdapterMarital);

        spinBloodGrp = findViewById(R.id.profileBloodGrpId);
        arrayAdapterBloodGrp = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BloodGrpArray));
        arrayAdapterBloodGrp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBloodGrp.setAdapter(arrayAdapterBloodGrp);

        spinNationality = findViewById(R.id.profileNationalityId);
        arrayAdapterNationality = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.NationalityArray));
        arrayAdapterNationality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNationality.setAdapter(arrayAdapterNationality);

        spinReligion = findViewById(R.id.profileReligionId);
        arrayAdapterReligion = new ArrayAdapter<String>(ProfileNavActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ReligionArray));
        arrayAdapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinReligion.setAdapter(arrayAdapterReligion);


        //validation();


    }

    public void getDataFromDatabase() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            String regno = user.getRegisterNumber();
            DocumentReference docRef = db.collection("Student_Profile_Details").document(regno);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            studentProfileDetails st = document.toObject(studentProfileDetails.class);
                            if (st != null) {
                                etFullName.setText(st.getFullName());
                                etDOB.setText(st.getDOB());
                                etAge.setText(st.getAge());
                                etFathersName.setText(st.getFathersName());
                                etPAddress.setText(st.getAddress());
                                etPhone.setText(st.getPhone_Number());

                                etFullName.setEnabled(false);
                                etDOB.setEnabled(false);
                                etAge.setEnabled(false);
                                etFathersName.setEnabled(false);
                                etPAddress.setEnabled(false);
                                etPhone.setEnabled(false);
                                int spinnerPosition;

                                spinnerPosition = arrayAdapterGender.getPosition(st.getGender());
                                spinGender.setSelection(spinnerPosition);

                                spinnerPosition = arrayAdapterMarital.getPosition(st.getMartial_Status());
                                spinMarital.setSelection(spinnerPosition);

                                spinnerPosition = arrayAdapterBloodGrp.getPosition(st.getBloodgrp());
                                spinBloodGrp.setSelection(spinnerPosition);

                                spinnerPosition = arrayAdapterNationality.getPosition(st.getNationality());
                                spinNationality.setSelection(spinnerPosition);

                                spinnerPosition = arrayAdapterReligion.getPosition(st.getReligion());
                                spinReligion.setSelection(spinnerPosition);
                                progressBar.setVisibility(View.INVISIBLE);

                                spinGender.setEnabled(false);
                                spinBloodGrp.setEnabled(false);
                                spinMarital.setEnabled(false);
                                spinNationality.setEnabled(false);
                                spinReligion.setEnabled(false);

                                btUpdate.setEnabled(true);
                                btUpdate.setBackgroundResource(R.drawable.shapesignup);
                                btSave.setEnabled(false);
                                btSave.setBackgroundResource(R.drawable.shapebuttondisabled);

                            }
                        } else {
                            //Log.d(TAG, "No such document");
                            validation();
                            progressBar.setVisibility(View.INVISIBLE);
                            btUpdate.setEnabled(false);
                            btUpdate.setBackgroundResource(R.drawable.shapebuttondisabled);
                            btSave.setEnabled(true);
                            btSave.setBackgroundResource(R.drawable.shapesignup);
                        }
                    } else {
                        validation();
                        progressBar.setVisibility(View.INVISIBLE);
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ProfileNavActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void validation() {
        etFullName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String arg = s.toString();
                if (!Pattern.matches("^[a-zA-Z\\s]*$", arg)) {
                    etFullName.setError("No Special characters allowed");
                    return;
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        etFathersName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String arg = s.toString();
                if (!Pattern.matches("^[a-zA-Z\\s]*$", arg)) {
                    etFathersName.setError("No Special characters allowed");
                    return;
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        etPAddress.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String arg = s.toString();
                if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", arg)) {
                    etPAddress.setError("No Special characters allowed");
                    return;
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String arg = s.toString();
                if (!Patterns.PHONE.matcher(arg).matches()) {
                    etPhone.setError("Enter valid Mobile Number");
                    return;
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String phone = etPhone.getText().toString();
                if (phone.isEmpty()) {
                    etPhone.setError("Enter valid Mobile Number(10 digits)");
                }
            }
        });

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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDOB.setText(sdf.format(myCalendar.getTime()));
        getAge();
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
        etFullName.setEnabled(true);
        etDOB.setEnabled(true);
        etFathersName.setEnabled(true);
        etPAddress.setEnabled(true);
        etPhone.setEnabled(true);
        spinGender.setEnabled(true);
        spinMarital.setEnabled(true);
        spinBloodGrp.setEnabled(true);
        spinNationality.setEnabled(true);
        spinReligion.setEnabled(true);
        btUpdate.setEnabled(false);
        btSave.setEnabled(true);
    }

    public void saveButtonEvent(View view) {
        btUpdate.setEnabled(true);
        btSave.setEnabled(false);
        String sFullName, sdob, sAge, sFathersName, sPAddress, sPhone, sGender, sMartial, sBloodgrp, sNationality, sReligion;
        sFullName = etFullName.getText().toString();
        sAge = etAge.getText().toString();
        sdob = etDOB.getText().toString();
        sFathersName = etFathersName.getText().toString();
        sPAddress = etPAddress.getText().toString();
        sPhone = etPhone.getText().toString();
        sGender = spinGender.getSelectedItem().toString();
        sMartial = spinMarital.getSelectedItem().toString();
        sBloodgrp = spinBloodGrp.getSelectedItem().toString();
        sNationality = spinNationality.getSelectedItem().toString();
        sReligion = spinReligion.getSelectedItem().toString();
        try {
            if (sFullName.isEmpty()) {
                etFullName.setError("Please enter Name");
                etFullName.requestFocus();
                return;
            }
            if (sFathersName.isEmpty()) {
                etFathersName.setError("Please enter Father's Name");
                etFathersName.requestFocus();
                return;
            }
            if (sPAddress.isEmpty()) {
                etPAddress.setError("Please enter Address");
                etPAddress.requestFocus();
                return;
            }
            if (sPhone.isEmpty()) {
                etPhone.setError("Please enter Mobile Number");
                etPhone.requestFocus();
                return;
            }
            if (sPhone.length() < 10) {
                etPhone.setError("Please 10 digit Mobile Number");
                etPhone.requestFocus();
                return;
            }
            if (Integer.valueOf(sAge) <= 17) {
                etDOB.setError("Age less than 17");
                etDOB.requestFocus();
                return;
            }
            if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", sPAddress)) {
                etPAddress.setError("No Special characters allowed");
                return;
            }
            if (!Pattern.matches("^[a-zA-Z\\s]*$", sFathersName)) {
                etFathersName.setError("No Special characters allowed");
                return;
            }
            if (!Pattern.matches("^[a-zA-Z\\s]*$", sFullName)) {
                etFullName.setError("No Special characters allowed");
                return;
            }
            if (sdob.isEmpty()) {
                Toast.makeText(ProfileNavActivity.this, "Enter Date of Birth", Toast.LENGTH_LONG).show();
                etDOB.setError("Enter Date of Birth");
                return;
            }
        } catch (Exception e) {

        }
        try {
            etFullName.setEnabled(false);
            etDOB.setEnabled(false);
            etDOB.setFocusable(false);
            etDOB.setClickable(false);
            etAge.setEnabled(false);
            etAge.setFocusable(false);
            etAge.setClickable(false);
            etFathersName.setEnabled(false);
            etPAddress.setEnabled(false);
            etPhone.setEnabled(false);
            spinGender.setEnabled(false);
            spinMarital.setEnabled(false);
            spinBloodGrp.setEnabled(false);
            spinNationality.setEnabled(false);
            spinReligion.setEnabled(false);
            addToDatabase(sFullName, sGender, sdob, sAge, sBloodgrp, sMartial, sFathersName, sPAddress, sReligion, sNationality, sPhone);
        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addToDatabase(String fullName, String gender, String DOB, String age, String bloodgrp, String martial_Status, String fathersName, String address, String religion, String nationality, String phone_Number) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            studentProfileDetails stDetails = new studentProfileDetails(fullName, gender, DOB, age, bloodgrp, martial_Status, fathersName, address, religion, nationality, phone_Number);
            String regno = user.getRegisterNumber();
            db.collection("Student_Profile_Details").document(regno).set(stDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileNavActivity.this, "User Registered",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileNavActivity.this, "ERROR" + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ProfileNavActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getAge() {

        Calendar dob = myCalendar;
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        etAge.setText(ageS);
    }

    public void fetchRegistrationId() {
        try {
            DocumentReference docRef = db.collection("User_ID_Registration_ID").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    getDataFromDatabase();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ProfileNavActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void AcademicButtonClick(View view) {
        Intent intent = new Intent(ProfileNavActivity.this, AcademicDetailsActivity.class);
        startActivity(intent);
        finish();

    }
}


