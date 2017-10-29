package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NettyMessageAnalyserManager extends AbstractMessageAnalyserManager {

    @Autowired
    private StringMessageAnalyser stringMessageAnalyser;

    @PostConstruct
    public void init() {
        addMessageAnalyser(stringMessageAnalyser);
    }

}
