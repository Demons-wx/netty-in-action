package online.wangxuan;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author wangxuan
 * @date 2018/5/20 下午2:23
 */

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;

    /**
     * LogEventEncoder创建了即将被发送到InetSocketAddress的DatagramPacket消息
     * @param remoteAddress
     */
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {

        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = ctx.alloc().buffer(file.length + msg.length + 1);
        // 将文件名写入到ByteBuf中
        buf.writeBytes(file);
        // 添加一个Separator
        buf.writeByte(LogEvent.SEPARATOR);
        // 将日志消息写入到ByteBuf中
        buf.writeBytes(msg);
        out.add(new DatagramPacket(buf, remoteAddress));
    }
}
