package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kr.hs.emirim.homecontents.Model;
import com.sacol.homecontents.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context = null;
    LayoutInflater layoutInflater = null;
    ArrayList<Model> models = new ArrayList<Model>();

    public MyAdapter(Context context, ArrayList<Model> data) {
        this.context = context;
        this.models = data;
        layoutInflater = LayoutInflater.from(context);
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
        View v = layoutInflater.inflate(R.layout.search_item, null);

        ImageView image = v.findViewById(R.id.search_image);
        TextView title = v.findViewById(R.id.search_title);
        TextView content = v.findViewById(R.id.search_content);

        title.setText(models.get(position).getTitle());
        content.setText(models.get(position).getCont());

        return v;
    }
}
