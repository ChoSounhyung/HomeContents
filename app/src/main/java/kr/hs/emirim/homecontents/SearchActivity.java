package kr.hs.emirim.homecontents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;

public class SearchActivity extends AppCompatActivity {
    private ImageView search_btn;
    private ImageView back_btn;
    private ListView list;
    private DatabaseReference databaseReference;
    private List<Model> models;
    private List<Model> searchModel;
    private EditText search_edittext;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        setUp();
        initDatabase();
    }

    public void init() {
        models = new ArrayList<>();
        searchModel = new ArrayList<>();
        search_btn = findViewById(R.id.search_search);
        back_btn = findViewById(R.id.search_back);
        search_edittext = findViewById(R.id.search_edittext);
        list = findViewById(R.id.search_list);
        adapter = new SearchAdapter(this, searchModel);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        list.setAdapter(adapter);
        search_edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search_btn.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    public void initDatabase() {
        databaseReference.child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot homecontent : snapshot.getChildren()) {
                    models.add(new Model((String) homecontent.child("ImgLink").child("ImgLink0").getValue(), (String) homecontent.child("title").getValue(), (String) homecontent.child("content").getValue(), homecontent.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setUp() {
        search_btn.setOnClickListener(search);
        back_btn.setOnClickListener(goBackPage);
    }


    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            searchDate(search_edittext.getText().toString());
        }
    };

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    public void searchDate(String search) {
        searchModel.clear();
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getTitle().contains(search)) {
                searchModel.add(models.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

}
