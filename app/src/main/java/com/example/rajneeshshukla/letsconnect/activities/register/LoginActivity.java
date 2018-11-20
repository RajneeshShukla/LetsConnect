package com.example.rajneeshshukla.letsconnect.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rajneeshshukla.letsconnect.R;

public class LoginActivity extends AppCompatActivity {

    private TextView mCreateAccountText;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mCreateAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpLayout();
    }

    /* Setup layout */
    private void setUpLayout() {
        mCreateAccountText = findViewById(R.id.create_new_account_text);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);

        mCreateAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class ));
                finish();
            }
        });
    }
}
