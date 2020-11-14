package kr.hs.emirim.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sacol.homecontents.R;

import java.util.ArrayList;
import java.util.Collections;
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
    private ProgressDialog progressDialog;

    private DatabaseReference databaseRefernece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startSignupActivity();
        }

        init();
        setUp();
        initDatabase();

    }

    public void init() {
        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.viewPager);
        search_btn = findViewById(R.id.search_btn);
        go_mypage = findViewById(R.id.go_mypage);
        go_mystorage = findViewById(R.id.go_mystrorage);
        databaseRefernece = FirebaseDatabase.getInstance().getReference();
        models = new ArrayList<>();
        mainAdapter = new MainAdapter(models, this);
    }

    public void setUp() {
        fab.setOnClickListener(goPlusPage);
        viewPager.setAdapter(mainAdapter);
        viewPager.setPadding(70, 0, 70, 0);
        search_btn.setOnClickListener(goSearchPage);
        go_mypage.setOnClickListener(goMypage);
        go_mystorage.setOnClickListener(goStorage);
    }

    private void initDatabase() {
        databaseRefernece.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot homecontent : snapshot.getChildren()) {
                    models.add(new Model((String) homecontent.child("ImgLink").child("ImgLink0").getValue(), (String) homecontent.child("title").getValue(), (String) homecontent.child("content").getValue(), homecontent.getKey()));
                }
                Collections.reverse(models);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
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