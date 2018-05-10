package scut218.pisces.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import scut218.pisces.beans.User;
import scut218.pisces.dao.UserDAO;
import scut218.pisces.dbc.SqliteDBConnector;

/**
 * Created by SeHun on 2018-03-18.
 */

public class SqliteUserDAO implements UserDAO {
    SqliteDBConnector sdbc =new SqliteDBConnector();
    SQLiteDatabase db=sdbc.getDb();
    public void insert(User user){
        ContentValues cValue = new ContentValues();
        cValue.put("userId",user.getId());
        cValue.put("password",user.getPassword());
        cValue.put("nickname",user.getNickname());
        cValue.put("phone", user.getPhone());
        cValue.put("gender", user.getGender());
        cValue.put("birthday",user.getBirthday().toString());
        cValue.put("school", user.getSchool());
        cValue.put("grade", user.getGrade());
        cValue.put("whatsup", user.getWhatsup());
        cValue.put("photoPath", user.getPhotoPath());
        db.insert("User",null,cValue);
    }
    public void delete(String id){
        String whereClause = "userId=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("User",whereClause,whereArgs);
    }
    public void update(User user){
        ContentValues cValue = new ContentValues();
        cValue.put("password",user.getPassword());
        cValue.put("nickname",user.getNickname());
        cValue.put("phone", user.getPhone());
        cValue.put("gender", user.getGender());
        cValue.put("birthday",user.getBirthday().toString());
        cValue.put("school", user.getSchool());
        cValue.put("grade", user.getGrade());
        cValue.put("whatsup", user.getWhatsup());
        cValue.put("photoPath", user.getPhotoPath());
        String whereClause ="userId=?";
        String[] whereArgs={user.getId()};
        db.update("User",cValue,whereClause,whereArgs);
    }
    public User queryById(String id){
        User temp=new User();
        Cursor cursor = db.query ("User",null,"userId=?",new String[]{id},null,null,null);
        if(cursor.moveToFirst()){
            temp.setId(id);
            temp.setNickname(cursor.getString(2));
            temp.setGender(cursor.getString(4));
            temp.setGrade(cursor.getInt(7));
            temp.setPassword(cursor.getString(1));
            temp.setPhone(cursor.getString(3));
            temp.setPhotoPath(cursor.getString(9));
            temp.setWhatsup(cursor.getString(8));
            temp.setSchool(cursor.getString(6));
            temp.setBirthday(Date.valueOf(cursor.getString(5)));
        }
        return temp;
    }
    public List<User> queryAll(){
        String userId;
        String password;
        String nickname;
        String phone;
        String gender;
        Date birthday;
        String photoPath;
        String whatsup;
        String school;
        int grade;
        List<User> temp = new ArrayList();
        Cursor cursor = db.query ("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++) {
                cursor.move(i);
                userId=cursor.getString(0);
                password=cursor.getString(1);
                nickname =cursor.getString(2);
                phone=cursor.getString(3);
                gender=cursor.getString(4);
                birthday=Date.valueOf(cursor.getString(5));
                school=cursor.getString(6);
                grade=cursor.getInt(7);
                whatsup=cursor.getString(8);
                photoPath=cursor.getString(9);
                temp.add(new User(userId,password,nickname,phone,gender,photoPath,whatsup,school,birthday,grade));
            }
        }
        return temp;
    }

}
