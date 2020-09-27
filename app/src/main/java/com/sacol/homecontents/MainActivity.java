package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import fragment.HashtagFragment;
import fragment.MainFragment;
import fragment.MypageFragment;
import fragment.StorageFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    //Frame Layout에 각 메뉴의Fragment를 바꿔줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFragment mainFragment = new MainFragment();
    private HashtagFragment hashtagFragment = new HashtagFragment();
    private StorageFragment storageFragment = new StorageFragment();
    private MypageFragment mypageFragment = new MypageFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()== null){
            startSignupActivity();
        }

        init();
        setUp();

        //첫화면 지정
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_layout, mainFragment).commitNowAllowingStateLoss();

        bottomNavigationView = findViewById(R.id.bottom_nav);

        //bottomNavigationView의 아이템이 선택 될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:transaction.replace(R.id.main_layout, mainFragment).commitNowAllowingStateLoss();
                        return false;
                    case R.id.hashtag:
                        transaction.replace(R.id.main_layout, hashtagFragment).commitNowAllowingStateLoss();
                        return false;
                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), PlusActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.storage:
                        transaction.replace(R.id.main_layout, storageFragment).commitNowAllowingStateLoss();
                        return false;
                    case R.id.mypage:
                        transaction.replace(R.id.main_layout, mypageFragment).commitNowAllowingStateLoss();
                        return false;
                }return false;
            }
        });

    }

    private void init() {

    }

    private void setUp() {

    }


    private void startSignupActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}