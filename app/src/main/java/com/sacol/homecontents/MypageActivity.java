package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class MypageActivity extends AppCompatActivity {
    private ImageView mypage_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        setBottomNav();
        init();
        setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {
        mypage_setting = findViewById(R.id.mypage_setting);
    }

    private void setUp() {
        mypage_setting.setOnClickListener(goSettingPage);
    }

    View.OnClickListener goSettingPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startSettingActivity();
        }
    };

    private void setBottomNav() {
        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //Set mypage selected
        bottomNavigationView.setSelectedItemId(R.id.mypage);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }
                return false;
            }
        });
    }
    private void startSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}