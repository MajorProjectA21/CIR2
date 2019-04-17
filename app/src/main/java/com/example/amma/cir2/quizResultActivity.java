package com.example.amma.cir2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class quizResultActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        t1 = findViewById(R.id.text_view_total_questions_value);
        t2 = findViewById(R.id.text_view_Correct_Answers_value);
        t3 = findViewById(R.id.text_view_Wrong_Answers_value);

        Intent intent = getIntent();

        String questions = intent.getStringExtra("total");
        String correct = intent.getStringExtra("correct");
        String wrong = intent.getStringExtra("incorrect");

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(quizResultActivity.this, AptitudeQuizNavActivity.class);
                startActivity(i);
                finish();
            }
        }, 8000);
    }
}
