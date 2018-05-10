package scut218.pisces.network;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;



public class ProtoCodecFactory implements ProtocolCodecFactory{
//    private static final Charset charset = Charset.forName("UTF-8");

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return new CumulativeDecoder();
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return new ProtoBufEncoder();
    }
}
