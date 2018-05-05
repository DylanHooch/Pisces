package scut218.pisces.adapters;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import scut218.pisces.MySquareImageView;
import scut218.pisces.R;

/**
 * Created by Lenovo on 2018/3/23.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    AppCompatActivity activity;
    int count=0;
    ActionMode mode;
    public MessageAdapter(AppCompatActivity activity)
    {
        super();
        this.activity=activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(), R.layout.message_recycler_item,null);
        return new messageHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //TODO 将list[position]的内容放上去,同时set监听器
        //TODO 看看需不需要提前将聊天记录加载到此处
        MySquareImageView imageView=((messageHolder)holder).imageView;
        TextView title=((messageHolder)holder).title;
        TextView content=((messageHolder)holder).content;
        TextView date=((messageHolder)holder).date;
        imageView.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(),R.drawable.test));
        title.setText("Test");
        content.setText("test");
        date.setText("testdate");
    }

    @Override
    public int getItemCount() {
        //TODO 将list大小返回
        return 1;
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
