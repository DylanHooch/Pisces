package scut218.pisces.beans;

import java.sql.Date;

import scut218.pisces.Constants;
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
    private java.sql.Date brithday;
    private String photoPath;
    private String whatsup;
    private String school;
    private int grade;
    private UserUtil userUtil;



    public Date getBrithday() {
        return brithday;
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

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
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
