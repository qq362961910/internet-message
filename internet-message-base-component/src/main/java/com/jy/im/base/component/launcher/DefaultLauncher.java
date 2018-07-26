package com.jy.im.base.component.launcher;

import com.jy.im.base.component.daemon.Daemon;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.concurrent.TimeUnit;

public class DefaultLauncher extends AbstractLauncher {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(DefaultLauncher.class);
    private LauncherConfig launcherConfig = LauncherConfig.defaultConfig();

    public void doStart() {
        //启动所有服务器
        if (!daemonList.isEmpty()) {
            logger.info("server to start list size: {}", daemonList.size());
            long before = System.currentTimeMillis();
            daemonList.forEach(this::startServer);
            //等待直到超时
            while (startedDaemonSet.size() != daemonList.size()) {
                boolean timeout = System.currentTimeMillis() - before > launcherConfig.getTimeout();
                if(timeout) {
                    logger.error("Launcher starts timeout!");
                    break;
                }
                try { Thread.sleep(500); } catch (InterruptedException e) { /* do nothing */ }
            }
        }
        //故障服务器检查
        if (launcherConfig.isAutoRestart() && !stop) {
            logger.info("launcher config server restart: {}", launcherConfig.isAutoRestart());
            final HashedWheelTimer timer = new HashedWheelTimer();
            final int period = launcherConfig.getHealthyCheckInSecond();
            timer.newTimeout(new TimerTask() {
                public void run(Timeout timeout) {
                    logger.info("check down server....");
                    if (downDaemonList.size() > 0) {
                        logger.info("find down server, size: {}", downDaemonList.size());
                        while (downDaemonList.size() > 0) {
                            Daemon daemon = downDaemonList.remove(0);
                            logger.info("server restart: {}", daemon);
                            if (!stop) {
                                startServer(daemon);
                            } else {
                                break;
                            }
                        }
                    }
                    if (!stop) {
                        timer.newTimeout(this, period, TimeUnit.SECONDS);
                    } else {
                        new Thread(timer::stop).start();
                    }
                }
            }, period, TimeUnit.SECONDS);
            timer.start();
        }
    }

    public void doClose() {
        //停止所有服务器
        if (!daemonList.isEmpty()) {
            logger.info("server to stop list size: {}", daemonList.size());
            daemonList.forEach(this::shutdownServer);
            while (startedDaemonSet.size() != 0) {
                try {
                    Thread.sleep(500);
                    logger.info("alive alive remain: {}", startedDaemonSet.size());
                } catch (InterruptedException e) { /* do nothing */ }
            }
        }
    }

    public boolean isStartUpOk() {
        return startedDaemonSet.size() == daemonList.size();
    }
}
