package com.example.rajneeshshukla.letsconnect.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.activities.home.MainActivity;
import com.example.rajneeshshukla.letsconnect.utils.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mCountrySpinner;
    private CircleImageView mProfileImage;
    private EditText mUsername;
    private EditText mName;
    private String mCountryName = "";
    private Button mSaveInfoBtn;
    private String[] mCountryNameArray;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseData;
    private DatabaseReference mFirebaseRef;

    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        // Get instance of Firebase
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);

        setUpCountrySpinner();
        setUpLayout();
    }

    /**
     * Setup layout component
     */
    private void setUpLayout() {
        mProfileImage = findViewById(R.id.profile_picture);
        mUsername = findViewById(R.id.user_name_id);
        mName = findViewById(R.id.name);
        mSaveInfoBtn = findViewById(R.id.save_info_btn);

        mSaveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileInfo();
            }
        });

    }

    /**
     * Will save the user profile data into the firebase database
     */
    private void saveProfileInfo() {
        String mUserName = mUsername.getText().toString().trim();
        String mUserFullName = mName.getText().toString().trim();

        if (TextUtils.isEmpty(mUserName)) {
            Utility.showShortText(this, "Enter valid username");
        } else if (TextUtils.isEmpty(mUserFullName)) {
            Utility.showShortText(this, "Enter valid name");
        } else if (!TextUtils.isEmpty(mCountryName)) {
            //TODO: Selection of Country is not working now
            Utility.showShortText(this, "Choose a valid country name");
        } else {
            HashMap mUserMap = new HashMap();
            mUserMap.put("user_name", mUserName);
            mUserMap.put("full_name", mUserFullName);
            mUserMap.put("country", mCountryName);
            mUserMap.put("status", "Hi, there I am using Let's Connect");
            mUserMap.put("gender", "none");
            mUserMap.put("bob", "none");
            mUserMap.put("relationship_status", "none");

            mFirebaseRef.updateChildren(mUserMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Utility.showLongText(getApplicationContext(), "Your account is created successfully");
                        sendUserToMainActivity();
                    } else {
                        Utility.showLongText(getApplicationContext(), task.getException().getMessage());
                    }
                }
            });

        }
    }

    /**
     * Will send user to MainActivity
     */
    private void sendUserToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    /**
     * This method will setup spinner to select country name
     */
    private void setUpCountrySpinner() {
        mCountrySpinner = findViewById(R.id.coutry_spinner);

        mCountryNameArray = getResources().getStringArray(R.array.countries_array);

        //Creating an array adapter which handle country name string array
        ArrayAdapter mCountryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mCountryNameArray);
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrySpinner.setAdapter(mCountryAdapter);
    }

    /**
     * Performing action onItemSelected and onNothing selected
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Log.e("TAG", "Item is accessed");
        mCountryNameArray = getResources().getStringArray(R.array.countries_array);
        mCountryName = mCountryNameArray[position];
        Utility.showShortText(this, mCountryName + "selected");
        Toast.makeText(getApplicationContext(), mCountryNameArray[position], Toast.LENGTH_LONG).show();
    }

    /**
     * When Nothing is selected in Spinner
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
