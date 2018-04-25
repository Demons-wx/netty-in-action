package io.netty.channel;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author wangxuan
 * @date 2018/4/22 上午12:04
 */

public class DummyChannelPipeline extends DefaultChannelPipeline {

    public static final ChannelPipeline DUMMY_INSTANCE = new DummyChannelPipeline(null);

    public DummyChannelPipeline(Channel channel) {
        super(channel);
    }
}
