package com.jy.im.client.tcp.netty;

import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.common.entity.CommonLoginRequestMessage;
import com.jy.im.common.util.PasswordUtil;

import java.security.NoSuchAlgorithmException;

public class TcpCommonMessageClient {

    private String host;
    private int port;
    private DefaultLauncher launcher;
    private TcpMessageClient tcpMessageClient;
    private byte[] ticket;

    public boolean connect() {
        if (launcher == null) {
            launcher = new DefaultLauncher();
            tcpMessageClient = new TcpMessageClient(host, port);
            launcher.addDaemon(tcpMessageClient);
        }
        launcher.startup();
        return launcher.isStartUpOk();
    }

    public void sendMessage(Object message) {
        tcpMessageClient.writeMessage(message);
    }

    public TcpCommonMessageClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void login(long userId, String password) throws NoSuchAlgorithmException {
        CommonLoginRequestMessage loginMessage = new CommonLoginRequestMessage();
        loginMessage.setUserId(userId);
        loginMessage.setPassword(PasswordUtil.encryptPassword(password).getBytes());
        sendMessage(loginMessage);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        TcpCommonMessageClient client = new TcpCommonMessageClient("localhost", 5000);
        boolean ok = client.connect();
        if (ok) {
            System.out.println("connect ok");
        } else {
            System.out.println("connect error");
        }
        client.login(1, "abc");
    }
}
