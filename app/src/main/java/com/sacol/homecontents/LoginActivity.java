package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
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
        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.login_signup);
    }

    private void setUp() {
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
}