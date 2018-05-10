package scut218.pisces.utils.impl;

import com.google.protobuf.ByteString;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scut218.pisces.network.MINA_ClientHandler;
import scut218.pisces.network.ProtoCodecFactory;
//import scut218.pisces.network.SSLContextGenerator;
import scut218.pisces.proto.MsgProtocol;
import scut218.pisces.utils.NetworkUtil;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;


public class ClientNetworkImpl implements NetworkUtil{
    private static IoSession session;
    private static IoConnector connector;
    //30S超时
    private static final int IDLETIMEOUT = 30;
    //15S心跳频率
    private static final int HEARTBEATRATE = 15;
    //心跳包内容
    private static final MsgProtocol.msgProtocol.Builder HEARTBEATREQUESTB = MsgProtocol.msgProtocol.newBuilder().setFromWhom("Client").setToWhom("Server").setType(MsgProtocol.msgProtocol.Type.FUNREQ).addMsgD(MsgProtocol.msgProtocol.msgDetail.newBuilder().setMsgStream(ByteString.copyFromUtf8("SFATETZGWETRGSGRTSERWR")));
    private static final byte[] HEARTBEATREQUEST = HEARTBEATREQUESTB.build().toByteArray();
    private static final MsgProtocol.msgProtocol.Builder HEARTBEATRESPONSEB = MsgProtocol.msgProtocol.newBuilder().setFromWhom("Server").setToWhom("Client").setType(MsgProtocol.msgProtocol.Type.FUNREQ).addMsgD(MsgProtocol.msgProtocol.msgDetail.newBuilder().setMsgStream(ByteString.copyFromUtf8("RWEOPRPZIPEMFSEORIWORE")));
    private static final byte[] HEARTBEATRESPONSE = HEARTBEATRESPONSEB.build().toByteArray();
    private static final Logger Client_LOG = LoggerFactory.getLogger(ClientNetworkImpl.class);
    private static final int PORT = 8088;
    private static String HOST;
    private static ClientNetworkImpl singleNetworkUtilImpl;
    private static String User;

    public synchronized  void  setUser(String usr)
    {
        User=usr;
    }//设置User用于配置session.attribute

    public static synchronized ClientNetworkImpl getsingleNetworkUtilImpl(){
        if(singleNetworkUtilImpl==null)
        {
            singleNetworkUtilImpl=new ClientNetworkImpl();
        }
        return singleNetworkUtilImpl;
    }

    public IoSession getsession()
    {
        return session;
    }

    public void init(String host) {
        try {
            //创建连接
            HOST=host;
            connector = new NioSocketConnector();
            //添加过滤器
//            SSLContext sslContext = new SSLContextGenerator().getSslContext();
//            SslFilter sslFilter = new SslFilter(sslContext);
//            sslFilter.setUseClientMode(true);
            //connector.getFilterChain().addFirst("sslFilter",sslFilter);
//            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
            connector.getFilterChain().addLast("proto", new ProtocolCodecFilter(new ProtoCodecFactory()));
            connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("threadPool",new ExecutorFilter(Executors.newCachedThreadPool()));

            // 读写通道10秒内无操作进入空闲状态
            connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLETIMEOUT);
            // 为接收器设置管理服务
            KeepAliveMessageFactory heartBeatFactory = new ClientHeartBeatFactory();
            KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE);//BOTH_IDLE表示在读写通道都是空闲的时候开始计时心跳

            //设置是否forward到下一个filter
            heartBeat.setForwardEvent(true);
            //设置心跳频率
            heartBeat.setRequestInterval(HEARTBEATRATE);
            //设置心跳超时
            heartBeat.setRequestTimeout(5);

            connector.getFilterChain().addLast("heartbeat", heartBeat);
            connector.setHandler(new MINA_ClientHandler());
            //创建连接
//            ConnectFuture future = connector.connect(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PORT));
            ConnectFuture future = connector.connect(new InetSocketAddress(HOST, PORT));
            //System.out.println("IP地址是： " + InetAddress.getLocalHost().getHostAddress());
            //等待链接创建完成
            future.awaitUninterruptibly();
            //获得session
            session = future.getSession();
            session.setAttribute("SessionTag", "13414478025");//Object setAttribute(Object key,Object value) key - the key of the attribute   value - the value of the attribute
           session.setAttribute("User",User);
            System.out.println(session.getAttribute("SessionTag"));

        } catch (Exception e) {
            System.out.println("与服务器建立连接异常...");
            e.printStackTrace();
        }
    }


    public long send(MsgProtocol.msgProtocol msg) {
        long id = msg.getId();
        byte[] msgStream = msg.toByteArray();
        session.write(msgStream);
        System.out.println("writed");
        return id;
    }

    public MsgProtocol.msgProtocol receive(long id) {

        int i = 10;
        while (i-- > 0) {
            try {
                sleep(1000);
                if (MINA_ClientHandler.ClientMap.get(id) != null) {
                    MsgProtocol.msgProtocol msgProtocol = MINA_ClientHandler.ClientMap.get(id);
                    MINA_ClientHandler.ClientMap.remove(id);
                    return msgProtocol;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;


    }

    private static class ClientHeartBeatFactory implements KeepAliveMessageFactory

    {
        @Override
        public boolean isResponse (IoSession session, Object message){
            try {
                MsgProtocol.msgProtocol msg = MsgProtocol.msgProtocol.parseFrom((byte[]) message);
                if (msg.getMsgD(0).getMsgStream().toStringUtf8().equals(MsgProtocol.msgProtocol.parseFrom(HEARTBEATRESPONSE).getMsgD(0).getMsgStream().toStringUtf8()))//如果是心跳包，返回true
                {
                    Client_LOG.info("收到服务端心跳回复包信息是: " + HEARTBEATRESPONSE);
                    System.out.println("wozaizhende");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("wozaizheli");
            return false;
        }

        @Override
        public Object getRequest (IoSession session){
            Client_LOG.info("客户端发出一个心跳请求包: " + HEARTBEATREQUEST);
            /**发出一个包刷新SessionMap* */
            session.write(MsgProtocol.msgProtocol.newBuilder().setFromWhom((String)session.getAttribute("User")).setToWhom("Server").setType(MsgProtocol.msgProtocol.Type.FUNREQ).addMsgD(MsgProtocol.msgProtocol.msgDetail.newBuilder().setType(MsgProtocol.msgProtocol.msgType.TEXT).setMsgName("little")).build().toByteArray());
            /** 返回预设语句 */
            return HEARTBEATREQUEST;
        }

        @Override
        public boolean isRequest (IoSession session, Object message){//因为不会接收到请求，所以直接返回false
//            LOG.info("获得的请求包信息是: " + message);
//            if (message.equals(HEARTBEATREQUEST))//如果是心跳包，返回true
//                return true;
            return false;
        }

        @Override
        public Object getResponse (IoSession session, Object request){
//            LOG.info("获取一个心跳回复包: " + HEARTBEATRESPONSE);
//            /** 返回预设语句 */
//            return HEARTBEATRESPONSE;
            return null;
        }
    }
}
