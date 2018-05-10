package scut218.pisces.beans;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Lenovo on 2018/3/14.
 */

public class Moment {
    private int id;
    private int type;
    private String authorId;//谁发的
    private Timestamp time;
    private String path;//若type为图片，则path代表存储图片的文件夹路径
    private String location;
    private String text;
    public final int IMAGE=1;
    public final int TEXT=2;
    public final int VIDEO=3;



    public Moment(){}
    public Moment(int id,String authorId,int type,Timestamp time,String path,String location,String text)
    {
        this.id=id;
        this.type=type;
        this.authorId=authorId;
        this.time=time;
        this.path=path;
        this.location=location;
        this.text=text;
    }
    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getLocation() {
        return location;
    }

    public String getPath() {
        return path;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

}
