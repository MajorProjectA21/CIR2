package com.example.amma.cir2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

public class FeedbackNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    User user;
    FirebaseFirestore fb;
    DatabaseReference ref;
    FirebaseDatabase database;
    ListView myListView;

    FloatingActionButton fab;
    ArrayList<String> arrayOfTime;
    String regno;


    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_nav);
        this.overridePendingTransition(0, 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("GroupChat");

        mAuth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        myListView = (ListView)findViewById(R.id.list_of_messages);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        arrayOfTime = new ArrayList<String>();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("chatsMessage");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                displayChatMessages();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        displayChatMessages();
        fetchRegistrationId();
        scrollMyListViewToBottom();
    }
    private void displayChatMessages() {
        final ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        try {
            adapter = new FirebaseListAdapter<ChatMessage>(FeedbackNavActivity.this, ChatMessage.class,
                    R.layout.message, FirebaseDatabase.getInstance().getReference("chatsMessage"))
            {

                @Override
                protected void populateView(View v, ChatMessage model, int position) {
                    // Get references to the views of message.xml
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                    TextView messageUserOwn = v.findViewById(R.id.message_text_own);
                    messageTime.setVisibility(View.INVISIBLE);
                    messageUserOwn.setVisibility(View.INVISIBLE);
                    messageUser.setVisibility(View.INVISIBLE);
                    arrayOfTime.add(""+DateFormat.format("dd-MMM-yy", model.getMessageTime()));
                    if (position > 0) {
                        if (arrayOfTime.get(position).equalsIgnoreCase(arrayOfTime.get(position - 1))) {
                            messageTime.setVisibility(View.GONE);
                        } else {
                            messageTime.setVisibility(View.VISIBLE);
                        }
                    } else {
                        messageTime.setVisibility(View.VISIBLE);
                    }
                    if(model.getMessageUser().equals(regno)){
                        String mtext = model.getMessageText();
                        String mtime = ""+DateFormat.format("hh:mm a",
                                model.getMessageTime());
                        String s = mtext +"&nbsp&nbsp&nbsp"+"<small>"+"<sub>" +mtime +"</sub>"+"</small>";

                        messageUserOwn.setText(Html.fromHtml(s));
                        messageUserOwn.setVisibility(View.VISIBLE);
                        messageText.setVisibility(View.INVISIBLE);

                    }else {
                        String mtext = model.getMessageText();
                        String mtime = ""+DateFormat.format("hh:mm a",
                                model.getMessageTime());
                        String s = mtext +"&nbsp&nbsp&nbsp"+"<small>"+"<sub>" +mtime +"</sub>"+"</small>";

                        messageText.setText(Html.fromHtml(s));
                        messageText.setVisibility(View.VISIBLE);
                        messageUserOwn.setVisibility(View.INVISIBLE);
                    }

                    //messageText.setText(model.getMessageText());
                    //messageText.setText(mtext);
                    if(model.getMessageUser().equals(regno)){
                        messageUser.setVisibility(View.INVISIBLE);

                    }else {
                        messageUser.setText(model.getMessageUser());
                        messageUser.setVisibility(View.VISIBLE);
                    }

                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MMM-yy",
                            model.getMessageTime()));

                }
            };

            listOfMessages.setAdapter(adapter);
            //listOfMessages.setAdapter(adapter);




        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void scrollMyListViewToBottom() {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(adapter.getCount() - 1);
            }
        });
    }
    public void saveChatToDatabase(final String regNo) {


            try {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText input1 = (EditText)findViewById(R.id.input);
                        if(input1.getText().toString().isEmpty()){
                            return;
                        }

                        EditText input = (EditText) findViewById(R.id.input);
                        // Read the input field and push a new instance
                        // of ChatMessage to the Firebase database
                   /* FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName())
                            );*/
                        FirebaseDatabase.getInstance()
                                .getReference("chatsMessage")
                                .push()
                                .setValue(new ChatMessage(input.getText().toString(),
                                        regNo)
                                );

                        // Clear the input
                        input.setText("");
                        displayChatMessages();
                        //regno = regNo;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

    }

    public void fetchRegistrationId() {
        try {
            DocumentReference docRef = fb.collection("User_ID_Registration_ID").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    saveChatToDatabase(user.registerNumber);
                    regno = user.getRegisterNumber();
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(FeedbackNavActivity.this, homeNav.class);
                startActivity(h);
                break;
            case R.id.nav_Profile:
                Intent p = new Intent(FeedbackNavActivity.this, ProfileNavActivity.class);
                startActivity(p);
                break;
            case R.id.nav_Announcement:
                Intent a = new Intent(FeedbackNavActivity.this, AnnouncementNavActivity.class);
                startActivity(a);
                break;
            case R.id.nav_Notifications:
                Intent n = new Intent(FeedbackNavActivity.this, NotificationsNavActivity.class);
                startActivity(n);
                break;
            case R.id.nav_AptitudeQuiz:
                Intent ap = new Intent(FeedbackNavActivity.this, AptitudeQuizNavActivity.class);
                startActivity(ap);
                break;
            case R.id.nav_Feedback:
                Intent f = new Intent(FeedbackNavActivity.this, FeedbackNavActivity.class);
                startActivity(f);
                break;
            case R.id.nav_ContactUs:
                Intent c = new Intent(FeedbackNavActivity.this, AboutUsNavActivity.class);
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
                        Intent intent = new Intent(FeedbackNavActivity.this, SignInActivity.class);
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
