//package com.example.amma.cir2;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Nullable;
//
//public class ViewFeedBackActivity extends AppCompatActivity {
//
////the listview
//    ListView listView;
//    ProgressBar progressBar;
//
//    //database reference to get uploads data
//    DatabaseReference mDatabaseReference;
//    FirebaseAuth mAuth;
//    FirebaseFirestore db;
//    User user;
//
//    //list to store uploads data
//    List<feedback> viewFeedBackList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_view_feed_back);
//            fetchRegistrationId();
//            mAuth = FirebaseAuth.getInstance();
//            db = FirebaseFirestore.getInstance();
//            viewData();
//            progressBar = findViewById(R.id.viewFeedbackProgressbar);
////            progressBar.setVisibility(View.VISIBLE);
//
//            viewFeedBackList = new ArrayList<>();
//            listView = (ListView) findViewById(R.id.viewFeedBackListView);
//
//
//
//
//
//
//    }
//public void viewData(){
//    String regno = user.getRegisterNumber();
//            //getting the database reference
////            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
//
//
//            //retrieving upload data from firebase database
//            mDatabaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Upload upload = postSnapshot.getValue(Upload.class);
//                        uploadList.add(upload);
//                    }
//
//                    String[] uploads = new String[uploadList.size()];
//
//                    for (int i = 0; i < uploads.length; i++) {
//                        uploads[i] = uploadList.get(i).getName();
//                    }
//
//                    //displaying it to list
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
//                    listView.setAdapter(adapter);
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//            progressBar.setVisibility(View.GONE);
//        }
//}
//    public void fetchRegistrationId() {
//        try {
//            DocumentReference docRef = db.collection("User_ID_Registration_ID").document(mAuth.getUid());
//            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    user = documentSnapshot.toObject(User.class);
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(ViewFeedBackActivity.this, "Failed to read data" + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//    }
//}
