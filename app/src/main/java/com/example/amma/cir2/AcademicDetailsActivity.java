package com.example.amma.cir2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AcademicDetailsActivity extends AppCompatActivity {

    public User user;
    EditText edPercentage, edHSPercentage;
    Spinner spDOP, spBoard, spHSDOP, spHSBoard;
    Button updateButton, saveButton;
    CheckBox sem1, sem2, sem3, sem4, sem5, sem6;
    EditText sem1cgpa, sem1dop, sem2cgpa, sem2dop, sem3cgpa, sem3dop, sem4cgpa, sem4dop, sem5cgpa, sem5dop, sem6cgpa, sem6dop;
    TextView tvsem1, tvsem2, tvsem3, tvsem4, tvsem5, tvsem6;
    TextView tvdopsem1, tvdopsem2, tvdopsem3, tvdopsem4, tvdopsem5, tvdopsem6;
    public FirebaseAuth mAuth;
    public FirebaseFirestore db;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_details);
        this.overridePendingTransition(0, 0);
        tvsem1 = findViewById(R.id.AcademicSem1CgpaTextView);
        tvsem2 = findViewById(R.id.AcademicSem2CgpaTextView);
        tvsem3 = findViewById(R.id.AcademicSem3CgpaTextView);
        tvsem4 = findViewById(R.id.AcademicSem4CgpaTextView);
        tvsem5 = findViewById(R.id.AcademicSem5CgpaTextView);
        tvsem6 = findViewById(R.id.AcademicSem6CgpaTextView);
        tvdopsem1 = findViewById(R.id.AcademicDOPTExtView);
        tvdopsem2 = findViewById(R.id.AcademicSem2DOPTExtView);
        tvdopsem3 = findViewById(R.id.AcademicSem3DOPTExtView);
        tvdopsem4 = findViewById(R.id.AcademicSem4DOPTExtView);
        tvdopsem5 = findViewById(R.id.AcademicSem5DOPTExtView);
        tvdopsem6 = findViewById(R.id.AcademicSem6DOPTExtView);

        tvsem1.setVisibility(View.GONE);
        tvsem2.setVisibility(View.GONE);
        tvsem3.setVisibility(View.GONE);
        tvsem4.setVisibility(View.GONE);
        tvsem5.setVisibility(View.GONE);
        tvsem6.setVisibility(View.GONE);
        tvdopsem1.setVisibility(View.GONE);
        tvdopsem2.setVisibility(View.GONE);
        tvdopsem3.setVisibility(View.GONE);
        tvdopsem4.setVisibility(View.GONE);
        tvdopsem5.setVisibility(View.GONE);
        tvdopsem6.setVisibility(View.GONE);


        sem1cgpa = findViewById(R.id.AcademicSem1Cgpa);
        sem1dop = findViewById(R.id.AcademicSem1DOPCgpa);
        sem2cgpa = findViewById(R.id.AcademicSem2Cgpa);
        sem2dop = findViewById(R.id.AcademicSem2DOPCgpa);
        sem3cgpa = findViewById(R.id.AcademicSem3Cgpa);
        sem3dop = findViewById(R.id.AcademicSem3DOPCgpa);
        sem4cgpa = findViewById(R.id.AcademicSem4Cgpa);
        sem4dop = findViewById(R.id.AcademicSem4DOPCgpa);
        sem5cgpa = findViewById(R.id.AcademicSem5Cgpa);
        sem5dop = findViewById(R.id.AcademicSem5DOPCgpa);
        sem6cgpa = findViewById(R.id.AcademicSem6Cgpa);
        sem6dop = findViewById(R.id.AcademicSem6DOPCgpa);

        sem1cgpa.setEnabled(false);
        sem1cgpa.setVisibility(View.GONE);
        sem1dop.setEnabled(false);
        sem1dop.setVisibility(View.GONE);


        sem2cgpa.setEnabled(false);
        sem2cgpa.setVisibility(View.GONE);
        sem2dop.setEnabled(false);
        sem2dop.setVisibility(View.GONE);

        sem3cgpa.setEnabled(false);
        sem3cgpa.setVisibility(View.GONE);
        sem3dop.setEnabled(false);
        sem3dop.setVisibility(View.GONE);

        sem4cgpa.setEnabled(false);
        sem4cgpa.setVisibility(View.GONE);
        sem4dop.setEnabled(false);
        sem4dop.setVisibility(View.GONE);

        sem5cgpa.setEnabled(false);
        sem5cgpa.setVisibility(View.GONE);
        sem5dop.setEnabled(false);
        sem5dop.setVisibility(View.GONE);

        sem6cgpa.setEnabled(false);
        sem6cgpa.setVisibility(View.GONE);
        sem6dop.setEnabled(false);
        sem6dop.setVisibility(View.GONE);


        sem1 = findViewById(R.id.AcademicDetailsCb1);
        sem1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sem1cgpa.setEnabled(true);
                    sem1cgpa.setVisibility(v.VISIBLE);
                    sem1dop.setEnabled(true);
                    sem1dop.setVisibility(v.VISIBLE);
                    tvsem1.setVisibility(v.VISIBLE);
                    tvdopsem1.setVisibility(v.VISIBLE);

                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem1cgpa.setEnabled(false);
                    sem1cgpa.setVisibility(v.GONE);
                    sem1dop.setEnabled(false);
                    sem1dop.setVisibility(v.GONE);
                    tvsem1.setVisibility(v.GONE);
                    tvdopsem1.setVisibility(v.GONE);
                }

            }
        });
        sem2 = findViewById(R.id.AcademicDetailsCb2);
        sem2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //CODE TO MAKE THE EDITTEXT ENABLED
                    sem2cgpa.setEnabled(true);
                    sem2cgpa.setVisibility(v.VISIBLE);
                    sem2dop.setEnabled(true);
                    sem2.setVisibility(v.VISIBLE);
                    tvsem2.setVisibility(v.VISIBLE);
                    tvdopsem2.setVisibility(v.VISIBLE);
                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem2cgpa.setEnabled(false);
                    sem2cgpa.setVisibility(v.GONE);
                    sem2dop.setEnabled(false);
                    sem2dop.setVisibility(v.GONE);
                    tvsem2.setVisibility(v.GONE);
                    tvdopsem2.setVisibility(v.GONE);
                }

            }
        });
        sem3 = findViewById(R.id.AcademicDetailsCb3);
        sem3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //CODE TO MAKE THE EDITTEXT ENABLED
                    sem3cgpa.setEnabled(true);
                    sem3cgpa.setVisibility(v.VISIBLE);
                    sem3dop.setEnabled(true);
                    sem3.setVisibility(v.VISIBLE);
                    tvsem3.setVisibility(v.VISIBLE);
                    tvdopsem3.setVisibility(v.VISIBLE);
                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem3cgpa.setEnabled(false);
                    sem3cgpa.setVisibility(v.GONE);
                    sem3dop.setEnabled(false);
                    sem3dop.setVisibility(v.GONE);
                    tvsem3.setVisibility(v.GONE);
                    tvdopsem3.setVisibility(v.GONE);
                }

            }
        });
        sem4 = findViewById(R.id.AcademicDetailsCb4);
        sem4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //CODE TO MAKE THE EDITTEXT ENABLED
                    sem4cgpa.setEnabled(true);
                    sem4cgpa.setVisibility(v.VISIBLE);
                    sem4dop.setEnabled(true);
                    sem4.setVisibility(v.VISIBLE);
                    tvsem4.setVisibility(v.VISIBLE);
                    tvdopsem4.setVisibility(v.VISIBLE);

                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem4cgpa.setEnabled(false);
                    sem4cgpa.setVisibility(v.GONE);
                    sem4dop.setEnabled(false);
                    sem4dop.setVisibility(v.GONE);
                    tvsem4.setVisibility(v.GONE);
                    tvdopsem4.setVisibility(v.GONE);
                }

            }
        });
        sem5 = findViewById(R.id.AcademicDetailsCb5);
        sem5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //CODE TO MAKE THE EDITTEXT ENABLED
                    sem5cgpa.setEnabled(true);
                    sem5cgpa.setVisibility(v.VISIBLE);
                    sem5dop.setEnabled(true);
                    sem5.setVisibility(v.VISIBLE);
                    tvsem5.setVisibility(v.VISIBLE);
                    tvdopsem5.setVisibility(v.VISIBLE);
                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem5cgpa.setEnabled(false);
                    sem5cgpa.setVisibility(v.GONE);
                    sem5dop.setEnabled(false);
                    sem5dop.setVisibility(v.GONE);
                    tvsem5.setVisibility(v.GONE);
                    tvdopsem5.setVisibility(v.GONE);
                }

            }
        });
        sem6 = findViewById(R.id.AcademicDetailsCb6);
        sem6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //CODE TO MAKE THE EDITTEXT ENABLED
                    sem6cgpa.setEnabled(true);
                    sem6cgpa.setVisibility(v.VISIBLE);
                    sem6dop.setEnabled(true);
                    sem6.setVisibility(v.VISIBLE);
                    tvsem6.setVisibility(v.VISIBLE);
                    tvdopsem6.setVisibility(v.VISIBLE);

                } else {
                    //CODE TO MAKE THE EDITTEXT DISABLED
                    sem6cgpa.setEnabled(false);
                    sem6cgpa.setVisibility(v.GONE);
                    sem6dop.setEnabled(false);
                    sem6dop.setVisibility(v.GONE);
                    tvsem6.setVisibility(v.GONE);
                    tvdopsem6.setVisibility(v.GONE);
                }

            }
        });

        updateButton = findViewById(R.id.AcademicDetailsUpdateButton);
        saveButton = findViewById(R.id.AcademicDetailsSaveButton);
        updateButton.setBackgroundColor(Color.GRAY);
        updateButton.setEnabled(false);

        edPercentage = findViewById(R.id.AcademicPercentageEditText);
        edHSPercentage = findViewById(R.id.AcademicHSPercentageEditText);

        spDOP = findViewById(R.id.AcademicYearOFPassing);
        ArrayAdapter<String> arrayAdapterDOP = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.YearOfPassingArray));
        spDOP.setAdapter(arrayAdapterDOP);

        spHSDOP = findViewById(R.id.AcademicHSYearOFPassing);
        ArrayAdapter<String> arrayAdapterHSDOP = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.YearOfPassingArray));
        spHSDOP.setAdapter(arrayAdapterHSDOP);

        spBoard = findViewById(R.id.AcademicBoardOfExaminationEditTExt);
        ArrayAdapter<String> arrayAdapterBoard = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoardOfExaminationArray));
        spBoard.setAdapter(arrayAdapterBoard);

        spHSBoard = findViewById(R.id.AcademicHSBoardOfExaminationEditTExt);
        ArrayAdapter<String> arrayAdapterHSBoard = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoardOfExaminationArray));
        spHSBoard.setAdapter(arrayAdapterHSBoard);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void updateButtonEvent(View view) {
        edPercentage.setEnabled(true);
        edHSPercentage.setEnabled(true);
        spDOP.setEnabled(true);
        spBoard.setEnabled(true);
        spHSBoard.setEnabled(true);
        spHSDOP.setEnabled(true);
        updateButton.setEnabled(false);
        saveButton.setEnabled(true);
    }

    public void saveButtonEvent(View view) {
        // edPercentage,edHSPercentage;
        //Spinner spDOP,spBoard,spHSDOP,spHSBoard;
        String percentage, hsPercentage, DOP, hsDOP, Board, hsBoard;


        try {
            saveButton.setEnabled(false);
            updateButton.setEnabled(true);
            percentage = edPercentage.getText().toString();
            hsPercentage = edHSPercentage.getText().toString();
            DOP = spDOP.getSelectedItem().toString();
            hsDOP = spHSDOP.getSelectedItem().toString();
            Board = spBoard.getSelectedItem().toString();
            hsBoard = spHSBoard.getSelectedItem().toString();

            edPercentage.setEnabled(false);
            edHSPercentage.setEnabled(false);
            spDOP.setEnabled(false);
            spBoard.setEnabled(false);
            spHSBoard.setEnabled(false);
            spHSDOP.setEnabled(false);
            addToDatabase(percentage, hsPercentage, DOP, hsDOP, Board, hsBoard);
        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addToDatabase(String percentage, String hsPercentage, String DOP, String hsDOP, String Board, String hsBoard) {
        try {
            studentAcademicDetails stDetails = new studentAcademicDetails(percentage, DOP, Board, hsPercentage, hsDOP, hsBoard);
            fetchRegistrationId();
            String regno = user.getRegisterNumber();
            db.collection("Academic_Details").document(regno).set(stDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AcademicDetailsActivity.this, "User Registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AcademicDetailsActivity.this, "ERROR" + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AcademicDetailsActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void fetchRegistrationId() {
        try {
            mDatabase.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(AcademicDetailsActivity.this, "Failed to read data" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AcademicDetailsActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void personalButtonClick(View view) {
        Intent intent = new Intent(AcademicDetailsActivity.this, ProfileNavActivity.class);
        startActivity(intent);
        finish();

    }
}
