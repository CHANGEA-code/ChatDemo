package com.chase.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    //定义Map集合用于存储用户的Socket以及用户的名字
    public final static Map<Socket,String> socketNameMap = Collections.synchronizedMap(new HashMap<Socket,String>());
    private ServerSocket serverSocket;

    public ChatServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    public void execute() throws IOException {
        System.out.println("------服务端暴露-------");
        while (true) {
            // 监听客户端套接字，若没有客户端连接，则代码不会往下执行，会堵塞在此处。
            Socket socket = serverSocket.accept();

            // 开启线程，用于读取客户端发送的信息，并转发给每一个客户端
            new ServerThread(socket).start();
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(9999);
        server.execute();
    }
}
