package scut218.pisces.network;

import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionMapManage {
    //    public static Map<Long,IoSession> sessionIdMap;
    public static Map<String, IoSession> KeyIdMap = new HashMap<>();

    public static void refreshSessionMap(IoSession curSession, String Key) {
//        sessionIdMap=curSession.getService().getManagedSessions();//获取当前为这个session服务的IoService，然后获取这个IoService下保存的所有由此IoErvice管理的session的MAP
          KeyIdMap.put(Key, curSession);
          System.out.println("Map Size  "+KeyIdMap.size());
    }

    public static IoSession getTarget(String Tag) {
        Iterator<Map.Entry<String, IoSession>> it = KeyIdMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, IoSession> entry = it.next();
            String sessionKey = entry.getKey();
            System.out.println(sessionKey+"  "+Tag);
            if (sessionKey.equals(Tag)) {
                System.out.println("Tag "+sessionKey);
                IoSession ioSession = entry.getValue();
                return ioSession;
            }
        }
        return null;
    }
}
