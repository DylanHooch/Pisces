package scut218.pisces.adapters;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import scut218.pisces.MySquareImageView;
import scut218.pisces.R;
import scut218.pisces.beans.Friend;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.view.ChatActivity;

/**
 * Created by Lenovo on 2018/3/23.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    AppCompatActivity activity;

    List<Friend> friends;

    public MessageAdapter(AppCompatActivity activity)
    {
        super();
        this.activity=activity;

    }

    public void setData(List<Friend> friends){
        this.friends=friends;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(), R.layout.message_recycler_item,null);
        return new messageHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //TODO 将list[position]的内容放上去,同时set监听器
        //TODO 看看需不需要提前将聊天记录加载到此处
        TextView title=((messageHolder)holder).title;
        TextView content=((messageHolder)holder).content;
        TextView date=((messageHolder)holder).date;
        Glide.with(activity).load(friends.get(position).getPhotoPath()).into(((messageHolder) holder).imageView);
        title.setText(friends.get(position).getFriendId());
        content.setText("hi");
        date.setText("11:30");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启聊天Activity
                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("myId", UtilFactory.getUserUtil().getMyId());
                intent.putExtra("friendId",friends.get(position).getFriendId());
                intent.putExtra("fpath",friends.get(position).getPhotoPath());
                //TODO startActivity,putExtra"friendId"
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //TODO 将list大小返回
        return friends==null? 0 : friends.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class messageHolder extends RecyclerView.ViewHolder{
        MySquareImageView imageView;
        TextView title;
        TextView content;
        TextView date;
        public messageHolder(View itemView)
        {
            super(itemView);
            imageView=(MySquareImageView)itemView.findViewById(R.id.icon_recycler_message);
            title=(TextView)itemView.findViewById(R.id.title_recycler_message);
            content=(TextView)itemView.findViewById(R.id.content_recycler_message);
            date=(TextView)itemView.findViewById(R.id.date_recycler_message);
        }
    }
}
