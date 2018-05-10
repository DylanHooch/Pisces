package scut218.pisces.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import scut218.pisces.beans.Comment;
import scut218.pisces.dao.CommentDAO;
import scut218.pisces.dbc.SqliteDBConnector;

/**
 * Created by SeHun on 2018-03-18.
 */

public class SqliteCommentDAO implements CommentDAO {
    SqliteDBConnector sdbc =new SqliteDBConnector();
    SQLiteDatabase db=sdbc.getDb();
    public void insert(Comment comment)
    {
        ContentValues cValue = new ContentValues();
        cValue.put("cId",comment.getId());
        cValue.put("mId",comment.getmId());
        cValue.put("userId",comment.getUserId());
        cValue.put("time",comment.getTime().toString());
        cValue.put("type",comment.getType());
        cValue.put("replyUserId",comment.getReplyUserId());
        cValue.put("text",comment.getText());
        db.insert("Comment",null,cValue);
    }
    public void delete(int id){

        String whereClause = "cId=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("Comment",whereClause,whereArgs);
    }
    public Comment queryById(int id){
        Comment temp=new Comment();
        Cursor cursor = db.query ("Comment",null,"cId=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
                temp.setId(id);
                temp.setmId(cursor.getInt(1));
                temp.setUserId(cursor.getString(2));
                temp.setTime(Timestamp.valueOf(cursor.getString(3)));
                temp.setType(cursor.getInt(4));
                temp.setReplyUserId(cursor.getString(5));
                temp.setText(cursor.getString(6));
        }
        return temp;
    }
    public List<Comment> queryAll(){
        List<Comment> temp=new ArrayList<>();
        String text;
        int cId;
        int mId;
        String userId;
        Timestamp time;
        int type;
        String replyUserId;
        Cursor cursor = db.query ("Comment",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                cId=cursor.getInt(0);
                mId=cursor.getInt(1);
                userId=cursor.getString(2);
                time=Timestamp.valueOf(cursor.getString(3));
                type=cursor.getInt(4);
                replyUserId=cursor.getString(5);
                text=cursor.getString(6);
                temp.add(new Comment(cId,text,mId,replyUserId,userId,type,time));
            }
        }
        return temp;
    }
    public List<Comment> queryByMomentId(int id){
        List<Comment> temp=new ArrayList<>();
        String text;
        int cId;
        int mId;
        String userId;
        Timestamp time;
        int type;
        String replyUserId;
        Cursor cursor = db.query ("Comment",null,"mId=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                cId=cursor.getInt(0);
                mId=cursor.getInt(1);
                userId=cursor.getString(2);
                time=Timestamp.valueOf(cursor.getString(3));
                type=cursor.getInt(4);
                replyUserId=cursor.getString(5);
                text=cursor.getString(6);
                temp.add(new Comment(cId,text,mId,replyUserId,userId,type,time));
            }
        }
        return temp;
    }

}
