package scut218.pisces.network;

import com.google.protobuf.ByteString;
import scut218.pisces.proto.MsgProtocol;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BuildMSG {
    private static long  ID=0;
    private MsgProtocol.msgProtocol.Builder msgBuilder;


    public BuildMSG(){
        this.msgBuilder=MsgProtocol.msgProtocol.newBuilder();
        msgBuilder.setId(ID++);
    }

    public void setID(long id){
        msgBuilder.setId(id);
    }

    public void setType(String type)
    {
        switch(type) {
            case "MOMENT": {
                msgBuilder.setType(MsgProtocol.msgProtocol.Type.MOMENT);
                break;
            }
            case"COMMENT":{
                msgBuilder.setType(MsgProtocol.msgProtocol.Type.COMMENT);
                break;
            }
            case "FUNREQ":{
                msgBuilder.setType(MsgProtocol.msgProtocol.Type.FUNREQ);
                break;
            }
            case "OTHERS":{
                msgBuilder.setType(MsgProtocol.msgProtocol.Type.OTHERS);
            }
        }
    }

    public void setFromWhom(String from)
    {
       msgBuilder.setFromWhom(from);
    }

    public void setToWhom(String to)
    {
        msgBuilder.setToWhom(to);
    }

    public void setMsgDetail(String type,String tag, ByteString content){
         switch (type){
             case "TEXT":{
                 msgBuilder.addMsgD(MsgProtocol.msgProtocol.msgDetail.newBuilder().setType(MsgProtocol.msgProtocol.msgType.TEXT).setMsgName(tag).setMsgStream(content));
                 break;
             }
             case "IMAGE": {
                 msgBuilder.addMsgD(MsgProtocol.msgProtocol.msgDetail.newBuilder().setType(MsgProtocol.msgProtocol.msgType.IMAGE).setMsgName(tag).setMsgStream(content));
                 break;
             }

         }
    }

    public void setTime(){
//        Timestamp time=new Timestamp(System.currentTimeMillis());
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//可以方便地修改日期格式
        String time=dateFormat.format(now);
        msgBuilder.setTime(time);
    }

    public MsgProtocol.msgProtocol build(){
        MsgProtocol.msgProtocol realMSG=msgBuilder.build();
        return  realMSG;
    }


}
