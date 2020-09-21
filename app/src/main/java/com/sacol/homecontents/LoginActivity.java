package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextView signupBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        init();
        setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
            String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
            String password = ((EditText) findViewById(R.id.login_password)).getText().toString();


            if(email.length() >0 && password.length()>0){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다..", Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(), "모두 입력해주세요", Toast.LENGTH_LONG).show();

            }
//
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