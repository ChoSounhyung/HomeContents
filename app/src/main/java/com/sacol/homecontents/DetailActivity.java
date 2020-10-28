package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import adapter.DetailAdapter;
import adapter.MainAdapter;

public class DetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DetailAdapter detailAdapter;
    private List<Model> models;

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
    }

    public void setUp() {
        viewPager.setAdapter(detailAdapter);
        viewPager.setPadding(0, 0, 0, 0);
    }
}