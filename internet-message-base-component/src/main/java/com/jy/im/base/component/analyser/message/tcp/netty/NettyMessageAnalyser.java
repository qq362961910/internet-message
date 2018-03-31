package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyser;
import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.analyser.protocol.netty.NettyMessageProtocolAnalyser;
import io.netty.buffer.ByteBuf;

public abstract class NettyMessageAnalyser extends AbstractMessageAnalyser<ByteBuf> {

    private NettyMessageProtocolAnalyser nettyMessageProtocolAnalyser;

    @Override
    public ProtocolAnalyser<ByteBuf> getProtocolAnalyser() {
        return nettyMessageProtocolAnalyser;
    }

    public void setNettyMessageProtocolAnalyser(NettyMessageProtocolAnalyser nettyMessageProtocolAnalyser) {
        this.nettyMessageProtocolAnalyser = nettyMessageProtocolAnalyser;
    }
}
