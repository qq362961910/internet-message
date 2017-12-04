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
            boolean notTimeout = true;
            daemonList.forEach(this::startServer);
            //等待直到超时
            while (serverSuccessCount.get() != daemonList.size() && (notTimeout = System.currentTimeMillis() - before < launcherConfig.getTimeout())) {
                try { Thread.sleep(500); } catch (InterruptedException e) { /* do nothing */ }
            }
            if (!notTimeout) {
                logger.error("Launcher starts timeout!");
            }
        }
        //故障服务器检查
        if (launcherConfig.isAutoRestart() && !stop) {
            logger.info("launcher config server restart: true");
            final HashedWheelTimer timer = new HashedWheelTimer();
            final int period = launcherConfig.getHealthyCheckInSecond();
            timer.newTimeout(new TimerTask() {
                public void run(Timeout timeout) throws Exception {
                    logger.info("check down server....");
                    if (downDaemonList.size() > 0) {
                        logger.info(stop + ", find down server, size: " + downDaemonList.size());
                        while (downDaemonList.size() > 0) {
                            Daemon daemon = downDaemonList.get(0);
                            logger.info("server restart: " + daemon);
                            if (!stop) {
                                startServer(daemon);
                                downDaemonList.remove(0);
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
            while (serverSuccessCount.get() != 0) {
                try {
                    Thread.sleep(500);
                    logger.info("alive alive remain: " + serverSuccessCount.get());
                } catch (InterruptedException e) { /* do nothing */ }
            }
        }
    }

    public boolean isStartUpOk() {
        return serverSuccessCount.get() == daemonList.size();
    }
}
