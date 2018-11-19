package com.example.rajneeshshukla.letsconnect.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.rajneeshshukla.letsconnect.R;

public class LoginActivity extends AppCompatActivity {

    private TextView mCreateAccountText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCreateAccountText = findViewById(R.id.create_new_account_text);

        mCreateAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class ));
            }
        });
    }
}
