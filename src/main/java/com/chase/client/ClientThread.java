package com.chase.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private WindowBuilder windowBuilder;
    public ClientThread(Socket socket, WindowBuilder windowBuilder) {
        this.socket = socket;
        this.windowBuilder = windowBuilder;
    }
    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String message = dataInputStream.readUTF();

                if (message.startsWith("①②③④") && message.endsWith("①②③④")) {
                    //说明信息是用户名
                    String[] names = message.replace("①②③④","").split(",");
                    // 将用户列表先清空
                    windowBuilder.getUserListModel().clear();
                    for (int i = 0; i < names.length; ++i) {
                        windowBuilder.getUserListModel().addElement(names[i]);
                    }
                } else {
                    //说明是聊天信息，将聊天信息放在displayTa中
                    windowBuilder.getInputTF().setText("");
                    windowBuilder.getDisplayTa().append(message+"\t\n");
                }
            }
        } catch (IOException e) {
            System.out.println("客户端接收线程报错：" + e.getMessage());
        }
    }
}
