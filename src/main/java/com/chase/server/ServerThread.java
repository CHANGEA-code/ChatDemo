package com.chase.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;

public class ServerThread extends Thread {
    private Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            while(true) {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String data = dataInputStream.readUTF();

                if(data.startsWith("①②③④") && data.endsWith("①②③④")) { //发送过来的是用户名
                    //将Socket以及用户名字都存放在Map集合中
                    ChatServer.socketNameMap.put(socket, data.replace("①②③④",""));

                    broadcastMsg("①②③④" + buildUserNamesStr() + "①②③④");
                } else{ //发送过来的是聊天信息
                    //將聊天信息广播出去
                    broadcastMsg("[ "+ChatServer.socketNameMap.get(socket)+" ]说："+data);
                }
            }
        } catch (IOException e) {
            // 客户端退出时清除对应socket，并同步用户列表
            ChatServer.socketNameMap.remove(socket);
            broadcastMsg("①②③④" + buildUserNamesStr() + "①②③④");

            System.out.println(this.getName() + "程序退出:" + e.getMessage());
        }
    }

    private String buildUserNamesStr() {
        Collection<String> names = ChatServer.socketNameMap.values();
        StringBuilder namesStr = new StringBuilder();
        for(String userName : names) {
            namesStr.append(userName).append(",");
        }
        System.out.println("用户列表:" + namesStr.toString());
        return namesStr.toString();
    }

    private void broadcastMsg(String msg){
        try {
            Set<Socket> sockets = ChatServer.socketNameMap.keySet();
            for(Socket soc:sockets) {
                DataOutputStream dataOutputStream = new DataOutputStream(soc.getOutputStream());
                dataOutputStream.writeUTF(msg);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("广播消息失败：" + e.getMessage());
        }
    }
}
