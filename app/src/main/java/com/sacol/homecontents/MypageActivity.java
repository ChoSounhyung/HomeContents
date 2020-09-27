package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MypageActivity extends AppCompatActivity {
    private ImageView mypage_setting;
    private TextView mypage_name;
    private TextView mypage_email;
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
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {
        mypage_name= findViewById(R.id.mypage_name);
        mypage_email= findViewById(R.id.mypage_email);
        mypage_setting = findViewById(R.id.mypage_setting);


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
}