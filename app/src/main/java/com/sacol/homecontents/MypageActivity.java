package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private ImageView mypage_setting;
    private TextView mypage_name;
    private TextView mypage_email;
    private GridView mypage_grid;
    private MypageAdapter mypageAdapter;

    private FirebaseAuth mAuth;
    private  FirebaseFirestore db;
    private  FirebaseUser user;
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

        mypageAdapter = new MypageAdapter();
        mypageAdapter.addItem(new Model(R.drawable.sample1));
        mypageAdapter.addItem(new Model(R.drawable.sample2));
        mypageAdapter.addItem(new Model(R.drawable.sample3));
        mypageAdapter.addItem(new Model(R.drawable.sample4));
        mypageAdapter.addItem(new Model(R.drawable.sample3));

        mypage_grid.setAdapter(mypageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        mypage_name= findViewById(R.id.mypage_name);
        mypage_email= findViewById(R.id.mypage_email);
        mypage_setting = findViewById(R.id.mypage_setting);
        mypage_grid = findViewById(R.id.mypage_gridview);
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
        mypage_setting.setOnClickListener(goSettingPage);
    }

    View.OnClickListener goSettingPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startSettingActivity();
        }
    };

    private void startSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
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