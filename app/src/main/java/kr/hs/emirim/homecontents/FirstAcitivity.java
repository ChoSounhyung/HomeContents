package kr.hs.emirim.homecontents;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class FirstAcitivity extends AppCompatActivity {

    private Button go_login_btn;
    private Button go_singup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        init();
        setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        go_singup_btn = findViewById(R.id.go_signup_btn);
        go_login_btn = findViewById(R.id.go_login_btn);
    }

    private void setUp() {
        go_singup_btn.setOnClickListener(goSingupPage);
        go_login_btn.setOnClickListener(goLoginPage);
    }

    View.OnClickListener goLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startLoginActivity();
        }
    };
    View.OnClickListener goSingupPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startSignUpActivity();
        }
    };

    private void startSignUpActivity(){
        Intent intent = new Intent(this, Personalinfo.class);
        startActivity(intent);
    }
    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}