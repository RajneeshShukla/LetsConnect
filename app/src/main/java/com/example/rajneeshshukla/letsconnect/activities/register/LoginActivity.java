package com.example.rajneeshshukla.letsconnect.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.activities.home.MainActivity;
import com.example.rajneeshshukla.letsconnect.utils.Utility;
import com.example.rajneeshshukla.letsconnect.utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.rajneeshshukla.letsconnect.R.id.create_new_account_text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mCreateAccountText;
    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpLayout();
        mAuth = FirebaseAuth.getInstance();
    }

    /* Setup layout */
    private void setUpLayout() {
        mCreateAccountText = findViewById(create_new_account_text);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login_button);
        mLoginBtn.setOnClickListener(this);
        mCreateAccountText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_new_account_text:
                Utility.hideSoftKeyboard(this);
                moveToRegisterActivity();
                break;

            case R.id.login_button:
                Utility.hideSoftKeyboard(this);
                loginUserToFirebase();
                break;
        }
    }

    /** Move to MainActivity */
    private void moveToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * Login to Firebase
     * Validate Email and password
     */
    private void loginUserToFirebase() {
        String mUserEmail = mEmail.getText().toString().trim();
        String mUserPassword = mPassword.getText().toString().trim();

        if (!Validate.validateEmail(this, mUserEmail)) {
            Utility.showShortText(this, "Enter valid image...");
        } else if (!Validate.validatePassword(this, mUserPassword)) {
            Utility.showShortText(this, "Enter valid password...");
        } else {
            loginUser(mUserEmail, mUserPassword);
        }
    }


    /**
     * Login User with firebase
     *
     * @param mUserEmail
     * @param mUserPassword
     */
    private void loginUser(String mUserEmail, String mUserPassword) {
        Utility.showLoader(this);
        mAuth.signInWithEmailAndPassword(mUserEmail, mUserPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utility.hideLoader(LoginActivity.this);
                            Utility.showShortText(getApplicationContext(), "Login successful...");
                        } else {
                            Utility.hideLoader(LoginActivity.this);
                            String message = task.getException().getMessage();
                            Utility.showShortText(getApplicationContext(), "Error: " + message);
                        }
                    }
                });
    }

    /**
     * Method will use user to Register Activity
     */
    private void moveToRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}
