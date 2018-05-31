import com.sun.tools.javac.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by quxiang on 2018/5/29.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server socket start at port : " + port);
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                BufferedReader bf = null;
                PrintWriter wr = null;
                try {
                    bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    wr = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        String msg = bf.readLine();
                        if (msg != null && msg.length() > 0) {
                            System.out.println("收到客户端的信息: " + msg);
                            wr.println(new Date().toString());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bf != null) {
                        try {
                            bf.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(wr != null){
                        wr.close();
                    }
                    if(socket!= null){
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
