package scut218.pisces.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scut218.pisces.Constants;
import scut218.pisces.base.MyApplication;
import scut218.pisces.utils.UserUtil;

/**
 * Created by Lenovo on 2018/3/14.
 * 用户类，有基本信息
 */

public class User {
    private String id;
    private String password;
    private String nickname;
    private String phone;
    private String gender;
    private java.sql.Date birthday;
    private String photoPath;
    private String whatsup;
    private String school;
    private int grade;

    public static User me;
    public static HashMap<String,User> userMap=new HashMap<>();//附近的人和好友都放在里面

    public static List<Friend> friendList=new ArrayList<>();//好友id列表
    public static List<String> nearbyIdList=new ArrayList<>();//附近的人id列表

    public User() {
        this.id="";
        this.password="";
        this.nickname="";
        this.phone="";
        this.gender="";
        this.photoPath= MyApplication.getContext().getFilesDir().getPath()+"/default.jpg";
        this.whatsup="";
        this.school="";
        this.birthday=new java.sql.Date(new java.util.Date().getTime());
        this.grade=0;
    }

    public User(String id,String password,String nickname,String phone,String gender,String photoPath, String whatsup,String school,Date birthday,int grade) {
        this.id=id;
        this.password=password;
        this.nickname=nickname;
        this.phone=phone;
        this.gender=gender;
        this.photoPath=photoPath;
        this.whatsup=whatsup;
        this.school=school;
        this.birthday=birthday;
        this.grade=grade;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getGrade() {
        return grade;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getSchool() {
        return school;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }
}
