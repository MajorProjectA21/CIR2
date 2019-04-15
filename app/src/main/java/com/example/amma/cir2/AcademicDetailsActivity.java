package com.example.amma.cir2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AcademicDetailsActivity extends AppCompatActivity {
    EditText edPercentage,edHSPercentage;
    Spinner spDOP,spBoard,spHSDOP,spHSBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_details);
        this.overridePendingTransition(0, 0);

        edPercentage = findViewById(R.id.AcademicPercentageEditText);
        edHSPercentage = findViewById(R.id.AcademicHSPercentageEditText);

        spDOP = findViewById(R.id.AcademicYearOFPassing);

        spHSDOP = findViewById(R.id.AcademicHSYearOFPassing);

        spBoard = findViewById(R.id.AcademicBoardOfExaminationEditTExt);
        ArrayAdapter<String> arrayAdapterBoard = new ArrayAdapter<String>(AcademicDetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoardOfExaminationArray));
        spHSBoard = findViewById(R.id.AcademicHSBoardOfExaminationEditTExt);
    }
}
