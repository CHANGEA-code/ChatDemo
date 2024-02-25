package com.chase.client;

import com.chase.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowBuilder {
    private ChatClient client;
    private String userName;
    private JFrame mainWin = new JFrame("聊天窗口");
    // 消息展示框
    private JTextArea displayTa = new JTextArea(14, 40);
    // 在线用户名称展示框
    private DefaultListModel<String> userListModel = new DefaultListModel<>();
    private JList<String> userList = new JList<>(userListModel);
    // 消息发送框
    private JTextArea inputTF = new JTextArea(4, 40);
    // 消息按钮
    private JButton sendBn = new JButton("发送");
    public WindowBuilder(ChatClient client){
        this.client = client;
    }
    public String inputUserName(){
        // 通过弹出对话框获取用户输入的用户名
        String userName = JOptionPane.showInputDialog(mainWin, "请输入您的用户名：");
        this.userName = userName;
        return userName;
    }
    public void buildChatWindow(){
        mainWin.setTitle("欢迎使用 " + userName + "聊天室应用");

        // 将消息框和按钮添加到窗口的底端
        JPanel bottomPanel = new JPanel();
        mainWin.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(inputTF);
        bottomPanel.add(sendBn);

        // 给发送消息按钮绑定点击事件监听器
        sendBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendSms(inputTF.getText());
            }
        });

        // 将展示消息区centerPanel添加到窗口的中间
        JPanel centerPanel = new JPanel();
        mainWin.add(centerPanel);

        // 让展示消息区可以滚动
        centerPanel.add(new JScrollPane(displayTa));
        displayTa.setEditable(false);

        // 用户列表和是否私聊放到窗口的最右边
        Box rightBox = new Box(BoxLayout.Y_AXIS);
        userList.setFixedCellWidth(60);
        userList.setVisibleRowCount(13);
        rightBox.add(new JLabel("用户列表："));
        rightBox.add(new JScrollPane(userList));
        centerPanel.add(rightBox);

        // 关闭窗口退出当前程序
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.pack(); // swing加上这句就可以拥有关闭窗口的功能
        mainWin.setVisible(true);
    }
    public DefaultListModel<String> getUserListModel() {
        return userListModel;
    }
    public JTextArea getDisplayTa() {
        return displayTa;
    }
    public JTextArea getInputTF() {
        return inputTF;
    }
}
