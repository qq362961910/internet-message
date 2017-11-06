package com.jy.im.base.component.launcher;

/**
 * 启动器配置
 */
public class LauncherConfig {
    /**
     * 启动超时时间
     */
    private int timeout = 10000;
    /**
     * 是否自动重启
     */
    private boolean autoRestart = true;
    /**
     * 健康检查频率
     */
    private int healthyCheckInSecond = 10;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
    }

    public static LauncherConfig defaultConfig() {
        return new LauncherConfig();
    }

    public int getHealthyCheckInSecond() {
        return healthyCheckInSecond;
    }

    public void setHealthyCheckInSecond(int healthyCheckInSecond) {
        this.healthyCheckInSecond = healthyCheckInSecond;
    }
}
