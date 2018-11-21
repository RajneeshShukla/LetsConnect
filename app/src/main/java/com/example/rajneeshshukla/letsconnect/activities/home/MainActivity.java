package com.example.rajneeshshukla.letsconnect.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.activities.register.LoginActivity;
import com.example.rajneeshshukla.letsconnect.activities.settings.SetUpProfileActivity;
import com.example.rajneeshshukla.letsconnect.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mPostList;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToogle;

    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance(); // get the instance of firebase

        // Get reference to firebase database
        mFirebaseDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        setLayout();
        setEventOnDrawerMenu();
    }

    /* Choose which menu item is checked on drawer*/
    private void setEventOnDrawerMenu() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                userMenuSelector(menuItem);
                return false;
            }
        });
    }

    /* Setup variables and header on drawer */
    private void setLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);

        // set toolbar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");
        mToogle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout ,R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View mNavView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mCurrentUser = mFirebaseAuth.getCurrentUser();

        if(mCurrentUser == null){
            sendUserToLoginActivity();
        }else{
            checkUserExistence();
        }

    }



    /**
     * Check if user is authenticated and its profile data is not available in firebase database
     *  In this case send user to setUpProfile activity to complete his profile
     * */
    private void checkUserExistence() {
        final String mCurrentUserId = mFirebaseAuth.getCurrentUser().getUid();

        //TODO: this functionality is not working now complete this
        mFirebaseDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(!dataSnapshot.hasChild(mCurrentUserId)){
                   sendUserToSetupProfileActivity();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /** method will open SetupProfileActivity */
    private void sendUserToSetupProfileActivity() {
        startActivity(new Intent(this, SetUpProfileActivity.class));
        finish();
    }

    /** Send user to login activity */
    private void sendUserToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /** set Action based on menu item selected */
    private void userMenuSelector(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_new_post:
                Toast.makeText(this, "New Post", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_friends:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_find_friends:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_messages:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_setting:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
              mFirebaseAuth.signOut();
                Utility.showShortText(this, "Logout successfully");
                moveToLoginActivity();
                break;
        }
    }

    /** Will  move to LoginActivity */
    private void moveToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
