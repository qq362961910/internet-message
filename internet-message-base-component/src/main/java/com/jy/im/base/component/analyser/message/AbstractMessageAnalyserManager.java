package com.jy.im.base.component.analyser.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息解析器Manager抽象类
 */
public class AbstractMessageAnalyserManager<In, Out extends Object> implements MessageAnalyserManager<In, Out> {

    private List<MessageAnalyser<In, Out>> messageAnalyserList = new ArrayList<>();

    @Override
    public MessageAnalyser<In, Out> selectMessageAnalyser(In msg) {
        if (messageAnalyserList == null || messageAnalyserList.isEmpty()) {
            return null;
        }
        for (MessageAnalyser<In, Out> analyser : messageAnalyserList) {
            if (analyser.support(msg)) {
                return analyser;
            }
        }
        return null;
    }

    public void addMessageAnalyser(MessageAnalyser<In, Out> messageAnalyser) {
        if (messageAnalyser != null) {
            messageAnalyserList.add(messageAnalyser);
        }
    }

}
