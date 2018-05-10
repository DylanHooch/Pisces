package scut218.pisces.adapters.holder;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

import scut218.pisces.R;
import scut218.pisces.base.MyApplication;
import scut218.pisces.beans.Moment;

/**
 * Created by Leebobo on 2017/5/3.
 */

public class ImageViewHolder extends BaseViewHolder {
    public ImageView imageView;
    public ImageViewHolder(View itemView) {
        super(itemView, TYPE_IMAGE);
    }

    @Override
    public void initImage(int viewType, View view) {
        imageView=(ImageView)view.findViewById(R.id.item_image);
    }

    public void show(Moment moment){
        Log.e("moment",moment.getPath());
        String url=moment.getPath();
        Glide.with(MyApplication.getContext())
                .load(url)
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }
}
