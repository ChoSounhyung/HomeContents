package com.sacol.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private FirebaseUser user;
    private String uid;
    private ImageView my_image;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        init();
        setUp();
        initDate();
        mypage_grid.setAdapter(mypageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        mypage_name = findViewById(R.id.mypage_name);
        mypage_email = findViewById(R.id.mypage_email);
        mypage_logout = findViewById(R.id.mypage_logout);
        mypage_grid = findViewById(R.id.mypage_gridview);
        mypage_back = findViewById(R.id.mypage_back);
        mypage_edit = findViewById(R.id.mypage_edit);
        my_image = findViewById(R.id.my_image);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mypageAdapter = new MypageAdapter(this);

    }

    private void initDate() {
        databaseReference.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mypage_name.setText(snapshot.child("name").getValue().toString());
                mypage_email.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot homecontent : snapshot.getChildren()) {

                    if (uid.equals(homecontent.child("uid").getValue().toString())) {
                        mypageAdapter.addItem(new Model(homecontent.child("ImgLink").child("ImgLink0").getValue().toString(), homecontent.getKey()));

                    }

                }
                mypageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("profileImg").getValue()!=null){
//                    Glide
//                            .with(getApplicationContext())
//                            .load(snapshot.child("profileImg").getValue())
//                            .into(my_image);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUp() {
        mypage_logout.setOnClickListener(logout);
        mypage_back.setOnClickListener(goBackPage);
        mypage_grid.setAdapter(mypageAdapter);
        mypage_edit.setOnClickListener(goEditPage);
    }

    View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 경고 다이얼로그 띄우기
            String msg = "로그아웃 하시겠습니까?";

            new AlertDialog.Builder(MypageActivity.this)
                    .setMessage(msg)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            startLoginActivity();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "로그아웃하지 않습니다", Toast.LENGTH_SHORT).show();
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

    View.OnClickListener goEditPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MypageActivity.this, MypageEditActivity.class);
            startActivity(intent);
        }
    };

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    class MypageAdapter extends BaseAdapter {
        private ArrayList<Model> models = new ArrayList<Model>();

        private Context context;

        public MypageAdapter(Context context) {
            this.context = context;
        }

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
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ModelViewer modelViewer = new ModelViewer(getApplicationContext());
            modelViewer.setItem(models.get(position));

            modelViewer.setOnClickListener(new ModelViewer.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("date", models.get(position).getDate());
                    context.startActivity(intent);
                }
            });

            return modelViewer;
        }

    }
}