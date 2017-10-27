package com.jy.im.base.component.analyser.message;


/**
 * 消息解析器管理器
 */
public interface MessageAnalyserManager<In, Out> {

    /**
     * 选取消息解析器
     */
    MessageAnalyser<In, Out> selectMessageAnalyser(In in);
}
