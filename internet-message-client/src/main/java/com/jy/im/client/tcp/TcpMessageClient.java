package com.jy.im.client.tcp;

import com.jy.im.base.component.daemon.AbstractDaemonClient;
import com.jy.im.base.component.launcher.Launcher;
import com.jy.im.client.tcp.initializer.NettyTcpClientInitializer;
import com.jy.im.client.tcp.listener.NettyTcpClientDaemonListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class TcpMessageClient extends AbstractDaemonClient<NettyTcpClientDaemonListener> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(TcpMessageClient.class);
    private Channel clientChannel;

    @Override
    public void start(Launcher launcher) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new NettyTcpClientInitializer());
        try {
            if(launcher != null) {
                launcher.daemonStartSuccess(this);
            }
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            clientChannel = channelFuture.channel();
            clientChannel.closeFuture().sync();
        } catch (Exception e) {
            logger.error(String.format("TCP Client: %s Down,host: %s port: %d ", name, host, port), e);
        }finally {
            launcher.daemonShutdownSuccess(this);
            logger.info(String.format("[TCP Client]: %s closed, port: %d ", name, port));
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void shutdown(Launcher launcher) {
        if (clientChannel != null) {
            clientChannel.close();
        }
    }

    public TcpMessageClient(String host, int port) {
        super("netty-tcp-client", host, port);
    }
    public void writeMessage(Object message) {
        if (message instanceof String) {
            byte[] content = ((String)message).getBytes();
            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(content.length);
            buf.writeBytes(content);
            clientChannel.writeAndFlush(buf);
        }
        else {
            System.out.println("write error");
        }
    }
}
