package com.example.rajneeshshukla.letsconnect.activities.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMG = 1;
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
    private StorageReference mStoreProfileRef;

    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        // Get instance of Firebase
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
        mStoreProfileRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

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

        mProfileImage.setClickable(true);

        // Add event listener on profile ImageView
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage();
            }
        });

        // On Save Profile info button is pressed
        mSaveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileInfo();
            }
        });

    }

    /**
     * Will choose a image from gallery
     */
    private void selectProfileImage() {
        Intent mGalIntent = new Intent();
        mGalIntent.setAction(Intent.ACTION_GET_CONTENT);
        mGalIntent.setType("image/*");
        startActivityForResult(mGalIntent, PICK_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG && resultCode == RESULT_OK && data != null) {
            Uri mImabeUri = data.getData();

            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult mResult = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri mResultUri = mResult.getUri();

                Log.e("SETUP: ", mResultUri + " ");

                mProfileImage.setImageURI(mResultUri);
                saveImageToFirebase(mResultUri);
            }
        }
    }

    /** Method will save profile image to the firebase database
     * @param mResultUri*/
    private void saveImageToFirebase(Uri mResultUri) {
        StorageReference mFilePath = mStoreProfileRef.child(mCurrentUserId + ".jpg");
    //     TODO:// write code to save the image to the database
    }

    /**
     * Will save the user profile data into the firebase database
     */
    private void saveProfileInfo() {
        String mUserName = mUsername.getText().toString().trim();
        String mUserFullName = mName.getText().toString().trim();

        if (TextUtils.isEmpty(mUserName)) {
            Utility.showShortText(this, getString(R.string.Enter_valid_username));
        } else if (TextUtils.isEmpty(mUserFullName)) {
            Utility.showShortText(this, getString(R.string.Enter_valid_name));
        } else if (!TextUtils.isEmpty(mCountryName)) {
            //TODO: Selection of Country is not working now
            Utility.showShortText(this, getString(R.string.enter_valid_country_nname));
        } else {
            HashMap mUserMap = new HashMap();
            mUserMap.put("user_name", mUserName);
            mUserMap.put("full_name", mUserFullName);
            mUserMap.put("country", mCountryName);
            mUserMap.put("status", getString(R.string.default_status_message));
            mUserMap.put("gender", "none");
            mUserMap.put("bob", "none");
            mUserMap.put("relationship_status", "none");

            mFirebaseRef.updateChildren(mUserMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Utility.showLongText(getApplicationContext(), getString(R.string.accout_created_successfully_message));
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
