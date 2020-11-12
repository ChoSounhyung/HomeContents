package com.sacol.homecontents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class ModelViewer extends RelativeLayout {
    private ImageView mypage_image;
    private String image;

    public ModelViewer(Context context ,String image) {
        super(context);

        this.image = image;
        init(context);
    }

    public ModelViewer(Context context, AttributeSet attrs,String image) {
        super(context, attrs);
        this.image = image;

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_image_item, this, true);

        mypage_image = findViewById(R.id.my_image);

        Glide
                .with(context)
                .load(image)
                .into(mypage_image);
    }

    public void setItem(Model model) {
//        mypage_image.setImageResource(model.getImage());
    }
}
