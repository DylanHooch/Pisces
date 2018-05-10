package scut218.pisces.utils.impl;

import android.util.Log;

import java.util.List;

import scut218.pisces.Constants;
import scut218.pisces.beans.Friend;
import scut218.pisces.beans.Moment;
import scut218.pisces.beans.User;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.network.ClientProtoAnalyze;
import scut218.pisces.network.RequestPacker;
import scut218.pisces.proto.MsgProtocol;
import scut218.pisces.utils.NetworkUtil;
import scut218.pisces.utils.UserUtil;

/**
 * Created by Lenovo on 2018/5/9.
 */

public class UserUtilImpl implements UserUtil {
    public static String id;


    @Override
    public List<Friend> requestFriends() {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.rquesetFriendPack(id));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            if(msg==null)
            {
                Log.e("requestFriend","receive error");
                return null;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                /*请求失败*/
                Log.e("requestFriend","requestFriend failure");
                return null;
            }
            else return analyze.toFriends(msg);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("requestFriend","send error");
            return null;
        }
    }

    @Override
    public String getMyId() {
        return id;
    }

    @Override
    public List<User> requestProf(String id) {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.rquesetUserPack(id,this.id));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            if(msg==null)
            {
                Log.e("requestProf","receive error");
                return null;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                /*请求失败*/
                Log.e("requestProf","requestProf failure");
                return null;
            }
            else {
                List<User> users=analyze.toUsers(msg);
                User.userMap.put(users.get(0).getId(),users.get(0));
                return users;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("requestProf","send error");
            return null;
        }
    }



    public int register(User user)
    {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.RegisterPack(user));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("register","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }
    public int login(String id,String pw)
    {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.LoginPack(id,pw));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            Log.e("login","sent");
            if(msg==null)
            {
                Log.e("login","receive error");
                return Constants.FAILURE;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                /*密码错误*/
                Log.e("login","authentification failure");
                return Constants.WRONGPW;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("login","send error");
            return Constants.FAILURE;
        }
        this.id=id;
        return Constants.SUCCESS;
    }
    public int updateProf(User user)
    {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.UpdateprofilePack(user));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            if(msg==null)
            {
                Log.e("updateProf","receive error");
                return Constants.FAILURE;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                Log.e("updateProf","updateProf failure");
                return Constants.WRONGPW;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("updateProf","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public int addFriend(Friend friend){
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.AddFriendPack(friend));

        }catch (Exception e){
            e.printStackTrace();
            Log.e("addFriend","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;

    }

    public int deleteFriend(String friendId){
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.DeletePack(friendId,packer.DELETEFRIEND,id));

        }catch (Exception e){
            e.printStackTrace();
            Log.e("deleteFriend","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;

    }



}
