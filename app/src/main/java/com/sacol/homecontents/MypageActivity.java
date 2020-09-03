package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

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
}