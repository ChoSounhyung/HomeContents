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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    String title;
    String content;
    int img;
    private DatabaseReference databaseRefernece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        models.add(new Model(R.drawable.sample1, "5000번 저어 만드는 오므라이스", "asdfasdf00"));
//        models.add(new Model(R.drawable.sample2, "아무노래 챌린지", "sdfgsdfg11"));
//        models.add(new Model(R.drawable.sample3, "달고나 커피 만들기", "qwerqwer22"));
//        models.add(new Model(R.drawable.sample4, "피포페인팅", "zxcvzxcv33"));
//
//        mainAdapter = new MainAdapter(models, this);

        init();
        setUp();
        initDatabase();
        models.add(new Model(R.drawable.sample1, title,content));

        //Firebase
        mainAdapter = new MainAdapter(models, this);
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
        databaseRefernece = FirebaseDatabase.getInstance().getReference();
        models = new ArrayList<>();
    }

    private void initDatabase() {


        databaseRefernece.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();

                for (DataSnapshot homecontent : snapshot.getChildren()) {

                    title = (String) homecontent.child("title").getValue();
                    content = (String) homecontent.child("content").getValue();
                    img =R.drawable.sample1;
                    models.add(new Model(img, title,content));
//                    showToast(content.child("title").getValue().toString());
                }
//                models.notify();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        models.add(new Model(R.drawable.sample1, "dd","dd"));
//        models.add(new Model(R.drawable.sample1, "dd","dd"));

//        mainAdapter = new MainAdapter(models, this);

    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    private void startSignupActivity() {
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