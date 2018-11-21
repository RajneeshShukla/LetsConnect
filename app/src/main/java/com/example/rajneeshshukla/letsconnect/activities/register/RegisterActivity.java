package com.example.rajneeshshukla.letsconnect.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.activities.settings.SetUpProfileActivity;
import com.example.rajneeshshukla.letsconnect.utils.Utility;
import com.example.rajneeshshukla.letsconnect.utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupLayout();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Will initialize the ui components
     */
    private void setupLayout() {
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mRegisterBtn = findViewById(R.id.register_button);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });

    }

    /** This method will create new User account*/
    private void createNewAccount() {
        //TODO// Check here is mobile is connect to internet

        Utility.hideSoftKeyboard(RegisterActivity.this);

        String mUserEmail = mEmail.getText().toString().trim();
        String mUserPassword = mPassword.getText().toString().trim();
        String mUserConfirmPassword = mConfirmPassword.getText().toString().trim();

        if (!Validate.validateEmail(this, mUserEmail)) {
            Utility.showLongText(this, "Enter Valid Email...");
        } else if (!Validate.validatePassword(this, mUserPassword)) {
            Utility.showLongText(this, "Enter Valid password with min 6 length...");
        } else if (!Validate.validateConfirmPassword(this, mUserPassword, mUserConfirmPassword)) {
            Utility.showLongText(this, "Password do not match...");
        } else {
            Utility.showLoader(this);
            mAuth.createUserWithEmailAndPassword(mUserEmail, mUserPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Utility.hideLoader(RegisterActivity.this);
                                startActivity(new Intent(getApplicationContext(), SetUpProfileActivity.class));
                                Utility.showLongText(getApplicationContext(), "Account is created successfully...");
                                finish();
                            } else {
                                Utility.hideLoader(RegisterActivity.this);
                                String message = task.getException().getMessage();
                                Utility.showLongText(getApplicationContext(), "Error: " + message);
                            }
                        }
                    });
        }
    }
}
