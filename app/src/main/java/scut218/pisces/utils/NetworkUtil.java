package scut218.pisces.utils;
import scut218.pisces.proto.MsgProtocol;
/**
 * Created by Lenovo on 2018/3/14.
 */

public interface NetworkUtil {
    void init(String str);//建立长连接
    long send(MsgProtocol.msgProtocol msg);
    MsgProtocol.msgProtocol receive(long id);
}
