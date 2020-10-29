package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;

    private ViewPager viewPager;
    private MainAdapter mainAdapter;
    private List<Model> models;

    private ImageView search_btn;
    private RelativeLayout go_mypage;
    private RelativeLayout go_mystorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.sample1, "5000번 저어 만드는 오므라이스", "asdfasdf00"));
        models.add(new Model(R.drawable.sample2, "아무노래 챌린지", "sdfgsdfg11"));
        models.add(new Model(R.drawable.sample3, "달고나 커피 만들기", "qwerqwer22"));
        models.add(new Model(R.drawable.sample4, "피포페인팅", "zxcvzxcv33"));

        mainAdapter = new MainAdapter(models, this);

        init();
        setUp();

        //Firebase
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startSignupActivity();
        }
    }

    public void init() {
        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.viewPager);
        search_btn = findViewById(R.id.search_btn);
        go_mypage = findViewById(R.id.go_mypage);
        go_mystorage = findViewById(R.id.go_mystrorage);
    }

    public void setUp() {
        fab.setOnClickListener(goPlusPage);
        viewPager.setAdapter(mainAdapter);
        viewPager.setPadding(70, 0, 70, 0);
        search_btn.setOnClickListener(goSearchPage);
        go_mypage.setOnClickListener(goMypage);
        go_mystorage.setOnClickListener(goStorage);
    }

    private void startSignupActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    View.OnClickListener goPlusPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, PlusActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goSearchPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goMypage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goStorage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, MystorageActivity.class);
            startActivity(intent);
        }
    };
}