package com.example.amma.cir2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {
    DatabaseReference database;
    TextView tvQuestion, tvTime, tvCountQuestion;
    Button bOption1, bOption2, bOption3, bOption4;
    int total = 0;
    int correct = 0;
    int incorrect = 0;
    CountDownTimer countDownTimer;
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

    public void updateQuestion()
    {
        total++;
        if (total > 6) {//total no of questions
            Intent intent = new Intent(QuizActivity.this, quizResultActivity.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("incorrect", String.valueOf(incorrect));
            startActivity(intent);

//result activity
        } else {
            try {
                database = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            } catch (Exception e) {
                Toast.makeText(QuizActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final questionClass question;

                    question = dataSnapshot.getValue(questionClass.class);
                    tvQuestion.setText(question.getQuestion());
                    bOption1.setText(question.getOption1());
                    bOption2.setText(question.getOption2());
                    bOption3.setText(question.getOption3());
                    bOption4.setText(question.getOption4());
                    reverseTimer(60, tvTime);

                    bOption1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvCountQuestion.setText(String.valueOf(total));
                            if (bOption1.getText().toString().equals(question.getAnswer())) {
                                bOption1.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bOption1.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
                            } else {
                                incorrect++;
                                bOption1.setBackgroundColor(Color.RED);
                                if (bOption2.getText().toString().equals(question.getAnswer())) {
                                    bOption2.setBackgroundColor(Color.GREEN);
                                } else if (bOption3.getText().toString().equals(question.getAnswer())) {
                                    bOption3.setBackgroundColor(Color.GREEN);
                                } else if (bOption4.getText().toString().equals(question.getAnswer())) {
                                    bOption4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bOption1.setBackgroundResource(R.drawable.option);
                                        bOption2.setBackgroundResource(R.drawable.option);
                                        bOption3.setBackgroundResource(R.drawable.option);
                                        bOption4.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });
                    //button2 click
                    bOption2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvCountQuestion.setText(String.valueOf(total));
                            if (bOption2.getText().toString().equals(question.getAnswer())) {
                                bOption2.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bOption2.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
                            } else {
                                incorrect++;
                                bOption2.setBackgroundColor(Color.RED);
                                if (bOption1.getText().toString().equals(question.getAnswer())) {
                                    bOption1.setBackgroundColor(Color.GREEN);
                                } else if (bOption3.getText().toString().equals(question.getAnswer())) {
                                    bOption3.setBackgroundColor(Color.GREEN);
                                } else if (bOption4.getText().toString().equals(question.getAnswer())) {
                                    bOption4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
//                                        bOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
//                                        bOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
//                                        bOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
//                                        bOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bOption1.setBackgroundResource(R.drawable.option);
                                        bOption2.setBackgroundResource(R.drawable.option);
                                        bOption3.setBackgroundResource(R.drawable.option);
                                        bOption4.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();

                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });
                    //button 3
                    bOption3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvCountQuestion.setText(String.valueOf(total));
                            if (bOption3.getText().toString().equals(question.getAnswer())) {
                                bOption3.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bOption3.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
                            } else {
                                incorrect++;
                                bOption3.setBackgroundColor(Color.RED);
                                if (bOption1.getText().toString().equals(question.getAnswer())) {
                                    bOption1.setBackgroundColor(Color.GREEN);
                                } else if (bOption2.getText().toString().equals(question.getAnswer())) {
                                    bOption2.setBackgroundColor(Color.GREEN);
                                } else if (bOption4.getText().toString().equals(question.getAnswer())) {
                                    bOption4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bOption1.setBackgroundResource(R.drawable.option);
                                        bOption2.setBackgroundResource(R.drawable.option);
                                        bOption3.setBackgroundResource(R.drawable.option);
                                        bOption4.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();

                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });
                    //button 4
                    bOption4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvCountQuestion.setText(String.valueOf(total));
                            if (bOption4.getText().toString().equals(question.getAnswer())) {
                                bOption4.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bOption4.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
                            } else {
                                incorrect++;
                                bOption4.setBackgroundColor(Color.RED);
                                if (bOption1.getText().toString().equals(question.getAnswer())) {
                                    bOption1.setBackgroundColor(Color.GREEN);
                                } else if (bOption2.getText().toString().equals(question.getAnswer())) {
                                    bOption2.setBackgroundColor(Color.GREEN);
                                } else if (bOption3.getText().toString().equals(question.getAnswer())) {
                                    bOption3.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bOption1.setBackgroundResource(R.drawable.option);
                                        bOption2.setBackgroundResource(R.drawable.option);
                                        bOption3.setBackgroundResource(R.drawable.option);
                                        bOption4.setBackgroundResource(R.drawable.option);
                                        countDownTimer.cancel();
                                        updateQuestion();
                                    }
                                }, 1500);
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

    public void reverseTimer(int seconds, final TextView textView) {
        countDownTimer = new CountDownTimer(seconds * 1000 + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                textView.setText("--:--");
                Intent intent = new Intent(QuizActivity.this, quizResultActivity.class);
                intent.putExtra("total", String.valueOf(total));
                intent.putExtra("correct", String.valueOf(correct));
                intent.putExtra("incorrect", String.valueOf(incorrect));
            }
        }.start();
    }

}
