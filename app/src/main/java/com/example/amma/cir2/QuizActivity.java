package com.example.amma.cir2;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {
    DatabaseReference database;
    TextView tvQuestion, tvTime, tvCountQuestion;
    Button bOption1, bOption2, bOption3, bOption4;
    int total = 1;
    int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.overridePendingTransition(0, 0);
        tvCountQuestion = findViewById(R.id.text_view_question_count);
        tvQuestion = findViewById(R.id.text_view_question);
        tvTime = findViewById(R.id.text_view_countdown);

        bOption1 = findViewById(R.id.button1);
        bOption2 = findViewById(R.id.button2);
        bOption3 = findViewById(R.id.button3);
        bOption4 = findViewById(R.id.button4);
        updateQuestion();

    }

    public void updateQuestion() {
        database = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final questionClass question = dataSnapshot.getValue(questionClass.class);
                tvQuestion.setText(question.getQuestion());
                bOption1.setText(question.getOption1());
                bOption2.setText(question.getOption2());
                bOption3.setText(question.getOption3());
                bOption4.setText(question.getOption4());
                bOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bOption1.getText().toString().equals(question.getAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    bOption1.setBackgroundColor(Color.GREEN);
                                    updateQuestion();
                                }
                            },1500);
                        }
                        else{
                                bOption1.setBackgroundColor(Color.RED);
                            if (bOption2.getText().toString().equals(question.getAnswer())) {
bOption2.setBackgroundColor(Color.GREEN);
                            }
                            else if (bOption3.getText().toString().equals(question.getAnswer())){
                                bOption3.setBackgroundColor(Color.GREEN);
                            }
                            else if(bOption4.getText().toString().equals(question.getAnswer())){
                                bOption4.setBackgroundColor(Color.GREEN);
                            }
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    bOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    bOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    bOption4.setBackgroundColor(Color.parseColor("#03A9F4"));

                                    updateQuestion();
                                }
                            },1500);
                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
