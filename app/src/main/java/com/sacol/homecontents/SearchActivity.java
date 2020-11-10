package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.MyAdapter;

public class SearchActivity extends AppCompatActivity {
    private TextView cancel_btn;
    private ListView list;

    ArrayList<Model> modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        setUp();
    }

    public void init() {
        cancel_btn = findViewById(R.id.search_cancel);
        list = findViewById(R.id.search_list);
        final MyAdapter myAdapter = new MyAdapter(this, modelArrayList);
    }

    public void setUp() {
        cancel_btn.setOnClickListener(goBackPage);
        list.setOnItemClickListener(clickItem);
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchActivity.this.finish();
        }
    };

    AdapterView.OnItemClickListener clickItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            startActivity(intent);
        }
    };
}