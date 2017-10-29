package com.jy.im.base.component.analyser.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息解析器Manager抽象类
 */
public class AbstractMessageAnalyserManager implements MessageAnalyserManager {

    private List<MessageAnalyser> messageAnalyserList = new ArrayList<>();

    @Override
    public MessageAnalyser selectMessageAnalyser(Object msg) {
        if (messageAnalyserList.isEmpty()) {
            return null;
        }
        for (MessageAnalyser analyser : messageAnalyserList) {
            if (analyser.support(msg)) {
                return analyser;
            }
        }
        return null;
    }

    public void addMessageAnalyser(MessageAnalyser messageAnalyser) {
        if (messageAnalyser != null) {
            messageAnalyserList.add(messageAnalyser);
        }
    }

}
