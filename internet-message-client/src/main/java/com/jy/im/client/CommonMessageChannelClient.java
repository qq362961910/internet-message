package com.jy.im.client;

import com.jy.im.common.constants.CommonMessageType;
import com.jy.im.common.entity.CommonMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;


public class CommonMessageChannelClient {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(CommonMessageChannelClient.class);

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new CommonMessageWriter());
                    pipeline.addLast(new EchoHandler());
                }
            });
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 5000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}

class EchoHandler extends SimpleChannelInboundHandler<String> {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(EchoHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("receive message: {}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildStringCommonMessage("this is a [Common] message"));
    }

    public CommonMessage buildStringCommonMessage(String content) {
        CommonMessage message = new CommonMessage();
        message.setMessageType(CommonMessageType.STRING.value);
        message.setLength((short)content.getBytes().length);
        message.setContent(content.getBytes());
        return message;
    }
}

