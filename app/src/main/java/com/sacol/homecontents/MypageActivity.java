package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MypageActivity extends AppCompatActivity {
    private ImageView mypage_logout;
    private ImageView mypage_back;
    private ImageView mypage_edit;
    private TextView mypage_name;
    private TextView mypage_email;
    private GridView mypage_grid;
    private MypageAdapter mypageAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        init();
        setUp();
        userDate();

//        mypageAdapter = new MypageAdapter();
//        mypageAdapter.addItem(new Model(R.drawable.sample1));
//        mypageAdapter.addItem(new Model(R.drawable.sample2));
//        mypageAdapter.addItem(new Model(R.drawable.sample3));
//        mypageAdapter.addItem(new Model(R.drawable.sample4));
//        mypageAdapter.addItem(new Model(R.drawable.sample3));

        mypage_grid.setAdapter(mypageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        mypage_name= findViewById(R.id.mypage_name);
        mypage_email= findViewById(R.id.mypage_email);
        mypage_logout = findViewById(R.id.mypage_logout);
        mypage_grid = findViewById(R.id.mypage_gridview);
        mypage_back = findViewById(R.id.mypage_back);
        mypage_edit = findViewById(R.id.mypage_edit);
    }

    private  void userDate(){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mypage_name.setText(document.getData().get("name").toString());
                        mypage_email.setText(document.getData().get("email").toString());
                    } else {
                        mypage_name.setText("사용자");
                        mypage_email.setText(user.getEmail().toString());
                    }
                }
            }
        });
    }

    private void setUp() {
        mypage_logout.setOnClickListener(logout);
        mypage_back.setOnClickListener(goBackPage);
    }

    View.OnClickListener logout = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            String message = "로그아웃 하시겠습니까?";
            new AlertDialog.Builder(MypageActivity.this).setMessage(message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 로그아웃
                    FirebaseAuth.getInstance().signOut();
                    startLoginActivity();
                }
            }).setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "로그아웃 하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    };

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MypageActivity.this.finish();
        }
    };

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showToast(String str){
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();
    }

    class MypageAdapter extends BaseAdapter {
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