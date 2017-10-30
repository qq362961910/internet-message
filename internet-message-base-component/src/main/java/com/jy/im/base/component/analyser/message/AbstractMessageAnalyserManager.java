package com.jy.im.base.component.analyser.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息解析器Manager抽象类
 */
public abstract class AbstractMessageAnalyserManager<In> implements MessageAnalyserManager<In> {

    private List<MessageAnalyser<In>> messageAnalyserList = new ArrayList<>();

    @Override
    public MessageAnalyser<In> selectMessageAnalyser(In msg) {
        if (messageAnalyserList.isEmpty()) {
            return null;
        }
        for (MessageAnalyser<In> analyser : messageAnalyserList) {
            if (analyser.support(msg)) {
                return analyser;
            }
        }
        return null;
    }

    public void addMessageAnalyser(MessageAnalyser<In> messageAnalyser) {
        if (messageAnalyser != null) {
            messageAnalyserList.add(messageAnalyser);
        }
    }

}
