package com.example.amma.cir2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AcademicDetailsActivity extends AppCompatActivity {

    public User user;
    EditText edPercentage, edHSPercentage, edrollno, edhsrollno;
    Spinner spDOP, spBoard, spHSDOP, spHSBoard;
    Button SecupdateButton, SecsaveButton, HSecupdateButton, HSecsaveButton, GradupdateButton, GradsaveButton;
    CheckBox sem1, sem2, sem3, sem4, sem5, sem6;
    EditText sem1cgpa, sem1dop, sem2cgpa, sem2dop, sem3cgpa, sem3dop, sem4cgpa, sem4dop, sem5cgpa, sem5dop, sem6cgpa, sem6dop;
    TextView tvsem1, tvsem2, tvsem3, tvsem4, tvsem5, tvsem6;
    TextView tvdopsem1, tvdopsem2, tvdopsem3, tvdopsem4, tvdopsem5, tvdopsem6;
    ProgressBar progressBar;
    public FirebaseAuth mAuth;
    public FirebaseFirestore db;
    ArrayAdapter<String> arrayAdapterDOP, arrayAdapterHSDOP, arrayAdapterBoard, arrayAdapterHSBoard;

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

        edrollno = findViewById(R.id.AcademicRollNumberEditText);
        edhsrollno = findViewById(R.id.AcademicHSRollNumberEditText);
        progressBar = findViewById(R.id.AcademicDetailsProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

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

        SecupdateButton = findViewById(R.id.AcademicDetailsUpdateButton);
        SecsaveButton = findViewById(R.id.AcademicDetailsSaveButton);
        SecupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
        SecupdateButton.setEnabled(false);

        HSecupdateButton = findViewById(R.id.AcademicDetailsHSUpdateButton);
        HSecsaveButton = findViewById(R.id.AcademicDetailsHSSaveButton);
        HSecupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
        HSecupdateButton.setEnabled(false);

        GradupdateButton = findViewById(R.id.AcademicDetailsGradUpdateButton);
        GradsaveButton = findViewById(R.id.AcademicDetailsGradSaveButton);
        GradupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
        GradupdateButton.setEnabled(false);

        edPercentage = findViewById(R.id.AcademicPercentageEditText);
        edHSPercentage = findViewById(R.id.AcademicHSPercentageEditText);

        spDOP = findViewById(R.id.AcademicYearOFPassing);
        arrayAdapterDOP = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.YearOfPassingArray));
        spDOP.setAdapter(arrayAdapterDOP);

        spHSDOP = findViewById(R.id.AcademicHSYearOFPassing);
        arrayAdapterHSDOP = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.HSYearOfPassingArray));
        spHSDOP.setAdapter(arrayAdapterHSDOP);

        spBoard = findViewById(R.id.AcademicBoardOfExaminationEditTExt);
        arrayAdapterBoard = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoardOfExaminationArray));
        spBoard.setAdapter(arrayAdapterBoard);

        spHSBoard = findViewById(R.id.AcademicHSBoardOfExaminationEditTExt);
        arrayAdapterHSBoard = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoardOfExaminationArray));
        spHSBoard.setAdapter(arrayAdapterHSBoard);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fetchRegistrationId();
        hideLabels();
        buttonClickEventHandler();
        fetchRegistrationId();
    }

    public int validationSecondary() {
        String percentage,rollNo;
        percentage = edPercentage.getText().toString();
        rollNo = edrollno.getText().toString();
        Double per = Double.valueOf(percentage);
        String regexStr = "^[0-9]*$";

        if(!rollNo.trim().matches(regexStr))
        {
            edrollno.setError("Enter  valid RollNumber");
            edrollno.requestFocus();
            return 1;
        }
        if(percentage.isEmpty()){
            edPercentage.setError("Enter Percentage");
            edPercentage.requestFocus();
            return 1;
        }
        if(rollNo.isEmpty()){
            edrollno.setError("Enter RollNumber");
            edrollno.requestFocus();
            return 1;
        }
        if(per<0 || per >100 || per.isNaN()){
            edPercentage.setError("Enter Valid Percentage");
            edPercentage.requestFocus();
            return 1;
        }
        else
        {
            String decimalSeparator=".";
            if(percentage.indexOf(decimalSeparator)<percentage.length()-3)
            {
                edPercentage.setError("Enter Valid Percentage...Tooo much decimal");
                edPercentage.requestFocus();
                return 1;
            }
        }
        return 0;
    }

    public int validationHigherSecondary() {
        String percentage,rollNo;
        percentage = edHSPercentage.getText().toString();
        rollNo = edhsrollno.getText().toString();
        Double per = Double.valueOf(percentage);
        String regexStr = "^[0-9]*$";

        if(!rollNo.trim().matches(regexStr))
        {
            edhsrollno.setError("Enter  valid RollNumber");
            edhsrollno.requestFocus();
            return 1;
        }
        if(percentage.isEmpty()){
            edHSPercentage.setError("Enter Percentage");
            edHSPercentage.requestFocus();
            return 1;
        }
        if(rollNo.isEmpty()){
            edhsrollno.setError("Enter RollNumber");
            edhsrollno.requestFocus();
            return 1;
        }
        if(per<0 || per >100 || per.isNaN()){
            edHSPercentage.setError("Enter Valid Percentage");
            edHSPercentage.requestFocus();
            return 1;
        }
        else
        {
            String decimalSeparator=".";
            if(percentage.indexOf(decimalSeparator)<percentage.length()-3)
            {
                edHSPercentage.setError("Enter Valid Percentage...Tooo much decimal");
                edHSPercentage.requestFocus();
                return 1;
            }
        }
        return 0;
    }

    public int validationGraduation() {
        return 0;
    }

    public void buttonClickEventHandler() {
        SecupdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                SecupdateButton.setEnabled(false);
                SecsaveButton.setBackgroundColor(R.drawable.shapesignup);
                SecsaveButton.setEnabled(true);
                edPercentage.setEnabled(true);
                edrollno.setEnabled(true);
                spDOP.setEnabled(true);
                spBoard.setEnabled(true);
            }
        });
        SecsaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String rollNo, percentage, DOP, Board;
                    rollNo = edrollno.getText().toString();
                    percentage = edPercentage.getText().toString();
                    DOP = spDOP.getSelectedItem().toString();
                    Board = spBoard.getSelectedItem().toString();


                    int flag = validationSecondary();
                    if (flag == 0) {
                        SecupdateButton.setBackgroundColor(R.drawable.shapesignup);
                        SecupdateButton.setEnabled(true);
                        SecsaveButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                        SecsaveButton.setEnabled(false);
                        edPercentage.setEnabled(false);
                        edrollno.setEnabled(false);
                        spDOP.setEnabled(false);
                        spBoard.setEnabled(false);
                        addToDatabaseAcademicSecondary(rollNo, percentage, DOP, Board);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AcademicDetailsActivity.this, "saveDatabaseError:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        HSecupdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HSecupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                HSecupdateButton.setEnabled(false);
                HSecsaveButton.setBackgroundColor(R.drawable.shapesignup);
                HSecsaveButton.setEnabled(true);
                edHSPercentage.setEnabled(true);
                edhsrollno.setEnabled(true);
                spHSDOP.setEnabled(true);
                spHSBoard.setEnabled(true);
            }
        });
        HSecsaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String rollNo, percentage, DOP, Board;
                    rollNo = edhsrollno.getText().toString();
                    percentage = edHSPercentage.getText().toString();
                    DOP = spHSDOP.getSelectedItem().toString();
                    Board = spHSBoard.getSelectedItem().toString();
                    int flag = validationHigherSecondary();
                    if (flag == 0) {
                        HSecupdateButton.setBackgroundColor(R.drawable.shapesignup);
                        HSecupdateButton.setEnabled(true);
                        HSecsaveButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                        HSecsaveButton.setEnabled(false);
                        edHSPercentage.setEnabled(false);
                        edhsrollno.setEnabled(false);
                        spHSDOP.setEnabled(false);
                        spHSBoard.setEnabled(false);

                        addToDatabaseAcademicHSec(rollNo, percentage, DOP, Board);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AcademicDetailsActivity.this, "saveDatabaseError:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        GradupdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradupdateButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                GradupdateButton.setEnabled(false);
                GradsaveButton.setBackgroundColor(R.drawable.shapesignup);
                GradsaveButton.setEnabled(true);
                if (sem1.isChecked()) {
                    sem1cgpa.setEnabled(true);
                    sem1dop.setEnabled(true);
                }
                if (sem2.isChecked()) {
                    sem2cgpa.setEnabled(true);
                    sem2dop.setEnabled(true);
                }
                if (sem3.isChecked()) {
                    sem3cgpa.setEnabled(true);
                    sem3dop.setEnabled(true);
                }
                if (sem4.isChecked()) {
                    sem4cgpa.setEnabled(true);
                    sem4dop.setEnabled(true);
                }
                if (sem5.isChecked()) {
                    sem5cgpa.setEnabled(true);
                    sem5dop.setEnabled(true);
                }
                if (sem6.isChecked()) {
                    sem6cgpa.setEnabled(true);
                    sem6dop.setEnabled(true);
                }
            }
        });
        GradsaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = validationGraduation();
                if (flag == 0) {
                    GradupdateButton.setBackgroundColor(R.drawable.shapesignup);
                    GradupdateButton.setEnabled(true);
                    GradsaveButton.setBackgroundColor(R.drawable.shapebuttondisabled);
                    GradsaveButton.setEnabled(false);
                    addToDatabaseGraduation();
                }
                if (sem1.isChecked()) {
                    sem1cgpa.setEnabled(false);
                    sem1dop.setEnabled(false);
                }
                if (sem2.isChecked()) {
                    sem2cgpa.setEnabled(false);
                    sem2dop.setEnabled(false);
                }
                if (sem3.isChecked()) {
                    sem3cgpa.setEnabled(false);
                    sem3dop.setEnabled(false);
                }
                if (sem4.isChecked()) {
                    sem4cgpa.setEnabled(false);
                    sem4dop.setEnabled(false);
                }
                if (sem5.isChecked()) {
                    sem5cgpa.setEnabled(false);
                    sem5dop.setEnabled(false);
                }
                if (sem6.isChecked()) {
                    sem6cgpa.setEnabled(false);
                    sem6dop.setEnabled(false);
                }
            }
        });
    }
    public void addToDatabaseAcademicSecondary(String rollNo,String percentage, String DOP, String Board){
        try {
            studentAcademicDetails stDetails = new studentAcademicDetails(rollNo,percentage,DOP,Board);
            fetchRegistrationId();
            String regno = user.getRegisterNumber();
            db.collection("Student_Academic_Secondary_Details").document(regno).set(stDetails)
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
            Toast.makeText(AcademicDetailsActivity.this, "addDatabaseError" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void addToDatabaseAcademicHSec(String rollNo,String percentage, String DOP, String Board){
        try {
            studentAcademicDetails stDetails = new studentAcademicDetails(rollNo,percentage,DOP,Board);
            fetchRegistrationId();
            String regno = user.getRegisterNumber();
            db.collection("Student_Academic_Higher_Secondary_Details").document(regno).set(stDetails)
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
            Toast.makeText(AcademicDetailsActivity.this, "addDatabaseError" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void addToDatabaseGraduation(){

    }

    public void hideLabels() {
        LinearLayout l1 = findViewById(R.id.academicdetailsLinearLayoutSecondary);
        LinearLayout l2 = findViewById(R.id.academicDetailsLinearLayoutHS);
        LinearLayout l3 = findViewById(R.id.academicDetailsLinearLayoutGraduation);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
    }

    public void secondaryButtonClick(View view) {
        LinearLayout l = findViewById(R.id.academicdetailsLinearLayoutSecondary);
        if (l.getVisibility() == View.GONE) {
            l.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.GONE);
        }
    }

    public void hsButtonClick(View view) {
        LinearLayout l = findViewById(R.id.academicDetailsLinearLayoutHS);
        if (l.getVisibility() == View.GONE) {
            l.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.GONE);
        }
    }

    public void GraduationButtonClick(View view) {
        LinearLayout l = findViewById(R.id.academicDetailsLinearLayoutGraduation);
        if (l.getVisibility() == View.GONE) {
            l.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.GONE);
        }
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
            Toast.makeText(AcademicDetailsActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void getDataFromDatabase() {
        getDataFromSecondary();
        getDataFromHigherSecondary();
        getDataFromGraduation();
    }
    public void getDataFromSecondary(){
        try {
            progressBar.setVisibility(View.VISIBLE);
            String regno = user.getRegisterNumber();
            DocumentReference docRef = db.collection("Student_Academic_Secondary_Details").document(regno);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            studentAcademicDetails sa = document.toObject(studentAcademicDetails.class);
                            if (sa != null) {
                                edPercentage.setText(sa.getPercentage());
                                edrollno.setText(sa.getRollNo());

                                int spinnerPosition;
                                spinnerPosition = arrayAdapterDOP.getPosition(sa.getDOP());
                                spDOP.setSelection(spinnerPosition);
                                spinnerPosition = arrayAdapterBoard.getPosition(sa.getBoard());
                                spBoard.setSelection(spinnerPosition);

                                progressBar.setVisibility(View.INVISIBLE);

                                edPercentage.setEnabled(false);
                                edrollno.setEnabled(false);
                                spBoard.setEnabled(false);
                                spDOP.setEnabled(false);

                                SecupdateButton.setEnabled(true);
                                SecupdateButton.setBackgroundResource(R.drawable.shapesignup);
                                SecsaveButton.setEnabled(false);
                                SecsaveButton.setBackgroundResource(R.drawable.shapebuttondisabled);

                            }
                        } else {
                            //Log.d(TAG, "No such document");
                            progressBar.setVisibility(View.INVISIBLE);
                            SecupdateButton.setEnabled(false);
                            SecupdateButton.setBackgroundResource(R.drawable.shapebuttondisabled);
                            SecsaveButton.setEnabled(true);
                            SecsaveButton.setBackgroundResource(R.drawable.shapesignup);
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AcademicDetailsActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void getDataFromHigherSecondary(){
        try {
            progressBar.setVisibility(View.VISIBLE);
            String regno = user.getRegisterNumber();
            DocumentReference docRef = db.collection("Student_Academic_Higher_Secondary_Details").document(regno);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            studentAcademicDetails sa = document.toObject(studentAcademicDetails.class);
                            if (sa != null) {
                                edHSPercentage.setText(sa.getPercentage());
                                edhsrollno.setText(sa.getRollNo());
                                int spinnerPosition;
                                spinnerPosition = arrayAdapterHSDOP.getPosition(sa.getDOP());
                                spHSDOP.setSelection(spinnerPosition);
                                spinnerPosition = arrayAdapterHSBoard.getPosition(sa.getBoard());
                                spHSBoard.setSelection(spinnerPosition);

                                progressBar.setVisibility(View.INVISIBLE);

                                edhsrollno.setEnabled(false);
                                edHSPercentage.setEnabled(false);
                                spHSBoard.setEnabled(false);
                                spHSDOP.setEnabled(false);
                                spHSBoard.setEnabled(false);
                                spHSDOP.setEnabled(false);
                                HSecupdateButton.setEnabled(true);
                                HSecupdateButton.setBackgroundResource(R.drawable.shapesignup);
                                HSecsaveButton.setEnabled(false);
                                HSecsaveButton.setBackgroundResource(R.drawable.shapebuttondisabled);

                            }
                        } else {
                            //Log.d(TAG, "No such document");
                            progressBar.setVisibility(View.INVISIBLE);
                            HSecupdateButton.setEnabled(false);
                            HSecupdateButton.setBackgroundResource(R.drawable.shapebuttondisabled);
                            HSecsaveButton.setEnabled(true);
                            HSecsaveButton.setBackgroundResource(R.drawable.shapesignup);
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        //Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AcademicDetailsActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void getDataFromGraduation(){

    }



    public void personalButtonClick(View view) {
        Intent intent = new Intent(AcademicDetailsActivity.this, ProfileNavActivity.class);
        startActivity(intent);
        finish();

    }
}
