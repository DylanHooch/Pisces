package scut218.pisces.network;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ProtoBufEncoder extends ProtocolEncoderAdapter{
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception{

        byte[] stream=(byte[]) message;//将object强制转换为字节流//
        int length=stream.length;//获取数据流长度（不包括length头的4bytes）
        IoBuffer buffer=IoBuffer.allocate(2048).setAutoExpand(true);//自动扩充的IoBuffer
        buffer.putInt(length);//在IoBuffer开头写入4bytes的数据表示图片流长度
        buffer.put(stream);//写入数据流
        buffer.flip();
        out.write(buffer);
        buffer.free();
    }
}
