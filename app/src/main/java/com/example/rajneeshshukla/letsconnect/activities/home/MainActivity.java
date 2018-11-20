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
import com.example.rajneeshshukla.letsconnect.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mPostList;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToogle;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();
        setEventOnDrawerMenu();

        mFirebaseAuth = FirebaseAuth.getInstance(); // get the instance of firebase
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

        FirebaseUser mUser = mFirebaseAuth.getCurrentUser();
        if(mUser == null){
            startActivity(new Intent(this, LoginActivity.class));
        }
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
