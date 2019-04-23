package com.example.amma.cir2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticsQuizActivity extends AppCompatActivity {

    ListView listView;
    FirebaseAuth mAuth;
    FirebaseFirestore fb;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<ScoreStatitics> list;
    ScoreStatitics scoreStatitics;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_quiz);
        mAuth = FirebaseAuth.getInstance();
        scoreStatitics = new ScoreStatitics();
        listView = findViewById(R.id.statisticsQuizListView);
        list = new ArrayList<>();
        fb = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("AptitudeQuizScoreBoard");
        fetchRegistrationId();


    }

    void fetchData(final String regid) {
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot userDatasnapshot = dataSnapshot.child(regid);
                    Iterable<DataSnapshot> userChildren = userDatasnapshot.getChildren();
                    for (DataSnapshot userscore : userChildren) {
                        ScoreStatitics sc = userscore.getValue(ScoreStatitics.class);
                        list.add(sc);
                        callArrayAdapter();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(StatisticsQuizActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(StatisticsQuizActivity.this, "error", Toast.LENGTH_LONG).show();
    }

    public void callArrayAdapter() {
        ArrayAdapter<ScoreStatitics> adapter = new ArrayAdapterQuizStatistics(StatisticsQuizActivity.this, 0, list);
        listView.setAdapter(adapter);
    }

    public void fetchRegistrationId() {
        try {
            DocumentReference docRef = fb.collection("User_ID_Registration_ID").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    fetchData(user.registerNumber.toUpperCase());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(StatisticsQuizActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
