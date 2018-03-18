package scut218.pisces.beans;

import java.sql.Timestamp;

/**
 * Created by Lenovo on 2018/3/14.
 * 评论类，可能为直接评论或者回复
 */

public class Comment {
    private int id;
    private String text;
    private int mId;//评论位于哪一条动态下
    private String userId;//评论者
    private java.sql.Timestamp time;
    private int type;//有可能是直接评论或者回复
    private String replyUserId;//回复谁
    public final int DIRCOMMENT=1;
    public final int REPLY=2;
    public Comment(int id,String text,int mId,String replyUserId,String userId,int type,Timestamp time)
    {   this.id=id;
        this.text=text;
        this.mId=mId;
        this.replyUserId=replyUserId;
        this.userId=userId;
        this.type=type;
        this.time=time;
    }
    public Comment() {}
    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getmId() {
        return mId;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
