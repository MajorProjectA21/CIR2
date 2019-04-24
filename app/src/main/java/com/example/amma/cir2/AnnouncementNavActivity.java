package com.example.amma.cir2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.regex.Pattern;


public class AnnouncementNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    //these are the views
    TextView textViewStatus;
    EditText editTextFilename;
    ProgressBar progressBar;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    //String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_nav);
        this.overridePendingTransition(0, 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Storage");

        mAuth = FirebaseAuth.getInstance();
        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(R.id.editTextFileName);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //attaching listeners to views
        findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        findViewById(R.id.textViewUploads).setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        try {
            //for greater than lolipop versions we need the permissions asked on runtime
            //so if the permission is not available user will go to the screen to allow storage permission
            if (editTextFilename.getText().toString().isEmpty()) {
                editTextFilename.setError("Please Enter a file Name");
                return;
            }
            if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", editTextFilename.getText().toString())) {
                editTextFilename.setError("NoSplecial Characters Allowed");
                return;
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(AnnouncementNavActivity.this, "Please give Storage Permission", Toast.LENGTH_LONG).show();
                return;
            }
            //creating an intent for file chooser
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
        } catch (
                Exception e) {
            e.printStackTrace();
            Toast.makeText(AnnouncementNavActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            //when the user choses the file
            if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                //if a file is selected
                if (data.getData() != null) {
                    //uploading the file
                    uploadFile(data.getData());
                } else {
                    Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AnnouncementNavActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {

        try {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
//            sRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    downloadUrl = task.getResult().toString();
//                    // downloadurl will be the resulted answer
//                }
//            });
            sRef.putFile(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            textViewStatus.setText("File Uploaded Successfully");
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete()) ;
                            Uri url = uri.getResult();
                            //Upload upload = new Upload(editTextFilename.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                            //  Upload upload = new Upload(editTextFilename.getText().toString(), Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString());
                            Upload upload = new Upload(editTextFilename.getText().toString(), url.toString());
                            mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            textViewStatus.setText((int) progress + "% Uploading...");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AnnouncementNavActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUploadFile:
                getPDF();
                break;
            case R.id.textViewUploads:
                startActivity(new Intent(this, ViewUploadsActivity.class));
                break;
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

        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(AnnouncementNavActivity.this, homeNav.class);
                startActivity(h);
                break;
            case R.id.nav_Profile:
                Intent p = new Intent(AnnouncementNavActivity.this, ProfileNavActivity.class);
                startActivity(p);
                break;
            case R.id.nav_Announcement:
                Intent a = new Intent(AnnouncementNavActivity.this, AnnouncementNavActivity.class);
                startActivity(a);
                break;
            case R.id.nav_Notifications:
                Intent n = new Intent(AnnouncementNavActivity.this, NotificationsNavActivity.class);
                startActivity(n);
                break;
            case R.id.nav_AptitudeQuiz:
                Intent ap = new Intent(AnnouncementNavActivity.this, AptitudeQuizNavActivity.class);
                startActivity(ap);
                break;
            case R.id.nav_Feedback:
                Intent f = new Intent(AnnouncementNavActivity.this, FeedbackNavActivity.class);
                startActivity(f);
                break;
            case R.id.nav_ContactUs:
                Intent c = new Intent(AnnouncementNavActivity.this, AboutUsNavActivity.class);
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
                        // AnnouncementNavActivity.this.finish();
                        finishAffinity();
                        mAuth.signOut();
                        Intent intent = new Intent(AnnouncementNavActivity.this, SignInActivity.class);
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
