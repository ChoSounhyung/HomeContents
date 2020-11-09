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
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PlusActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;

    private ImageView cancel_btn;
    private TextView share_btn;
    private ImageView plus_gallery;
    private GridView plus_grid;
    private EditText plus_title;
    private EditText plus_contents;
    private PlusAdapter plusAdapter;

    private Uri imguri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference mDatabase;
    private int i;
    private ArrayList ImageList;
    private ArrayList urlStrings;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        init();
        setUp();
        checkSelfPermission();

        plus_grid.setAdapter(plusAdapter);

    }

    public void init() {
        cancel_btn = findViewById(R.id.plus_cancel);
        share_btn = findViewById(R.id.plus_share);
        plus_gallery = findViewById(R.id.plus_gallery);
        plus_grid = findViewById(R.id.plus_grid);
        plus_title = findViewById(R.id.plus_title);
        plus_contents = findViewById(R.id.plus_contents);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ImageList = new ArrayList();
        urlStrings = new ArrayList();
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

            for (i = 0; i < ImageList.size(); i++) {
                imguri = (Uri) ImageList.get(i);
                filename = System.currentTimeMillis() + FirebaseAuth.getInstance().getUid() + i + "content.png";
                final StorageReference imgRef = firebaseStorage.getReference("contentsImg/" + filename);
                UploadTask uploadTask = imgRef.putFile(imguri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(
                                new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        urlStrings.add(String.valueOf(uri));
                                        if (urlStrings.size() == ImageList.size()) {
                                            HashMap<String, String> imghashMap = new HashMap<>();

                                            for (int i = 0; i < urlStrings.size(); i++) {
                                                imghashMap.put("ImgLink" + i, (String) urlStrings.get(i));

                                            }
                                            HashMap<Object, Object> contents = new HashMap<>();
                                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");

                                            Date time = new Date();

                                            String now = format1.format(time);
                                            contents.put("uid", FirebaseAuth.getInstance().getUid());
                                            contents.put("current_date", now);
                                            contents.put("title", plus_title.getText().toString());
                                            contents.put("content", plus_contents.getText().toString());
                                            contents.put("ImgLink", imghashMap);
                                            mDatabase.child("contents").push().setValue(contents);
                                        }

                                    }
                                }
                        );
                    }
                });
            }

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
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
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

        if (TextUtils.isEmpty(temp) == false) {
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
        if (data.getClipData() != null) {

            int countClipData = data.getClipData().getItemCount();
            int currentImageSlect = 0;

            while (currentImageSlect < countClipData) {

                imguri = data.getClipData().getItemAt(currentImageSlect).getUri();
                ImageList.add(imguri);
                currentImageSlect = currentImageSlect + 1;
            }

        } else {
            Toast.makeText(this, "Please Select Multiple Images", Toast.LENGTH_SHORT).show();
        }
    }

    class PlusAdapter extends BaseAdapter {
        private ArrayList<Model> models = new ArrayList<Model>();

        @Override
        public int getCount() {
            return models.size();
        }

        public void addItem(Model model) {
            models.add(model);
        }

        @Override
        public Object getItem(int position) {
            return models.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ModelViewer modelViewer = new ModelViewer(getApplicationContext());
            modelViewer.setItem(models.get(position));

            return modelViewer;
        }

    }
}