package scut218.pisces.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Friend;
import scut218.pisces.dbc.SqliteDBConnector;

/**
 * Created by SeHun on 2018-03-23.
 */

public class SqliteFriendDAO  {
    SqliteDBConnector sdbc =new SqliteDBConnector();
    SQLiteDatabase db=sdbc.getDb();
    public void insert(Friend friend){
        ContentValues cValue = new ContentValues();
        cValue.put("userId",friend.getFriendOfWhom());
        cValue.put("friendId",friend.getFriendId());
        cValue.put("remark",friend.getRemark());
        cValue.put("tag",friend.getTag());
        db.insert("Friend",null,cValue);
    }

    public void update(Friend friend) {
        ContentValues cValue = new ContentValues();
        cValue.put("remark",friend.getRemark());
        cValue.put("tag",friend.getTag());
        db.update("Friend",cValue,"userId=? and friendId=?",new String[]{friend.getFriendOfWhom(),friend.getFriendId()});
    }

    public void delete(String userId,String friendId){
        db.delete("Friend","userId=? and friendId=?",new String[]{userId,friendId});
    }
    public List<Friend> queryAll(String userId){

        List<Friend> friends=new ArrayList<>();
        String friendId;
        String remark;
        String tag;
        Cursor cursor = db.query ("Friend",null,"userId=?",new String[]{userId},null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                   friendId=cursor.getString(2);
                   remark=cursor.getString(3);
                   tag=cursor.getString(4);
                friends.add(new Friend(userId,friendId,remark,tag));
            }
        }
        return friends;
    }
    public Friend queryByFriendId(String userId,String friendId){
        Friend friend=null;
        String remark;
        String tag;
        Cursor cursor = db.query ("Friend",null,"userId=? and friendId=?",new String[]{userId,friendId},null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                remark=cursor.getString(3);
                tag=cursor.getString(4);
                friend=new Friend(userId,friendId,remark,tag);
            }
        }
        return friend;

    }

}
