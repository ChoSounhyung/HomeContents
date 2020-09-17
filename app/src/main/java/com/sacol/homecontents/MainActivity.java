package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView login;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomNav();
        init();
        setUp();
    }

    private void init() {
        login = findViewById(R.id.go_login);
        signup = findViewById(R.id.go_signup);
    }

    private void setUp() {
        login.setOnClickListener(goLoginPage);
        signup.setOnClickListener(goSignupPage);
    }

    private void setBottomNav() {
        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.hashtag:
                        startActivity(new Intent(getApplicationContext(), HashtagActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), PlusActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.storage:
                        startActivity(new Intent(getApplicationContext(), StorageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mypage:
                        startActivity(new Intent(getApplicationContext(), MypageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    View.OnClickListener goLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goSignupPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    };
}