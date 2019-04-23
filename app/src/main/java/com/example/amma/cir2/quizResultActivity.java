package com.example.amma.cir2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class quizResultActivity extends AppCompatActivity {

    TextView t1, t2, t3;
    User user;
    FirebaseFirestore fb;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    String correct, incorrect, total, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        t1 = findViewById(R.id.text_view_total_questions_value);
        t2 = findViewById(R.id.text_view_Correct_Answers_value);
        t3 = findViewById(R.id.text_view_Wrong_Answers_value);

        fb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("AptitudeQuizScoreBoard");
        Intent intent = getIntent();

        total = intent.getStringExtra("total");
        correct = intent.getStringExtra("correct");
        incorrect = intent.getStringExtra("incorrect");
        date = intent.getStringExtra("date");
        fetchRegistrationId();
        t1.setText(total);
        t2.setText(correct);
        t3.setText(incorrect);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(quizResultActivity.this, AptitudeQuizNavActivity.class);
                startActivity(i);
                finish();
            }
        }, 8000);
    }

    public void saveDataToDatabase(String regNo) {
// We can also chain the two calls together
        ScoreStatitics statitics = new ScoreStatitics(correct,incorrect,total,date);
        ref.child(regNo).push().setValue(statitics);
    }

    public void fetchRegistrationId() {
        try {
            DocumentReference docRef = fb.collection("User_ID_Registration_ID").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    saveDataToDatabase(user.registerNumber);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(quizResultActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
