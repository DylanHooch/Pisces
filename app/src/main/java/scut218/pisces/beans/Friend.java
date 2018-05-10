package scut218.pisces.beans;

import java.sql.Date;

/**
 * Created by SeHun on 2018-03-22.
 */

public class Friend  {
    private  String friendId;
    private String friendName;
    private String photoPath;
    private String remark;
    private String tag;
    private String friendOfWhom;
    public Friend(){}

    public Friend(String id,String friendOfWhom,String remark,String tag,String friendname,String photoPath)
    {   this.friendName=friendname;
        this.photoPath=photoPath;
        this.friendId=id;
        this.friendOfWhom=friendOfWhom;
        this.remark=remark;
        this.tag=tag;
    }
    public Friend(String id,String friendOfWhom,String remark,String tag)
    {
        this.friendId=id;
        this.friendOfWhom=friendOfWhom;
        this.remark=remark;
        this.tag=tag;
    }
    public void setFriendName(String friendName){this.friendName=friendName;}
    public void setFriendId(String id) {this.friendId=id;}
    public  void setFriendOfWhom(String friendOfWhom)
    {
        this.friendOfWhom=friendOfWhom;
    }
    public void setRemark(String remark)
    {
        this.remark=remark;
    }
    public void setTag(String tag)
    {
        this.tag=tag;
    }
    public String getFriendName(){return friendName;}
    public String getPhotoPath(){return photoPath;}
    public String getFriendId(){ return friendId;}
    public String getRemark()
    {
        return remark;
    }
    public String getTag()
    {
        return tag;
    }
    public String getFriendOfWhom()
    {
        return friendOfWhom;
    }
}
