package scut218.pisces.adapters.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import scut218.pisces.R;
import scut218.pisces.base.MyApplication;
import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Moment;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.utils.MomentUtil;
import scut218.pisces.utils.UserUtil;
import scut218.pisces.utils.impl.ImageUtil;
import scut218.pisces.utils.impl.MomentUtilImpl;
import scut218.pisces.widget.CommentListView;
import scut218.pisces.widget.EditTextPopupWindow;
import scut218.pisces.widget.SnsPopupWindow;


/**
 * Created by Leebobo on 2017/5/3.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public final static int TYPE_TEXT = 0;
    public final static int TYPE_IMAGE = 1;


    public View view;
    public int viewType;
    public ImageView head;
    public TextView name,time,location,imfor;

    public TextView deleteBtn;
    public ImageView snsBtn;

    public LinearLayout commentBody;
    //评论列表
    public CommentListView commentListView;

    //弹出窗口
    public SnsPopupWindow popupWindow;

    //评论窗口
    public EditTextPopupWindow editTextPopupWindow;

    public BaseViewHolder(View itemView, int type) {
        super(itemView);
        view=itemView;
        this.viewType=type;

        initImage(viewType,view);
        head=(ImageView)itemView.findViewById(R.id.headIv);
        name=(TextView)itemView.findViewById(R.id.nameTv);
        time=(TextView)itemView.findViewById(R.id.timeTv);
        location=(TextView)itemView.findViewById(R.id.locationTv);
        imfor=(TextView)itemView.findViewById(R.id.imforTv);
        deleteBtn=(TextView)itemView.findViewById(R.id.deleteBtn);

        snsBtn=(ImageView)itemView.findViewById(R.id.snsBtn);

        commentBody=(LinearLayout)itemView.findViewById(R.id.CommentBody);
        commentListView=(CommentListView)itemView.findViewById(R.id.commentList);

        popupWindow=new SnsPopupWindow(itemView.getContext());
        editTextPopupWindow=new EditTextPopupWindow(itemView.getContext());

    }

    public abstract void initImage(int viewType,View view);

    public void setData(Moment moment){

        viewType=Integer.valueOf(moment.getType());
        imfor.setText(moment.getText());
        name.setText(moment.getAuthorId());
        location.setText(moment.getLocation());
        time.setText(moment.getTime().toString());
        commentBody.setVisibility(View.VISIBLE);
        //加载头像
        ImageUtil.loadIcon(this.head,moment.getAuthorId());

        //删除按钮
        if(moment.getAuthorId()== UtilFactory.getUserUtil().getMyId()) {
            deleteBtn.setVisibility(View.VISIBLE);
        }

        List<Comment> comments;
        if((comments=MomentUtilImpl.commentMap.get(moment.getId()))!=null)
        {
            commentListView.setVisibility(View.VISIBLE);
            setCommentListView((ArrayList)comments);
        }

    }

    public void initPopupWindow(final int tid) {
        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showPopupWindow(view);
            }
        });
        popupWindow.setItemClickListener(new PopupItemClickListener(tid));

    }


    public void setCommentListView(final ArrayList<Comment> list){
        commentListView.notifyDataSetChanged(list);
        commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //评论
            }
        });
        commentListView.setOnSpanClickListener(new CommentListView.OnSpanClickListener() {
            @Override
            public void onSpanClick(int position) {
                //StartMemberActivity(list.get(position).getUser().getUid());
            }
        });
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener{

        private long lasttime = 0;
        private int tid;

        public PopupItemClickListener(final int tid){
            this.tid=tid;
        }

        @Override
        public void onItemClick(SnsPopupWindow.ActionItem actionitem, int position) {
            lasttime = System.currentTimeMillis();
            editTextPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.commentEt:
                            break;
                        case R.id.sendBtn:
                            String text=editTextPopupWindow.getComment();
                            if (text!=null)
                            {
                                MomentUtil momentUtil=UtilFactory.getMomentUtil();
                                Comment comment=new Comment();
                                comment.setTime(new Timestamp(new Date().getTime()));
                                comment.setType(1);//不是回复
                                comment.setReplyUserId("no");
                                comment.setmId(tid);
                                comment.setUserId(UtilFactory.getUserUtil().getMyId());
                                comment.setText(text);
                                momentUtil.addComment(comment);
                                setCommentListView((ArrayList<Comment>)MomentUtilImpl.commentMap.get(tid));
                            }
                            break;
                        default:break;
                    }
                }
            });
            editTextPopupWindow.showPopupWindow(view);

        }
    }

    private void StartMemberActivity(final int uid){
        Intent intent=new Intent();
        Activity curActivity=(Activity)view.getContext();
        //intent.setClass(curActivity, MemberActivity.class);
        intent.putExtra("uid",""+uid);
        curActivity.startActivity(intent);
    }
}
