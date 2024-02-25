package com.chase.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private WindowBuilder windowBuilder;

    public ChatClient(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.windowBuilder = new WindowBuilder(this);
    }
    public void sendSms(String sms) {
        try {
            //发送聊天消息到服务端
            DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            dataOutputStream.writeUTF(sms);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("发送消息报错：" + e.getMessage());
        }
    }
    public void execute(){
        String userName = windowBuilder.inputUserName();
        sendSms("①②③④" + userName + "①②③④");
        windowBuilder.buildChatWindow();
        new ClientThread(socket, windowBuilder).start();
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", 9999);
        client.execute();
    }
}
