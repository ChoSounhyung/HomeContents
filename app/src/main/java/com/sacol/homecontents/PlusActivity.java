package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlusActivity extends AppCompatActivity {
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        backBtn = findViewById(R.id.plus_back);
        backBtn.setOnClickListener(goBack);
    }

    View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PlusActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };
}