package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.DetailAdapter;
import adapter.MainAdapter;

public class DetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DetailAdapter detailAdapter;
    private List<Model> models;

    private ImageView back;
    private ImageView save;
    private boolean flag;
    private String date;

    private TextView detail_title;
    private TextView detail_contents;
    private DatabaseReference databaseReference;
    private TextView detail_nickname;
    private String uid;
    private DatabaseReference mDatabase;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        init();
        setUp();
        initDatabase();
    }

    public void init() {
        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.detail_back);
        save = findViewById(R.id.detail_storage);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        models = new ArrayList<>();
        detailAdapter = new DetailAdapter(models, this);
        detail_contents =findViewById(R.id.detail_contents);
        detail_title = findViewById(R.id.detail_title);
        detail_nickname = findViewById(R.id.detail_nickname);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid().toString();

    }

    public void setUp() {
        viewPager.setAdapter(detailAdapter);
        viewPager.setPadding(0, 0, 0, 0);
        back.setOnClickListener(goBackPage);
        save.setOnClickListener(saveContent);
    }

    private void initDatabase() {
        databaseReference.child("contents").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot homecontent : snapshot.getChildren()) {
                    models.add(new Model(homecontent.getValue().toString()));
                }
                detail_contents.setText(snapshot.child("content").getValue().toString());
                detail_title.setText(snapshot.child("title").getValue().toString());

                detailAdapter.notifyDataSetChanged();
                databaseReference.child("users").child(snapshot.child("uid").getValue().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        detail_nickname.setText(snapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference.child("save").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot homecontent : snapshot.getChildren()) {
                    if(date.equals(homecontent.getValue().toString())){
                        save.setImageResource(R.drawable.storage_filled_icon);
                        flag = true;
                        key = homecontent.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DetailActivity.this.finish();
        }
    };
    View.OnClickListener saveContent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!flag) {
                save.setImageResource(R.drawable.storage_filled_icon);
                mDatabase.child("save").child(uid).push().setValue(date);
                flag = true;

            } else {
                save.setImageResource(R.drawable.storage_icon);
                mDatabase.child("save").child(uid).child(key).removeValue();

                flag = false;
            }
        }
    };
}