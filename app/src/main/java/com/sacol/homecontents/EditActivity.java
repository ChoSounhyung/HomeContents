package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private TextView edit_back;
    private TextView edit_completion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();
        setUp();
    }

    private void init() {
        edit_back = findViewById(R.id.edit_back);
        edit_completion = findViewById(R.id.edit_completion);
    }

    private void setUp() {
        edit_back.setOnClickListener(goBackPage);
        edit_completion.setOnClickListener(complete);
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener complete = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Toast.makeText(EditActivity.this, "수정 되었습니다", Toast.LENGTH_SHORT).show();
        }
    };
}