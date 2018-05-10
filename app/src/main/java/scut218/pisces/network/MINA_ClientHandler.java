package scut218.pisces.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import scut218.pisces.proto.MsgProtocol;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by He_ee on 2018/3/16.
 */

public class MINA_ClientHandler extends IoHandlerAdapter {

    public static Map<Long, MsgProtocol.msgProtocol> ClientMap = new ConcurrentHashMap<>();
    // 从端口接受消息，会响应此方法来对消息进行处理

    /**
     * recv
     * 创建一个计时器，当发出请求（不包含IM信息），设定计时器，获取发出的请求的类型，如果一定时间内没有收到与请求类型对应的回复，则超时报错（默认网络错误），需重新执行操作（手动发送、刷新等）while(flag) 超时flag=0，返回报错。接收到消息停定时器返回protobuf。
     * IM信息存在一个List中，由客户端service定时查询List是否为空，若不为空，发出提示，写UI  3个
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        byte[] protoByte=(byte[]) message;
        MsgProtocol.msgProtocol msgProto=MsgProtocol.msgProtocol.parseFrom(protoByte);
        ClientMap.put(msgProto.getId(),msgProto);
        System.out.println(msgProto.getId());

    }
    // 向服务器发送消息后会调用此方法
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        System.out.println("客户端发送消息成功...");
    }

    // 关闭与服务器的连接时会调用此方法
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("客户端与服务器断开连接...");
    }

    // 客户端与服务器创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("客户端与服务器创建连接...");
    }

    // 客户端与服务器连接打开
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("客户端与服务器连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("客户端进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        System.out.println("客户端发送异常...");
    }
}
