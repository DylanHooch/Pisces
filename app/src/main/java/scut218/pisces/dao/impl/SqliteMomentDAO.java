package scut218.pisces.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Moment;
import scut218.pisces.dao.MomentDAO;
import scut218.pisces.dbc.SqliteDBConnector;

/**
 * Created by SeHun on 2018-03-18.
 */

public class SqliteMomentDAO implements MomentDAO {
    SqliteDBConnector sdbc =new SqliteDBConnector();
    SQLiteDatabase db=sdbc.getDb();

    @Override
    public List<Moment> queryAll() {
        return null;
    }

    public void insert(Moment moment){
        ContentValues cValue = new ContentValues();
        cValue.put("mId",moment.getId());
        cValue.put("time", moment.getTime().toString());
        cValue.put("authorId",moment.getAuthorId());
        cValue.put("type", moment.getType());
        cValue.put("path", moment.getPath());
        cValue.put("location",moment.getLocation());
        cValue.put("text", moment.getText());
        db.insert("Moment",null,cValue);
    }
    public void delete(int id){
        String whereClause = "mId=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("Moment",whereClause,whereArgs);
    }
    public Moment queryById(int id){
        Moment temp=new Moment();
        Cursor cursor = db.query ("Moment",null,"mId=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            temp.setId(id);
            temp.setAuthorId(cursor.getString(1));
            temp.setType(cursor.getInt(2));
            temp.setTime(Timestamp.valueOf(cursor.getString(3)));
            temp.setPath(cursor.getString(4));
            temp.setLocation(cursor.getString(5));
            temp.setText(cursor.getString(6));
        }
        return temp;
    }
    public List<Moment> queryAll(String id){
        List<Moment> temp=new ArrayList<>();
        int mId;
        int type;
        String authorId;//谁发的
        Timestamp time;
        String path;//若type为图片，则path代表存储图片的文件夹路径
        String location;
        String text;
        String SQL = "SELECT * FROM  `Pisces`.`Moment` where `authorId` IN (SELECT friendId from `Pisces`.`Friend' where userId=?)";
        Cursor cursor = db.rawQuery (SQL,new String[]{id});
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                mId=cursor.getInt(0);
                authorId=cursor.getString(1);
                type =cursor.getInt(2);
                time=Timestamp.valueOf(cursor.getString(3));
                path=cursor.getString(4);
                location=cursor.getString(5);
                text=cursor.getString(6);
                temp.add(new Moment(mId,authorId,type,time,path,location,text));
            }
        }
        return temp;
    }
    public List<Moment> queryByAuthorId(String id){
        List<Moment> temp=new ArrayList<>();
        int mId;
        int type;
        String authorId;//谁发的
        Timestamp time;
        String path;//若type为图片，则path代表存储图片的文件夹路径
        String location;
        String text;
        Cursor cursor = db.query ("Moment",null,"authorId=?",new String[]{id},null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                mId=cursor.getInt(0);
                authorId=cursor.getString(1);
                type =cursor.getInt(2);
                time=Timestamp.valueOf(cursor.getString(3));
                path=cursor.getString(4);
                location=cursor.getString(5);
                text=cursor.getString(6);
                temp.add(new Moment(mId,authorId,type,time,path,location,text));
            }
        }
        return temp;
    }
}
