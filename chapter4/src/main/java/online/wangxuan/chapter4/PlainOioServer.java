package online.wangxuan.chapter4;

import online.wangxuan.chapter4.util.MyThreadPool;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;

/**
 * @author wangxuan
 * @date 2018/4/15 下午7:13
 */

public class PlainOioServer {

    public void serve(int port) throws IOException {

        final ServerSocket socket = new ServerSocket(port);
        try {
            ExecutorService exec = new MyThreadPool().getExecutor("oio demo");
            for (; ; ) {
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                exec.execute(() -> {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));
                        clientSocket.close();
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            // ignore on close
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
