package com.jy.im.server.config.netty;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyServerMessageAnalyser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NettyMessageAnalyserConfig {

    /**
     * netty common message analyser
     */
    @Bean
    public NettyServerMessageAnalyser nettyCommonMessageAnalyser() {
        return new NettyServerMessageAnalyser();
    }

    /**
     * netty message analyser manager
     */
    @Bean
    public NettyMessageAnalyserManager nettyMessageAnalyserManager(List<NettyMessageAnalyser> nettyMessageAnalyserList) {
        return new NettyMessageAnalyserManager(nettyMessageAnalyserList);
    }

}
