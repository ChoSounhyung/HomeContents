package com.sacol.homecontents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ModelViewer extends RelativeLayout {
    private ImageView mypage_image;

    public ModelViewer(Context context) {
        super(context);

        init(context);
    }

    public ModelViewer(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_image_item, this, true);

        mypage_image = findViewById(R.id.mypage_image);
    }

    public void setItem(Model model) {
        mypage_image.setImageResource(model.getImage());
    }
}
