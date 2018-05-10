package scut218.pisces.network;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class CumulativeDecoder extends CumulativeProtocolDecoder {

    @Override
    public boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() > 0) {//IoBuffer.remaining() the remaining bytes in the buffer
            in.mark();//标记reset后要恢复到的地方
            int length = in.getInt(in.position());//IoBuffer.position() the current position in the buffer

            if (length > in.remaining())//消息不够，不读取，重制
            {
                in.reset();//重置，再读取一批数据后再判断是否足够解码
                return false;
            }
            else {
                in.getInt();//把头都出来，因为这部分不是流的内容
                byte[] bytes = new byte[length];
                in.get(bytes, 0, length);//从缓冲中将数据读入bytes数组

                out.write(bytes);
                if (in.remaining() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

