package kr.hs.emirim.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private Button signup_btn;
    private TextView signup_login;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private CheckBox personalinfo_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

        setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void init() {
        signup_btn = findViewById(R.id.signup_btn);
        signup_login = findViewById(R.id.signup_login);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        personalinfo_checkbox = findViewById(R.id.personalinfo_checkbox);
    }

    private void setUp() {
        signup_btn.setOnClickListener(goLoginPage);
        signup_login.setOnClickListener(goRealLoginPage);

    }

    View.OnClickListener goLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String name = ((TextInputEditText) findViewById(R.id.signUp_name)).getText().toString();
            final String email = ((TextInputEditText) findViewById(R.id.signUp_email)).getText().toString();
            final String password = ((TextInputEditText) findViewById(R.id.signUp_pwd)).getText().toString();
            final String passwordConfirm = ((TextInputEditText) findViewById(R.id.signUp_pwdConfirm)).getText().toString();

            if (personalinfo_checkbox.isChecked()) {
                if (password.equals(passwordConfirm)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        HashMap<Object, String> userDate = new HashMap<>();
                                        userDate.put("uid", mAuth.getUid());
                                        userDate.put("email", email);
                                        userDate.put("name", name);
                                        mDatabase.child("users").child(mAuth.getUid()).setValue(userDate);
                                        startLoginActivity();
                                    } else {
                                        showToast("회원가입에 실패했습니다.");
                                    }
                                }
                            });
                } else {
                    showToast("비밀번호를 확인해주세요.");
                }
            }else{
                showToast("개인정보를 확인해주세요");
            }
        }
    };

    View.OnClickListener goRealLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startLoginActivity();
        }
    };

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}