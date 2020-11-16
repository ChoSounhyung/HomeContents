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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import adapter.DetailAdapter;

public class DetailEditActivity extends AppCompatActivity {
    private ImageView cancel;
    private TextView completion;
    private TextView detail_edit_contents;
    private TextView detail_edit_title;
    private DatabaseReference databaseReference;
    private  String date;
    private DatabaseReference mDatabase;
    private GridView detail_edit_grid;
    private DetailEditAdapter detailEditAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit);
        init();
        setUp();
    }

    private void init() {
        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        cancel = findViewById(R.id.detail_edit_cancel);
        completion = findViewById(R.id.detail_edit_completion);
        detail_edit_contents = findViewById(R.id.detail_edit_contents);
        detail_edit_title = findViewById(R.id.detail_edit_title);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        detail_edit_grid = findViewById(R.id.detail_edit_grid);
        detailEditAdapter = new DetailEditAdapter(this);
        detail_edit_grid.setAdapter(detailEditAdapter);
    }

    private void setUp() {
        cancel.setOnClickListener(goBackPage);
        completion.setOnClickListener(completionEdit);
        databaseReference.child("contents").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                detail_edit_contents.setText(snapshot.child("content").getValue().toString());
                detail_edit_title.setText(snapshot.child("title").getValue().toString());
                for (DataSnapshot homecontent : snapshot.child("ImgLink").getChildren()) {
                    detailEditAdapter.addItem(new Model(homecontent.getValue().toString()));
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
            finish();
            Toast.makeText(getApplicationContext(), "수정되지 않았습니다", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener completionEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDatabase.child("contents").child(date).child("content").setValue(detail_edit_contents.getText().toString());
            mDatabase.child("contents").child(date).child("title").setValue(detail_edit_title.getText().toString());
            finish();
            Toast.makeText(getApplicationContext(), "수정이 완료되었습니다", Toast.LENGTH_SHORT).show();
        }
    };

    class DetailEditAdapter extends BaseAdapter {
        private ArrayList<Model> models = new ArrayList<Model>();

        public DetailEditAdapter(Context context) { }

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
            ModelViewer modelViewer = new ModelViewer(getApplicationContext(),models.get(position).getImage());
            modelViewer.setItem(models.get(position));

            return modelViewer;
        }

    }
}
