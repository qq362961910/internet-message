package com.jy.im.base.component.launcher;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.launcher.config.LauncherConfig;
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
        if (!getDaemonList().isEmpty()) {
            logger.info(String.format("server list size: %d", getDaemonList().size()));
            long before = System.currentTimeMillis();
            boolean notTimeout = true;
            for (final Daemon server : getDaemonList()) {
                startServer(server);
            }
            //等待直到超时
            while (serverSuccessCount.get() != getDaemonList().size() && (notTimeout = System.currentTimeMillis() - before < launcherConfig.getTimeout())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
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
                        logger.info("server health check monitor stop....");
                    }
                }
            }, period, TimeUnit.SECONDS);
            timer.start();
        }
    }

    public void doClose() {
        //停止所有服务器
        if (!getDaemonList().isEmpty()) {
            logger.info(getDaemonList().size() + " servers to stop");
            for (Daemon server : getDaemonList()) {
                server.close(this);
            }
            while (serverSuccessCount.get() != 0) {
                try {
                    Thread.sleep(500);
                    logger.info("alive alive remain: " + serverSuccessCount.get());
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
