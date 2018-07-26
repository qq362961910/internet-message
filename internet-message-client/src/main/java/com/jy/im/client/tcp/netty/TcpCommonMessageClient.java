package com.jy.im.client.tcp.netty;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientCommonMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.client.tcp.netty.initializer.NettyTcpClientInitializer;
import com.jy.im.common.constants.MessageSource;
import com.jy.im.common.entity.LoginRequestMessage;
import com.jy.im.common.entity.UserStringMessage;
import com.jy.im.common.util.MessageIdUtil;
import com.jy.im.common.util.PasswordUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TcpCommonMessageClient {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(TcpCommonMessageClient.class);

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

    private Long userId;

    private String password;
    /**
     * ticket
     * */
    public static Map<Long, byte[]> TICKET_MAP = new HashMap<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
    public void sendCommonStringMessage(long userId, String message) {
        UserStringMessage msg = new UserStringMessage();
        msg.setContent(message.getBytes());
        msg.setToId(userId);
        msg.setToIdType(MessageSource.USER_ID_TYPE.value);
        msg.setTicket(TICKET_MAP.get(this.userId));
        tcpMessageClient.writeMessage(msg);
    }

    public void sendCommonLoginMessage() throws NoSuchAlgorithmException {
        if(userId == null || password == null) {
            logger.error("[username] is null or [password] is null");
            return;
        }
        LoginRequestMessage loginMessage = new LoginRequestMessage();
        loginMessage.setMessageId(MessageIdUtil.generateLoginMessageId());
        loginMessage.setUserId(userId);
        loginMessage.setPassword(PasswordUtil.encryptPassword(password).getBytes());
        tcpMessageClient.writeMessage(loginMessage);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        TcpCommonMessageClient client = new TcpCommonMessageClient("localhost", 5000);
        boolean connectResult = client.connect();
        logger.info("connect result: {}", connectResult);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMessageTypeOpt();
            String command = scanner.nextLine();
            if ("99".equals(command)) {
                break;
            }
            //login
            else if("0".equals(command)) {
                System.out.println("please input the user id: ");
                long useId = Long.valueOf(scanner.nextLine().trim());
                System.out.println("please input the password: ");
                String password = scanner.nextLine().trim();
                client.setUserId(useId);
                client.setPassword(password);
                client.sendCommonLoginMessage();
            }
            //string message
            else if("1".equals(command)) {
                System.out.println("please input the user id: ");
                long useId = Long.valueOf(scanner.nextLine().trim());
                System.out.println("please input the message: ");
                String message = scanner.nextLine();
                client.sendCommonStringMessage(useId, message);
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
