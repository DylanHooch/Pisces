package scut218.pisces.network;


import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Friend;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;
import scut218.pisces.proto.MsgProtocol;
import scut218.pisces.utils.impl.FileUtil;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClientProtoAnalyze {
    public static final int ERROR=-1;
    public static final int SUCCESS=0;
    public static final int FRIENDS=3;
    public static final int USERS=4;
    public static final int CONVERSATION=5;


    public int getType(MsgProtocol.msgProtocol message)
    {
        switch (message.getType())
        {
            case COMMENT: return 1;
            case MOMENT: return 2;
            case OTHERS: switch (message.getMsgD(0).getMsgStream().toStringUtf8())
            {
                case "friends" :return 3;
                case "users" :return 4;
                case "conversation" :return 5;
                case "error" :return -1;
                case "success": return 0;
            }


        }
        return -1;
    }

    public List<User> toUsers(MsgProtocol.msgProtocol message)
    {
        int size=message.getMsgDCount();//获取Protobuf中有几条singleMsg
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
        List<User> users=new ArrayList<>();
        for(int i=1;i<size;i+=10)
        {

            userId=message.getMsgD(i).getMsgStream().toStringUtf8();
            password=message.getMsgD(i+1).getMsgStream().toStringUtf8();
            nickname=message.getMsgD(i+2).getMsgStream().toStringUtf8();
            phone=message.getMsgD(i+3).getMsgStream().toStringUtf8();
            gender=message.getMsgD(i+4).getMsgStream().toStringUtf8();
            birthday= Date.valueOf(message.getMsgD(i+5).getMsgStream().toStringUtf8());
            photoPath=FileUtil.savePhoto(message.getMsgD(i+6).getMsgStream().toByteArray(),userId+".jpg");
            whatsup=message.getMsgD(i+7).getMsgStream().toStringUtf8();
            school=message.getMsgD(i+8).getMsgStream().toStringUtf8();
            grade=Integer.valueOf(message.getMsgD(i+9).getMsgStream().toStringUtf8());
            users.add(new User(userId,password,nickname,phone,gender,photoPath,whatsup,school,birthday,grade));

        }
        return users;
    }

    public List<Moment> toMoments(MsgProtocol.msgProtocol message)
    {
        int size=message.getMsgDCount();//获取Protobuf中有几条singleMsg
        int mId;
        int type;
        String authorId;//谁发的
        Timestamp time;
        String path;//若type为图片，则path代表存储图片的文件夹路径
        String location;
        String text;
        List<Moment> moments=new ArrayList<>();
        for(int i=0;i<size;i+=7)
        {

            mId=Integer.valueOf(message.getMsgD(i).getMsgStream().toStringUtf8());
            type=Integer.valueOf(message.getMsgD(i+1).getMsgStream().toStringUtf8());
            authorId=message.getMsgD(i+2).getMsgStream().toStringUtf8();
            time= Timestamp.valueOf(message.getMsgD(i+3).getMsgStream().toStringUtf8());
            if(type==1)
            path=FileUtil.savePhoto(message.getMsgD(i+4).getMsgStream().toByteArray(),mId+".jpg");
            else path=null;
            location= message.getMsgD(i+5).getMsgStream().toStringUtf8();
            text=message.getMsgD(i+6).getMsgStream().toStringUtf8();
            moments.add(new Moment(mId,authorId,type,time,path,location,text));

        }
        return moments;
    }
    public List<Comment> toComments(MsgProtocol.msgProtocol message)
    {
        int size=message.getMsgDCount();//获取Protobuf中有几条singleMsg
        String text;
        int cId;
        int mId;
        String userId;
        Timestamp time;
        int type;
        String replyUserId;
        List<Comment> comments=new ArrayList<>();
        for(int i=0;i<size;i+=7)
        {

            cId=Integer.valueOf(message.getMsgD(i).getMsgStream().toStringUtf8());
            mId=Integer.valueOf(message.getMsgD(i+1).getMsgStream().toStringUtf8());
            userId=message.getMsgD(i+2).getMsgStream().toString();
            time= Timestamp.valueOf(message.getMsgD(i+3).getMsgStream().toStringUtf8());
            replyUserId= message.getMsgD(i+4).getMsgStream().toStringUtf8();
            text=message.getMsgD(i+5).getMsgStream().toStringUtf8();
            type=Integer.valueOf(message.getMsgD(i+6).getMsgStream().toStringUtf8());
           comments.add(new Comment(cId,text,mId,replyUserId,userId,type,time));

        }
        return comments;
    }

    public List<Friend> toFriends(MsgProtocol.msgProtocol message)
    {
        int size=message.getMsgDCount();//获取Protobuf中有几条singleMsg
        String friendId;
        String friendName;
        String photoPath;
        String remark;
        String tag;
        String friendOfWhom;
        List<Friend> friends=new ArrayList<>();
        System.out.println("Size==="+size);
        for(int i=1;i<size;i+=6)
        {

            friendName=message.getMsgD(i).getMsgStream().toStringUtf8();
            System.out.println(friendName);
            friendId=message.getMsgD(i+1).getMsgStream().toStringUtf8();
            photoPath=FileUtil.savePhoto(message.getMsgD(i+2).getMsgStream().toByteArray(),friendId+".jpg");
            remark=message.getMsgD(i+3).getMsgStream().toStringUtf8();
            tag=message.getMsgD(i+4).getMsgStream().toStringUtf8();
            friendOfWhom=message.getMsgD(i+5).getMsgStream().toStringUtf8();
            System.out.println(friendId+friendOfWhom+remark+tag+friendName+photoPath);
            friends.add(new Friend(friendId,friendOfWhom,remark,tag,friendName,photoPath));

        }
        return friends;
    }
    public String toError(MsgProtocol.msgProtocol message)
    {
        return  message.getMsgD(1).getMsgStream().toStringUtf8();
    }
    public String toConversation(MsgProtocol.msgProtocol message)
    {   //返回fromwhom，text 用split获取相应的信息。
        String str=message.getFromWhom()+","+message.getMsgD(1).getMsgStream().toStringUtf8();
        return str;
    }

}
