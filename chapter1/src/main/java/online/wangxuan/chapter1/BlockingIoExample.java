package online.wangxuan.chapter1;

import online.wangxuan.chapter4.util.MyThreadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author wangxuan
 * @date 2018/4/7
 */
public class BlockingIoExample {

    public void server(int portNumber) throws IOException {

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("开始监听 " + portNumber + " 端口...");

        ExecutorService executor = new MyThreadPool().getExecutor("block demo");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            executor.execute(() -> {
                handler(clientSocket);
            });
        }
    }

    private void handler(Socket clientSocket) {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request, response;

            while ((request = in.readLine()) != null) {
                if ("Done".equals(request)) {
                    break;
                }

                response = processRequest(request);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String processRequest(String request) {
        System.out.println(request);
        return "echo: " + request;
    }

    public static void main(String[] args) throws IOException {
        new BlockingIoExample().server(9999);
    }
}
