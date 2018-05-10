package scut218.pisces.network;

import com.google.protobuf.ByteString;
import org.apache.mina.core.session.IoSession;
import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Friend;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;
import scut218.pisces.proto.MsgProtocol;
import scut218.pisces.utils.impl.FileUtil;


import java.io.IOException;

public class RequestPacker {
    private BuildMSG msgBuilder;
    private IoSession session;
    public final int DELETECOMMENT=1;
    public final int DELETEMOMENT=2;
    public final int DELETEFRIEND=3;
    public final int ALLMOMENT=5;
    public final int ALLMOMENTBYID=6;
    public final int MOMENT=4;
    public void msgInit(String  fromWhom,String toWhom){
        msgBuilder=new BuildMSG();
        msgBuilder.setTime();
        msgBuilder.setFromWhom(fromWhom);
        msgBuilder.setToWhom(toWhom);

    }


    public MsgProtocol.msgProtocol RegisterPack(User user) throws IOException {
        msgInit(user.getId(),"Server");
        msgBuilder.setMsgDetail("TEXT","Register", ByteString.copyFrom("Register".getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getId().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getPassword().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getNickname().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getPhone().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getGender().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getBirthday().toString().getBytes()));
        msgBuilder.setMsgDetail("IMAGE","", ByteString.copyFrom(FileUtil.getPhoto(user.getPhotoPath())));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getWhatsup().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getSchool().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(String.valueOf(user.getGrade()).getBytes()));
        return msgBuilder.build();
    }

    public MsgProtocol.msgProtocol LoginPack(String id,String psw){
        msgInit(id,"Server");
        msgBuilder.setMsgDetail("TEXT","Login", ByteString.copyFrom("Login".getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(id.getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(psw.getBytes()));
        return msgBuilder.build();
    }

    public MsgProtocol.msgProtocol UpdateprofilePack(User user) throws IOException {//user内容可以不完整
        msgInit(user.getId(),"Server");
        msgBuilder.setMsgDetail("TEXT","Updateprofile", ByteString.copyFrom("Updateprofile".getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getId().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getPassword().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getNickname().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getPhone().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getGender().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getBirthday().toString().getBytes()));
        msgBuilder.setMsgDetail("IMAGE","",ByteString.copyFrom(FileUtil.getPhoto(user.getPhotoPath())));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getWhatsup().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(user.getSchool().getBytes()));
        msgBuilder.setMsgDetail("TEXT","",ByteString.copyFrom(String.valueOf(user.getGrade()).getBytes()));
        return msgBuilder.build();
    }

    public MsgProtocol.msgProtocol AddcommentPack(Comment comment){
        msgInit(comment.getUserId(),"Server");
        msgBuilder.setMsgDetail("TEXT","Addcomment", ByteString.copyFrom("Addcomment".getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(String.valueOf(comment.getId()).getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(comment.getText().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(String.valueOf(comment.getmId()).getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(comment.getUserId().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(comment.getTime().toString().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(String.valueOf(comment.getType()).getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(comment.getReplyUserId().getBytes()));
        return msgBuilder.build();

    }
    public MsgProtocol.msgProtocol PostmomentPack(Moment moment) throws IOException {//moment的type有图片时为1，否则为0
        msgInit(moment.getAuthorId(),"Server");
        msgBuilder.setMsgDetail("TEXT","Postmoment", ByteString.copyFrom("Postmoment".getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(String.valueOf(moment.getId()).getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(String.valueOf(moment.getType()).getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(moment.getAuthorId().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(moment.getTime().toString().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(moment.getLocation().getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(moment.getText().getBytes()));
        if(moment.getType()==1) {
            msgBuilder.setMsgDetail("IMAGE","",ByteString.copyFrom(FileUtil.getPhoto(moment.getPath())));
        }
        return msgBuilder.build();
    }

    public MsgProtocol.msgProtocol AddFriendPack(Friend friend) throws IOException {
        msgInit(friend.getFriendOfWhom(),"Server");
        msgBuilder.setMsgDetail("TEXT","AddFriend", ByteString.copyFrom("AddFriend".getBytes()));
        msgBuilder.setMsgDetail("TEXT", "friendId", ByteString.copyFrom(friend.getFriendId().getBytes()));
        msgBuilder.setMsgDetail("TEXT", "remark", ByteString.copyFrom(friend.getRemark().getBytes()));
        msgBuilder.setMsgDetail("TEXT", "tag", ByteString.copyFrom(friend.getTag().getBytes()));
        msgBuilder.setMsgDetail("TEXT", "friendOfWhom", ByteString.copyFrom(friend.getFriendOfWhom().getBytes()));
        return msgBuilder.build();
    }

    public MsgProtocol.msgProtocol DeletePack(String id,int type,String userId){//1删评论 2删票圈 3删好友
        msgInit(userId,"Server");
        switch (type){
            case 1:{
                msgBuilder.setMsgDetail("TEXT","Deletecomment", ByteString.copyFrom("Deletecomment".getBytes()));
                msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(id.getBytes()));
                break;
            }
            case 2:{
                msgBuilder.setMsgDetail("TEXT","Deletemoment", ByteString.copyFrom("Deletemoment".getBytes()));
                msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(id.getBytes()));
                break;
            }
            case 3:{
                msgBuilder.setMsgDetail("TEXT","DeleteFriend", ByteString.copyFrom("DeleteFriend".getBytes()));
                msgBuilder.setMsgDetail("TEXT","friendOfWhom", ByteString.copyFrom(id.getBytes()));
                msgBuilder.setMsgDetail("TEXT","friendId", ByteString.copyFrom(String.valueOf(userId).getBytes()));
                break;
            }
        }
        return msgBuilder.build();

    }
    public MsgProtocol.msgProtocol rquesetMomentPack(int type,String queryId,String userId){
        msgInit(userId,"Server");
        msgBuilder.setType("MOMENT");
        switch (type)
        {   //查询单条Moment
            case(4):
                msgBuilder.setMsgDetail("TEXT","queryMomentById", ByteString.copyFrom("queryMomentById".getBytes()));
                msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(queryId.getBytes()));
                break;
            //queryAll
            case(5):
                msgBuilder.setMsgDetail("TEXT","queryMomentAllById", ByteString.copyFrom("queryMomentAllById".getBytes()));
                msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(queryId.getBytes()));
                break;
            //queryAllbyid 查询一个人的所有moment
            case(6):
                msgBuilder.setMsgDetail("TEXT","queryMomentAll", ByteString.copyFrom("queryMomentAll".getBytes()));
                msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(queryId.getBytes()));
                break;
        }
        return msgBuilder.build();

    }

    public MsgProtocol.msgProtocol rquesetUserPack(String queryId,String userId){
        msgInit(userId,"Server");
        msgBuilder.setType("OTHERS");
        msgBuilder.setMsgDetail("TEXT","queryuserById", ByteString.copyFrom("queryUserById".getBytes()));
        msgBuilder.setMsgDetail("TEXT","", ByteString.copyFrom(queryId.getBytes()));
        return msgBuilder.build();

    }

    public MsgProtocol.msgProtocol rquesetFriendPack(String userId){
        msgInit(userId,"Server");
        msgBuilder.setType("OTHERS");
        msgBuilder.setMsgDetail("TEXT", "queryAllFriend", ByteString.copyFrom("queryAllFriend".getBytes()));
        msgBuilder.setMsgDetail("TEXT", "", ByteString.copyFrom(userId.getBytes()));
        return msgBuilder.build();

    }

    public MsgProtocol.msgProtocol conversationPack(String userId,String friendId,String text){
        msgInit(userId,friendId);
        msgBuilder.setType("OTHERS");
        msgBuilder.setMsgDetail("TEXT", "conversation", ByteString.copyFrom("conversation".getBytes()));
        msgBuilder.setMsgDetail("TEXT", "text", ByteString.copyFrom(text.getBytes()));
        return msgBuilder.build();

    }


}
