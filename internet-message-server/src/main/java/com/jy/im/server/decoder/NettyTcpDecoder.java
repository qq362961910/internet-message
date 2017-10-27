package com.jy.im.server.decoder;

import com.jy.im.base.component.analyser.message.MessageAnalyser;
import com.jy.im.base.component.analyser.message.MessageAnalyserManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * netty根解码器
 */
@Scope("prototype")
@Component
public class NettyTcpDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyTcpDecoder.class);

    private MessageAnalyser currentMessageAnalyser;

    @Autowired
    private MessageAnalyserManager messageAnalyserManager;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.isReadable()) {

            ByteBuf copied = in.copy();
            byte[] content = new byte[copied.readableBytes()];
            copied.readBytes(content);
            String hex = DatatypeConverter.printHexBinary(content);
            logger.info("hex: \r\n" + hex);
            logger.info("string: \r\n" + new String(content));
            logger.info("bytes: \r\n" + Arrays.toString(content));

            if (currentMessageAnalyser == null) {
                markReaderIndex(in);
                MessageAnalyser messageAnalyser = messageAnalyserManager.selectMessageAnalyser(in);
                resetReaderIndex(in);
                //未找到消息解析器
                if (messageAnalyser == null) {
                    close(channelHandlerContext, "no MessageAnalyser found");
                }
                else {
                    currentMessageAnalyser = messageAnalyser;
                }
            }
            if (currentMessageAnalyser != null) {
                //mark reader index
                markReaderIndex(in);
                Object message = currentMessageAnalyser.analyse(in);
                if (message != null) {
                    logger.info(message.toString());
                    out.add(message);
                    //读取完一条消息后reset当前handler
                    reset();
                }
                else {
                    //reset reader index
                    resetReaderIndex(in);
                }
            }
        }
    }

    public MessageAnalyserManager getMessageAnalyserManager() {
        return messageAnalyserManager;
    }

    public NettyTcpDecoder setMessageAnalyserManager(MessageAnalyserManager messageAnalyserManager) {
        this.messageAnalyserManager = messageAnalyserManager;
        return this;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("app analyse message from: " + ctx.channel().localAddress() + "exception, connection is closing!", cause);
    }

    public void markReaderIndex(ByteBuf in) {
        in.markReaderIndex();
    }
    public void resetReaderIndex(ByteBuf in) {
        in.resetReaderIndex();
    }


    public void reset() {
        currentMessageAnalyser = null;
    }

    public void close(ChannelHandlerContext channelHandlerContext, String message) {
        logger.error(message);
        channelHandlerContext.close();
    }
}
