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
import com.sacol.homecontents.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView cancel_btn;
    private ListView list;
    private DatabaseReference databaseReference;
    private List<Model> models;
    private List<Model> searchModel;
    private EditText search_edittext;
    private SearchAdater adapter;

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
        cancel_btn = findViewById(R.id.search_cancel);
        search_edittext = findViewById(R.id.search_edittext);
        list = findViewById(R.id.search_list);
        adapter = new SearchAdater(this, searchModel);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        list.setAdapter(adapter);
        search_edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cancel_btn.performClick();
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
        cancel_btn.setOnClickListener(goBackPage);
    }


    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            searchDate(search_edittext.getText().toString());

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

class SearchAdater extends BaseAdapter {
    private List<Model> models = new ArrayList<>();
    private Context context;

    public SearchAdater() {
    }

    public SearchAdater(Context context, List<Model> model) {
        this.context = context;
        this.models = model;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_item, viewGroup, false);

        }

        TextView search_title = view.findViewById(R.id.search_title);
        TextView search_content = view.findViewById(R.id.search_content);
        ImageView search_image = view.findViewById(R.id.search_image);
        search_content.setText(models.get(pos).getCont());
        search_title.setText(models.get(pos).getTitle());

        Glide
                .with(view)
                .load(models.get(pos).getImage())
                .into(search_image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("date", models.get(pos).getDate());
                context.startActivity(intent);
            }
        });

        return view;
    }

    public void addItem(String title, String cont) {
        Model m = new Model();
        m.setTitle(title);
        m.setCont(cont);

        models.add(m);
    }
}