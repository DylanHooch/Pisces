package scut218.pisces.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import scut218.pisces.MySquareImageView;
import scut218.pisces.R;
import scut218.pisces.adapters.holder.BaseViewHolder;
import scut218.pisces.adapters.holder.ImageViewHolder;
import scut218.pisces.adapters.holder.TextViewHolder;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;

/**
 * Created by Lenovo on 2018/5/8.
 */

public class MomentAdapter extends RecyclerView.Adapter {
    private List<Moment> moments;
    private Context context;
    private final int TEXT_ONLY=1;
    private final int IMAGE=2;
    private final int FOOTER=3;

    public MomentAdapter()
    {
        moments=new ArrayList<>();
    }
    public MomentAdapter(List<Moment> moments,Context context)
    {
        this.moments=moments;
        this.context=context;
    }

    public void setData(List<Moment> moments){
        this.moments=moments;
        notifyDataSetChanged();
    }

    public void add(Moment moment){
        moments.add(moment);
        notifyDataSetChanged();
    }

    public void addAll(List<Moment> moments)
    {
        this.moments.addAll(moments);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(moments.get(position).getType()==moments.get(position).IMAGE)
            return IMAGE;
        else if(moments.get(position).getType()==moments.get(position).TEXT)
            return TEXT_ONLY;
        return FOOTER;
    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final BaseViewHolder holder = (BaseViewHolder) viewHolder;
        holder.setData(moments.get(position));
        //popupwindow
        holder.initPopupWindow(moments.get(position).getId());

        if(holder instanceof ImageViewHolder)
            ((ImageViewHolder)holder).show(moments.get(position));

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_topic_item,parent, false);
        if (viewType==TEXT_ONLY) {
            //文本消息
            holder= new TextViewHolder(view);
        }else if (viewType== IMAGE){
            //图片消息
            holder=new ImageViewHolder(view);
        }
        return holder;
    }

    
}
