package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.sacol.homecontents.DetailActivity;
import com.sacol.homecontents.Model;
import com.sacol.homecontents.MypageEditActivity;
import com.sacol.homecontents.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends PagerAdapter {
    private List<Model> models =new ArrayList<Model>();;
    private LayoutInflater layoutInflater;
    private Context context;

    public DetailAdapter(List models, Context context) {
        this.models = models;
        this.context = context;
    }

    public void addItem(Model model) {
        models.add(model);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.detail_image_item, container, false);
        ImageView image;
        image = view.findViewById(R.id.detail_image);

        Glide
                .with(view)
                .load(models.get(position).getImage())
                .into(image);


        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
