package adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sacol.homecontents.R;

import java.util.ArrayList;
import java.util.List;

import kr.hs.emirim.homecontents.DetailActivity;
import kr.hs.emirim.homecontents.Model;

public class SearchAdapter extends BaseAdapter {
    private List<Model> models = new ArrayList<>();
    private Context context;

    public SearchAdapter() {
    }

    public SearchAdapter(Context context, List<Model> model) {
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