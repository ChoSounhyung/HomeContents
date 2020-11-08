package com.sacol.homecontents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MystorageActivity extends AppCompatActivity {
    private ImageView mystorage_back;
    private GridView mystorage_grid;
    private MystorageAdapter mystorageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystorage);

        mystorage_back = findViewById(R.id.mystorage_back);
        mystorage_grid = findViewById(R.id.mystorage_gridview);

        mystorage_back.setOnClickListener(goBackPage);

        mystorageAdapter = new MystorageAdapter();
        mystorageAdapter.addItem(new Model(R.drawable.sample1));
        mystorageAdapter.addItem(new Model(R.drawable.sample2));
        mystorageAdapter.addItem(new Model(R.drawable.sample3));
        mystorageAdapter.addItem(new Model(R.drawable.sample2));
        mystorageAdapter.addItem(new Model(R.drawable.sample4));

        mystorage_grid.setAdapter(mystorageAdapter);
    }

    View.OnClickListener goBackPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MystorageActivity.this.finish();
        }
    };

    class MystorageAdapter extends BaseAdapter {

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