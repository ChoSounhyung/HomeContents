package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailEditActivity extends AppCompatActivity {
    private ImageView cancel;
    private TextView completion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit);
        init();
        setUp();
    }

    private void init() {
        cancel = findViewById(R.id.detail_edit_cancel);
        completion = findViewById(R.id.detail_edit_completion);
    }

    private void setUp() {
        cancel.setOnClickListener(goBackPage);
        completion.setOnClickListener(completionEdit);
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Toast.makeText(getApplicationContext(), "수정되지 않았습니다", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener completionEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Toast.makeText(getApplicationContext(), "수정이 완료되었습니다", Toast.LENGTH_SHORT).show();
        }
    };
}