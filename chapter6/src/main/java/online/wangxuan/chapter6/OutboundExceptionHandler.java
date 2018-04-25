package online.wangxuan.chapter6;

import io.netty.channel.*;

/**
 * Adding a ChannelFutureListener to a ChannelPromise
 * @author wangxuan
 * @date 2018/4/22 下午10:31
 */
public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        });
    }
}
