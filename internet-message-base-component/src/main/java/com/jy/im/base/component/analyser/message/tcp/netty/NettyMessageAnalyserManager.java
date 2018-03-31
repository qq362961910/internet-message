package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyserManager;
import io.netty.buffer.ByteBuf;

import java.util.List;

public class NettyMessageAnalyserManager extends AbstractMessageAnalyserManager<ByteBuf> {

    public NettyMessageAnalyserManager(List<NettyMessageAnalyser> nettyMessageAnalyserList) {
        for(NettyMessageAnalyser nettyMessageAnalyser: nettyMessageAnalyserList) {
            addMessageAnalyser(nettyMessageAnalyser);
        }
    }
}
