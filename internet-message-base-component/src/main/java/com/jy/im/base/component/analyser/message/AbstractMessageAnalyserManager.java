package com.jy.im.base.component.analyser.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 消息解析器Manager抽象类
 */
public abstract class AbstractMessageAnalyserManager<In> implements MessageAnalyserManager<In> {

    private final List<MessageAnalyser<In>> messageAnalyserList = new ArrayList<>();

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

    @Override
    public void addMessageAnalyser(MessageAnalyser<In>... messageAnalysers) {
        if (messageAnalysers != null && messageAnalysers.length > 0) {
            messageAnalyserList.addAll(Arrays.asList(messageAnalysers));
        }
    }
}
