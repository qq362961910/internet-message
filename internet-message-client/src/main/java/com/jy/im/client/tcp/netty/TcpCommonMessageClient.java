package com.jy.im.client.tcp.netty;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientCommonMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.client.tcp.netty.initializer.NettyTcpClientInitializer;
import com.jy.im.common.constants.MessageSource;
import com.jy.im.common.entity.CommonLoginRequestMessage;
import com.jy.im.common.entity.CommonUserStringMessage;
import com.jy.im.common.util.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TcpCommonMessageClient {

    /**
     * 远程主机
     * */
    private String host;
    /**
     * 远程主机端口号
     * */
    private int port;
    /**
     * 启动器
     * */
    private DefaultLauncher launcher;
    /**
     * TCP消息客户端
     * */
    private TcpMessageClient tcpMessageClient;
    /**
     * ticket
     * */
    private byte[] ticket;

    public boolean connect() {
        List<NettyMessageAnalyser> nettyMessageAnalyserList = new ArrayList<>();
        nettyMessageAnalyserList.add(new NettyClientCommonMessageAnalyser());
        //第一次初始化
        if (launcher == null) {
            launcher = new DefaultLauncher();
            tcpMessageClient = new TcpMessageClient(host, port, new NettyTcpClientInitializer(nettyMessageAnalyserList));
            launcher.addDaemon(tcpMessageClient);
        }
        launcher.startup();
        return launcher.isStartUpOk();
    }

    public boolean isActive() {
        return launcher.isStartUpOk();
    }



    public TcpCommonMessageClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void shutdown() {
        launcher.close();
    }
    public void sendCommonStringMessage(String message) {
        CommonUserStringMessage msg = new CommonUserStringMessage();
        msg.setContent(message.getBytes());
        msg.setToId(2);
        msg.setToIdType(MessageSource.USER_ID_TYPE.value);
        try {
            msg.setTicket(PasswordUtil.encryptPassword("123454435").getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        tcpMessageClient.writeMessage(msg);
    }

    public void sendCommonLoginMessage(long userId, String password) throws NoSuchAlgorithmException {
        CommonLoginRequestMessage loginMessage = new CommonLoginRequestMessage();
        loginMessage.setUserId(userId);
        loginMessage.setPassword(PasswordUtil.encryptPassword(password).getBytes());
        tcpMessageClient.writeMessage(loginMessage);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        TcpCommonMessageClient client = new TcpCommonMessageClient("localhost", 5000);
        client.connect();
        while (!client.isActive()) {
            System.out.println("waiting connection");
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("connect ok");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMessageTypeOpt();
            String command = scanner.nextLine();
            if ("99".equals(command)) {
                break;
            }
            //login
            else if("0".equals(command)) {
                client.sendCommonLoginMessage(1, "abc");
            }
            //string message
            else if("1".equals(command)) {
                System.out.println("please input the message: ");
                String message = scanner.nextLine();
                client.sendCommonStringMessage(message);
            }
        }
        client.shutdown();
    }

    private static void printMessageTypeOpt() {
        System.out.println("chose message type: ");
        for(Map.Entry<String, String> entry: options.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    private static final Map<String, String> options = new LinkedHashMap<String, String>(){{
        put("Login", "0");
        put("String message", "1");
        put("exit", "99");
    }};
}
