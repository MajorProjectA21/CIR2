package com.example.amma.cir2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    EditText edFeedback;
    User user;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_nav);
        this.overridePendingTransition(0, 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Feedback");

        edFeedback = findViewById(R.id.feedbackeditText);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fetchRegistrationId();
    }
public void saveToDatabase(View v){
    try {
        //progressBar.setVisibility(View.VISIBLE);
        feedback f = new feedback(edFeedback.getText().toString());
        String regno = user.getRegisterNumber();
        db.collection("Student_Feed_Back").document(regno).collection("feedback").add(f)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(FeedbackNavActivity.this, "Feedback Submitted",
                        Toast.LENGTH_SHORT).show();
                edFeedback.setText("");
                Intent intent = new Intent(FeedbackNavActivity.this,ViewFeedBackActivity.class);
                startActivity(intent);

                //   progressBar.setVisibility(View.INVISIBLE);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FeedbackNavActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                        // progressBar.setVisibility(View.INVISIBLE);
                    }
                });

    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(FeedbackNavActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
public void viewFeedback(View v){
    Intent intent = new Intent(FeedbackNavActivity.this,ViewFeedBackActivity.class);
    startActivity(intent);
}
    public void fetchRegistrationId() {
        try {
            DocumentReference docRef = db.collection("User_ID_Registration_ID").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(FeedbackNavActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

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

        switch (id){

            case R.id.nav_home:
                Intent h = new Intent (FeedbackNavActivity.this,homeNav.class);
                startActivity(h);
                break;
            case R.id.nav_Profile:
                Intent p = new Intent (FeedbackNavActivity.this,ProfileNavActivity.class);
                startActivity(p);
                break;
            case R.id.nav_Announcement:
                Intent a = new Intent (FeedbackNavActivity.this,AnnouncementNavActivity.class);
                startActivity(a);
                break;
            case R.id.nav_Notifications:
                Intent n = new Intent (FeedbackNavActivity.this,NotificationsNavActivity.class);
                startActivity(n);
                break;
            case R.id.nav_AptitudeQuiz:
                Intent ap = new Intent (FeedbackNavActivity.this,AptitudeQuizNavActivity.class);
                startActivity(ap);
                break;
            case R.id.nav_Feedback:
                Intent f = new Intent (FeedbackNavActivity.this,FeedbackNavActivity.class);
                startActivity(f);
                break;
            case R.id.nav_ContactUs:
                Intent c = new Intent (FeedbackNavActivity.this,AboutUsNavActivity.class);
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
                       // FeedbackNavActivity.this.finish();
                        finishAffinity();
                        mAuth.signOut();
                        Intent intent = new Intent(FeedbackNavActivity.this,SignInActivity.class);
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
}
