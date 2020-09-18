package com.sacol.homecontents;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private ImageView backBtn;
    private Button loginBtn;
    private TextView signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setUp();

    }

    private void init() {
        backBtn = findViewById(R.id.login_back);
        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.login_signup);
    }

    private void setUp() {
        backBtn.setOnClickListener(goBackPage);
        loginBtn.setOnClickListener(goMainPage);
        signupBtn.setOnClickListener(goSignupPage);

    }

    View.OnClickListener goMainPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goSignupPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LoginActivity.this.finish();
        }
    };
}