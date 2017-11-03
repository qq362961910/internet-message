package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.analyser.protocol.NettyCommonMessageProtocolAnalyser;
import com.jy.im.base.component.translator.CommonMessageMessageTranslator;
import com.jy.im.base.component.translator.LoginCommonMessageTranslator;

public class NettyCommonMessageAnalyser extends NettyMessageAnalyser {

    public NettyCommonMessageAnalyser() {
        setNettyMessageProtocolAnalyser(new NettyCommonMessageProtocolAnalyser());
        addMessageTranslator(new LoginCommonMessageTranslator(), new CommonMessageMessageTranslator());
    }
}
