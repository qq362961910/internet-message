package com.jy.im.base.component.launcher;


import com.jy.im.base.component.launcher.listener.LauncherListener;
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

public abstract class AbstractLauncher implements Launcher{

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractLauncher.class);

    protected volatile boolean stop = false;
    protected AtomicInteger serverSuccessCount = new AtomicInteger(0);
    private List<Daemon> daemonList = new ArrayList<>();
    protected List<Daemon> downDaemonList = new Vector<>();
    private List<LauncherListener> launcherListenerList = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 启动
     * */
    @Override
    public void startup() {
        logger.info("launcher begin to startup");
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        doStart();
        //回调监听器
        if (!launcherListenerList.isEmpty()) {
            logger.info(String.format("launcher listener size: %d, begin to call launcher listeners",launcherListenerList.size()));
            for (LauncherListener listener: launcherListenerList) {
                listener.startup(this);
            }
        }
        logger.info("launcher startup successfully");
    }

    public abstract void doStart();

    /**
     * 关闭
     * */
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
            for (LauncherListener listener: launcherListenerList) {
                try { listener.close(this); } catch (Exception e) { }
            }
        }
        logger.info("launcher shutdown successfully");
        //关闭日志
        //todo
    }
    protected void startServer(Daemon server) {
        if (!executorService.isShutdown()) {
            executorService.submit(() -> server.start(this));
        }
    }

    public abstract void doClose();

    @Override
    public void serverStartSuccess(Daemon server) {
        serverSuccessCount.addAndGet(1);
        logger.info("server alive count: " + serverSuccessCount.get());
    }

    @Override
    public void serverShutdownSuccess(Daemon server) {
        serverSuccessCount.addAndGet(-1);
        downDaemonList.add(server);
    }

    public List<Daemon> getDaemonList() {
        return daemonList;
    }

    public AbstractLauncher setDaemonList(List<Daemon> daemonList) {
        if(daemonList != null && !daemonList.isEmpty()) {
            this.daemonList.addAll(daemonList);
        }
        return this;
    }

    public List<LauncherListener> getLauncherListenerList() {
        return launcherListenerList;
    }

    public AbstractLauncher setLauncherListenerList(List<LauncherListener> launcherListenerList) {
        if(launcherListenerList != null && !launcherListenerList.isEmpty()) {
            this.launcherListenerList.addAll(launcherListenerList);
        }
        return this;
    }
}
