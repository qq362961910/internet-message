package com.jy.im.base.component.analyser.message;


/**
 * 消息解析器管理器
 */
public interface MessageAnalyserManager<In> {

    /**
     * 选取消息解析器
     */
    MessageAnalyser selectMessageAnalyser(In in);

    /**
     * 添加消息解析器
     */
    void addMessageAnalyser(MessageAnalyser<In>... messageAnalysers);
}
