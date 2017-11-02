package com.jy.im.client;

import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.client.tcp.TcpMessageClient;

public class CommonMessageClient {

    private String host;
    private int port;
    private DefaultLauncher launcher;
    private TcpMessageClient tcpMessageClient;
    private byte[] ticket;

    public boolean connect() {
        if(launcher == null) {
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

    public CommonMessageClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        CommonMessageClient client = new CommonMessageClient("localhost", 5000);
        boolean ok = client.connect();
        if(ok) {
            System.out.println("connect ok");
        }
        else {
            System.out.println("connect error");
        }
        client.sendMessage("123456789");
    }
}
