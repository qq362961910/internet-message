package com.jy.im.base.component.launcher;


import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.launcher.listener.LauncherListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractLauncher implements Launcher {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractLauncher.class);

    /**
     * 启动器状态
     */
    protected volatile boolean stop = false;
    /**
     * 服务器成功启动数量
     */
    protected final AtomicInteger serverSuccessCount = new AtomicInteger(0);
    /**
     * 服务器实例
     */
    private final List<Daemon> daemonList = new ArrayList<>();
    /**
     * 停机实例
     */
    protected final List<Daemon> downDaemonList = new Vector<>();
    /**
     * 启动器监听器
     */
    private final List<LauncherListener> launcherListenerList = new ArrayList<>();

    /**
     * 线程池
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 启动
     */
    @Override
    public void startup() {
        logger.info("launcher begin to startup");
        doStart();
        //回调监听器
        if (!launcherListenerList.isEmpty()) {
            logger.info("launcher listener size: {}, begin to call launcher listeners", launcherListenerList.size());
            for (LauncherListener listener : launcherListenerList) {
                listener.startup(this);
            }
        }
        logger.info("launcher startup successfully");
    }

    public abstract void doStart();

    /**
     * 关闭
     */
    @Override
    public void close() {
        logger.info("launcher begin to shutdown!!!");
        stop = true;
        doClose();
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Executor Service shutdown error", e);
            executorService.shutdownNow();
        }
        //回调监听器
        if (!launcherListenerList.isEmpty()) {
            for (LauncherListener listener : launcherListenerList) {
                try {
                    listener.close(this);
                } catch (Exception e) {}
            }
        }
        logger.info("launcher shutdown successfully");
    }

    protected void startServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> server.start(this));
        }
    }

    public abstract void doClose();

    @Override
    public void daemonStartSuccess(Daemon server) {
        serverSuccessCount.addAndGet(1);
        logger.info("server alive count: " + serverSuccessCount.get());
        server.afterStart();
    }

    @Override
    public void daemonShutdownSuccess(Daemon server) {
        serverSuccessCount.addAndGet(-1);
        downDaemonList.add(server);
        server.afterShutdown();
    }

    public List<Daemon> getDaemonList() {
        return daemonList;
    }

    public AbstractLauncher addDaemonList(List<Daemon> daemonList) {
        if (daemonList != null && !daemonList.isEmpty()) {
            this.daemonList.addAll(daemonList);
        }
        return this;
    }
    public AbstractLauncher addDaemon(Daemon daemon) {
        if(daemon != null) {
            daemonList.add(daemon);
        }
        return this;
    }

    public List<LauncherListener> getLauncherListenerList() {
        return launcherListenerList;
    }

    public AbstractLauncher setLauncherListenerList(List<LauncherListener> launcherListenerList) {
        if (launcherListenerList != null && !launcherListenerList.isEmpty()) {
            this.launcherListenerList.addAll(launcherListenerList);
        }
        return this;
    }
}
