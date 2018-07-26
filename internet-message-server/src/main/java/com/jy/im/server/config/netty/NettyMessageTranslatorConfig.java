package com.jy.im.server.config.netty;

import com.jy.im.base.component.translator.tcp.netty.common.impl.CommonLoginRequestMessageTranslator;
import com.jy.im.base.component.translator.tcp.netty.common.impl.CommonUserStringMessageTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NettyMessageTranslatorConfig {


    @Scope("prototype")
    @Bean
    public CommonLoginRequestMessageTranslator commonLoginRequestMessageTranslator() {
        return new CommonLoginRequestMessageTranslator();
    }

    @Scope("prototype")
    @Bean
    public CommonUserStringMessageTranslator commonUserStringMessageTranslator() {
        return new CommonUserStringMessageTranslator();
    }

}


