package com.jy.im.base.component.analyser.message;

import io.netty.buffer.ByteBuf;

public class NettyMessageAnalyserManager extends AbstractMessageAnalyserManager<ByteBuf> {

    public NettyMessageAnalyserManager(NettyMessageAnalyser... nettyMessageAnalysers) {
        addMessageAnalyser(nettyMessageAnalysers);
    }

}
