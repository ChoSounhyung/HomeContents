package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private Button logout_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    init();
    setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {
        logout_btn = findViewById(R.id.logout_btn);
    }

    private void setUp() {
        logout_btn.setOnClickListener(goSignupPAge);
    }

    View.OnClickListener goSignupPAge = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            startSignUpActivity();
        }
    };

    private void startSignUpActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}