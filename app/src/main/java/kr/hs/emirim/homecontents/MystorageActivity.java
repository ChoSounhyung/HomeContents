package kr.hs.emirim.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MystorageActivity extends AppCompatActivity {
    private ImageView mystorage_back;
    private GridView mystorage_grid;
    private MystorageAdapter mystorageAdapter;
    private DatabaseReference databaseRefernece;
    private List<Model> models;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystorage);

        init();
        setUp();
        initDatabase();
    }

    private void init() {
        mystorage_back = findViewById(R.id.mystorage_back);
        mystorage_grid = findViewById(R.id.mystorage_gridview);
        models = new ArrayList<>();
        mystorageAdapter = new MystorageAdapter(models,this);
        databaseRefernece = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();
    }

    private void setUp() {
        mystorage_back.setOnClickListener(goBackPage);
        mystorage_grid.setAdapter(mystorageAdapter);
    }

    private void initDatabase() {

        databaseRefernece.child("save").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot homecontent : snapshot.getChildren()) {

                    models.clear();

                    databaseRefernece.child("contents").child(homecontent.getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            models.add(new Model(snapshot.child("ImgLink").child("ImgLink0").getValue().toString(), snapshot.getKey()));
                            mystorageAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MystorageActivity.this.finish();
        }
    };

    class MystorageAdapter extends BaseAdapter {

        private List<Model> models = new ArrayList<Model>();
        private Context context;

        public MystorageAdapter(List<Model> model,Context context) {
            this.models = model;
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
            ModelViewer modelViewer = new ModelViewer(getApplicationContext(),models.get(position).getImage());
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