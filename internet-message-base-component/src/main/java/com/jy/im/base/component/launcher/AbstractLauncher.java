package com.jy.im.base.component.launcher;


import com.jy.im.base.component.daemon.Daemon;
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
    protected final List<Daemon> daemonList = new ArrayList<>();
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
            logger.info("launcher listener list size: {}, begin to call LauncherListener#afterStartup(AbstractLauncher launcher)", launcherListenerList.size());
            launcherListenerList.forEach(listener -> listener.afterStartup(AbstractLauncher.this));
        }
        logger.info("launcher startup successfully");
    }

    public abstract void doStart();

    /**
     * 关闭
     */
    @Override
    public void close() {
        logger.info("launcher begin to shutdown");
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
            logger.info("launcher listener list size: {}, begin to call LauncherListener#afterClose(AbstractLauncher launcher)", launcherListenerList.size());
            launcherListenerList.forEach(listener -> listener.afterClose(AbstractLauncher.this));
        }
        logger.info("launcher shutdown successfully");
    }

    protected void startServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> {
                server.beforeStart();
                server.start(AbstractLauncher.this);
            });
        }
    }

    protected void shutdownServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> {
                server.beforeShutdown();
                server.shutdown(AbstractLauncher.this);
            });
        }
    }

    public abstract void doClose();

    @Override
    public void daemonStartSuccess(Daemon server) {
        int count = serverSuccessCount.addAndGet(1);
        logger.info("server alive count: {}", count);
        //因为server会阻塞线程,所以在这里进行调用afterStart()
        server.afterStart();
    }

    @Override
    public void daemonShutdownSuccess(Daemon server) {
        int count = serverSuccessCount.addAndGet(-1);
        logger.info("server alive count: {}", count);
        downDaemonList.add(server);
        server.afterShutdown();
    }

    public AbstractLauncher addDaemonList(List<Daemon> daemonList) {
        if (daemonList != null && !daemonList.isEmpty()) {
            this.daemonList.addAll(daemonList);
        }
        return this;
    }

    public AbstractLauncher addDaemon(Daemon daemon) {
        if (daemon != null) {
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
    public boolean getLauncherStatus() {
        return !stop;
    }
}
