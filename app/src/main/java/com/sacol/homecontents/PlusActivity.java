package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlusActivity extends AppCompatActivity {
    private ImageView cancelBtn;
    private TextView shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        cancelBtn = findViewById(R.id.plus_cancel);
        shareBtn = findViewById(R.id.plus_share);

        cancelBtn.setOnClickListener(cancel);
        shareBtn.setOnClickListener(share);

    }

    View.OnClickListener cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Toast.makeText(PlusActivity.this, "컨텐츠 작성을 취소합니다", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener share = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PlusActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(PlusActivity.this, "컨텐츠가 공유되었습니다", Toast.LENGTH_SHORT).show();
        }
    };
}