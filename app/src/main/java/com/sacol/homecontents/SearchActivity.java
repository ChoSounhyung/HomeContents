package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    private TextView cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        setUp();
    }

    public void init() {
        cancel_btn = findViewById(R.id.search_cancel);
    }

    public void setUp() {
        cancel_btn.setOnClickListener(goBackPage);
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchActivity.this.finish();
        }
    };
}