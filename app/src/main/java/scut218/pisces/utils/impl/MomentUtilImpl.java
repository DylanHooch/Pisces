package scut218.pisces.utils.impl;

import android.util.Log;

import scut218.pisces.Constants;
import scut218.pisces.beans.Comment;
import scut218.pisces.beans.Moment;
import scut218.pisces.factory.UtilFactory;
import scut218.pisces.network.ClientProtoAnalyze;
import scut218.pisces.network.RequestPacker;
import scut218.pisces.proto.MsgProtocol;
import scut218.pisces.utils.MomentUtil;
import scut218.pisces.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MomentUtilImpl implements MomentUtil {
    private static int id=0;
    private static int commentId=0;

    public static HashMap<Integer,List<Comment>> commentMap=new HashMap<>();

    @Override
    public int post(Moment moment) {
        moment.setId(id++);
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.PostmomentPack(moment));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("postMoment","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    @Override
    public int delete(int momentId) {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.DeletePack(String.valueOf(momentId),packer.DELETEMOMENT, UtilFactory.getUserUtil().getMyId()));
        }catch (Exception e){
            e.printStackTrace();
            Log.e("delete","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    @Override
    public int addComment(Comment comment) {
        comment.setId(commentId++);
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.AddcommentPack(comment));
            List<Comment> comments=commentMap.get(comment.getmId());
            if(comments==null)
                commentMap.put(comment.getmId(),new ArrayList<Comment>());
            commentMap.get(comment.getmId()).add(comment);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("addComment","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    @Override
    public int deletecomment(int commentId) {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            networkUtil.send(packer.DeletePack(String.valueOf(commentId),packer.DELETECOMMENT, UtilFactory.getUserUtil().getMyId()));


        }catch (Exception e){
            e.printStackTrace();
            Log.e("delete","send error");
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    @Override
    public List<Moment> requestAllMoment() {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.rquesetMomentPack(packer.ALLMOMENT,UtilFactory.getUserUtil().getMyId(),UtilFactory.getUserUtil().getMyId()));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            if(msg==null)
            {
                Log.e("requestMoment","receive error");
                return null;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                /*请求失败*/
                Log.e("requestMoment","requestMoment failure");
                return null;
            }
            else return analyze.toMoments(msg);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("requestMoment","send error");
            return null;
        }
    }

    @Override
    public List<Moment> requestMomentByAuthor(String uid) {
        RequestPacker packer=new RequestPacker();
        NetworkUtil networkUtil= UtilFactory.getNetworkUtil();
        try{
            long rid=networkUtil.send(packer.rquesetMomentPack(packer.ALLMOMENTBYID,uid,UtilFactory.getUserUtil().getMyId()));
            MsgProtocol.msgProtocol msg=networkUtil.receive(rid);
            if(msg==null)
            {
                Log.e("requestMoment","receive error");
                return null;
            }
            ClientProtoAnalyze analyze=new ClientProtoAnalyze();
            int type=analyze.getType(msg);
            if(type==analyze.ERROR){
                /*请求失败*/
                Log.e("requestMoment","requestMoment failure");
                return null;
            }
            else return analyze.toMoments(msg);
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("requestMoment", "send error");
            return null;
        }

    }
}
