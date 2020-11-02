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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;

public class PlusActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;

    private ImageView cancel_btn;
    private TextView share_btn;
    private ImageView plus_gallery;
    private ImageView plus_image;
    private EditText plus_title;
    private EditText plus_contents;
    private Uri imguri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        init();
        setUp();
        checkSelfPermission();
    }

    public void init() {
        cancel_btn = findViewById(R.id.plus_cancel);
        share_btn = findViewById(R.id.plus_share);
        plus_gallery = findViewById(R.id.plus_gallery);
        plus_image = findViewById(R.id.plus_image);
        plus_title = findViewById(R.id.plus_title);
        plus_contents = findViewById(R.id.plus_contents);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }


    public void setUp() {
        cancel_btn.setOnClickListener(cancel);
        share_btn.setOnClickListener(share);
        plus_gallery.setOnClickListener(approach);
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
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

            //2. 업로드할 파일의 node를 참조하는 객체
            //파일 명이 중복되지 않도록 날짜를 이용
            String filename = FirebaseAuth.getInstance().getUid()+ "content.png";//현재 시간으로 파일명 지정 20191023142634
            //원래 확장자는 파일의 실제 확장자를 얻어와서 사용해야함. 그러려면 이미지의 절대 주소를 구해야함.

            StorageReference imgRef = firebaseStorage.getReference("contentsImg/" + filename);
            //uploads라는 폴더가 없으면 자동 생성

            //참조 객체를 통해 이미지 파일 업로드
            // imgRef.putFile(imgUri);
            //업로드 결과를 받고 싶다면..
            UploadTask uploadTask = imgRef.putFile(imguri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
            HashMap<Object, String> contents = new HashMap<>();
            contents.put("uid", FirebaseAuth.getInstance().getUid());
            contents.put("title", plus_title.getText().toString());
            contents.put("content", plus_contents.getText().toString());
            mDatabase.child("contents").push().setValue(contents);
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
            //갤러리에서 여러 이미지 선택
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
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
//        if (requestCode == REQUEST_CODE) {
//            if(requestCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
                    imguri = data.getData();
                    plus_image.setVisibility(View.VISIBLE);
                    plus_image.setImageURI(imguri);

//                    in.close();
//                    plus_image.setImageBitmap(img);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else if (requestCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}