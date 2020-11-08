package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.sample1));
        models.add(new Model(R.drawable.sample2));

        detailAdapter = new DetailAdapter(models, this);

        init();
        setUp();
    }

    public void init() {
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.detail_back);
        save = findViewById(R.id.detail_storage);
    }

    public void setUp() {
        viewPager.setAdapter(detailAdapter);
        viewPager.setPadding(0, 0, 0, 0);
        back.setOnClickListener(goBackPage);
        save.setOnClickListener(saveContent);
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
                flag = true;
            } else {
                save.setImageResource(R.drawable.storage_icon);
                flag = false;
            }
        }
    };
}