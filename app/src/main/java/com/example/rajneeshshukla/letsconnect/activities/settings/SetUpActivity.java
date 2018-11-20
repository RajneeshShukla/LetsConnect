package com.example.rajneeshshukla.letsconnect.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.utils.Utility;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mCountrySpinner;
    private CircleImageView mProfileImage;
    private EditText mUsername;
    private EditText mName;
    private String mCountryName;
    private Button mSaveInfoBtn;
    private String[] mCountryNameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        setUpCountrySpinner();
        setUpLayout();
    }

    /** Setup layout component */
    private void setUpLayout() {
        mProfileImage = findViewById(R.id.profile_picture);
        mUsername = findViewById(R.id.user_name_id);
        mName = findViewById(R.id.name);
        mSaveInfoBtn = findViewById(R.id.save_info_btn);
    }


    /** This method will setup spinner to select country name */
    private void setUpCountrySpinner() {
        mCountrySpinner = findViewById(R.id.coutry_spinner);

        mCountryNameArray = getResources().getStringArray(R.array.countries_array);

        //Creating an array adapter which handle country name string array
        ArrayAdapter mCountryAdapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_dropdown_item, mCountryNameArray );
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrySpinner.setAdapter(mCountryAdapter);
    }

    /** Performing action onItemSelected and onNothing selected */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Log.e("TAG", "Item is accessed");
        mCountryNameArray = getResources().getStringArray(R.array.countries_array);
        mCountryName = mCountryNameArray[position];
        Utility.showShortText(this, mCountryName + "selected");
        Toast.makeText(getApplicationContext(),mCountryNameArray[position] , Toast.LENGTH_LONG).show();
    }

    /** When Nothing is selected in Spinner  */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
