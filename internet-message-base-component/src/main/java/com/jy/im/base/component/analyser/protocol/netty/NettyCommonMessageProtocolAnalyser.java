package com.jy.im.base.component.analyser.protocol.netty;

import com.jy.im.common.constants.MessageProtocol;
import io.netty.buffer.ByteBuf;

/**
 * netty通用消息协议解析器
 */
public class NettyCommonMessageProtocolAnalyser extends NettyMessageProtocolAnalyser {

    /**
     * 通用协议在netty中取首字节
     * */
    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        return MessageProtocol.getMessageProtocol(byteBuf.readByte());
    }

}
