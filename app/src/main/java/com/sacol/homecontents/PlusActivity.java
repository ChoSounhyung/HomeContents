package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;

public class PlusActivity extends AppCompatActivity {
    private ImageView cancelBtn;
    private TextView shareBtn;
    private ImageView plusGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        init();
        setUp();
        checkSelfPermission();
    }

    public void init() {
        cancelBtn = findViewById(R.id.plus_cancel);
        shareBtn = findViewById(R.id.plus_share);
        plusGallery = findViewById(R.id.plus_gallery);
    }

    public void setUp() {
        cancelBtn.setOnClickListener(cancel);
        shareBtn.setOnClickListener(share);
        plusGallery.setOnClickListener(approach);
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

    View.OnClickListener approach = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            //기기 기본 갤러리 접근
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 101);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1) {
            int length = permissions.length;
            for(int i=0; i < length; i++) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //동의
                    Log.d("PlusActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }
    }

    public void checkSelfPermission() {
        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if(TextUtils.isEmpty(temp) == false) {
            //권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
        } else {
            //모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용하셨습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && requestCode == RESULT_OK) {
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                plusGallery.setImageBitmap(bm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 101 && requestCode == RESULT_CANCELED) {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
        }
    }
}