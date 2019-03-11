package com.example.amma.cir2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class homeNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav);
        this.overridePendingTransition(0, 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
               Intent h = new Intent (homeNav.this,homeNav.class);
               startActivity(h);
               break;
           case R.id.nav_Profile:
               Intent p = new Intent (homeNav.this,ProfileNavActivity.class);
               startActivity(p);
               break;
           case R.id.nav_Announcement:
               Intent a = new Intent (homeNav.this,AnnouncementNavActivity.class);
               startActivity(a);
               break;
           case R.id.nav_Notifications:
               Intent n = new Intent (homeNav.this,NotificationsNavActivity.class);
               startActivity(n);
               break;
           case R.id.nav_AptitudeQuiz:
               Intent ap = new Intent (homeNav.this,AptitudeQuizNavActivity.class);
               startActivity(ap);
               break;
           case R.id.nav_Feedback:
               Intent f = new Intent (homeNav.this,FeedbackNavActivity.class);
               startActivity(f);
               break;
           case R.id.nav_ContactUs:
               Intent c = new Intent (homeNav.this,AboutUsNavActivity.class);
               startActivity(c);
               break;
           case R.id.nav_Logout:
               Intent l = new Intent (homeNav.this,homeNav.class);
               startActivity(l);
               break;

       }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
