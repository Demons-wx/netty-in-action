package online.wangxuan.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author wangxuan
 * @date 2018/5/2 下午10:35
 */

public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
