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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {
    private Button signup_btn;
    private TextView signup_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
        signup_btn = findViewById(R.id.signup_btn);
        signup_login = findViewById(R.id.signup_login);
    }

    private void setUp() {
        signup_btn.setOnClickListener(goLoginPage);
        signup_login.setOnClickListener(goRealLoginPage);

    }

    void toast(String str){

    }

    View.OnClickListener goLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String name = ((TextInputEditText) findViewById(R.id.signUp_name)).getText().toString();
            final String email = ((TextInputEditText) findViewById(R.id.signUp_email)).getText().toString();
            final String password = ((TextInputEditText) findViewById(R.id.signUp_pwd)).getText().toString();
            final String passwordConfirm = ((TextInputEditText) findViewById(R.id.signUp_pwdConfirm)).getText().toString();


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (password.equals(passwordConfirm)) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    if(task.getException() != null){
                                        showToast(task.getException().toString());

                                    }else{
                                        showToast("회원가입에 실패하셨습니다.");
                                    }
                                }
                            } else {
                                showToast("비밀번호를 확인해주세요.");
                            }
                        }

                    });

        }
    };

    View.OnClickListener goRealLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    private void showToast(String str){
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();
    }
}
