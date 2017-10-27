package com.jy.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.nio.charset.Charset;
import java.util.List;


public class SimpleChannelClient {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(SimpleChannelClient.class);

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
                    pipeline.addLast(new StringMessageDecoder());
                    pipeline.addLast(new StringMessageEncoder());
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
        ctx.writeAndFlush("hello");
    }
}

class StringMessageDecoder extends ByteToMessageDecoder {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(StringMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.isReadable()) {
            byte[] content = new byte[in.readableBytes()];
            in.readBytes(content);
            out.add(new String(content, Charset.defaultCharset()));
        }
    }
}

class StringMessageEncoder extends MessageToByteEncoder<String> {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(StringMessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        if (msg != null) {
            logger.info("write a message: {}", msg);
            out.writeBytes(msg.getBytes());
        }
    }
}
