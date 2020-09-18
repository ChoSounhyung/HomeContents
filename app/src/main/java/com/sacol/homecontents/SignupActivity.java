package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    private TextView loginBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        setUp();
    }

    private void init() {
        loginBtn = findViewById(R.id.signup_login);
        backBtn = findViewById(R.id.signup_back);
    }

    private void setUp() {
        loginBtn.setOnClickListener(goLoginPage);
        backBtn.setOnClickListener(goBackPage);
    }

    View.OnClickListener goLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SignupActivity.this.finish();
        }
    };
}