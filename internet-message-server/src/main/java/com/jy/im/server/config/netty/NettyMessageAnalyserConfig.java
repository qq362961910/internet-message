package com.jy.im.server.config.netty;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyServerMessageAnalyser;
import com.jy.im.base.component.translator.MessageTranslator;
import io.netty.buffer.ByteBuf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NettyMessageAnalyserConfig {

    /**
     * netty common message analyser
     */
    @Bean
    public NettyServerMessageAnalyser nettyCommonMessageAnalyser(List<MessageTranslator<ByteBuf>> messageTranslatorList) {
        NettyServerMessageAnalyser nettyServerMessageAnalyser = new NettyServerMessageAnalyser();
        nettyServerMessageAnalyser.addMessageTranslatorList(messageTranslatorList);
        return nettyServerMessageAnalyser;
    }

    /**
     * netty message analyser manager
     */
    @Bean
    public NettyMessageAnalyserManager nettyMessageAnalyserManager(List<NettyMessageAnalyser> nettyMessageAnalyserList) {
        NettyMessageAnalyserManager nettyMessageAnalyserManager = new NettyMessageAnalyserManager();
        for(NettyMessageAnalyser nettyMessageAnalyser: nettyMessageAnalyserList) {
            nettyMessageAnalyserManager.addMessageAnalyser(nettyMessageAnalyser);
        }
        return nettyMessageAnalyserManager;
    }

}
