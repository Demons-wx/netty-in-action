package online.wangxuan;

import java.net.InetSocketAddress;

/**
 * @author wangxuan
 * @date 2018/5/17 下午11:08
 */

public class LogEvent {

    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    // 用于传出消息的构造函数
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    // 用于传入消息的构造函数
    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimestamp() {
        return received;
    }
}
