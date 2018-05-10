package scut218.pisces.adapters.holder;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

import scut218.pisces.R;
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
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.viewstub_image);
        View subView=viewStub.inflate();
        ImageView view=(ImageView) subView.findViewById(R.id.imageView);
        if (view!=null){
            this.imageView =view;
        }
    }

    public void show(Moment moment){
        String url=moment.getPath();

    }
}
