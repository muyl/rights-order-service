package com.cc.cmbc.order.common.log;

import com.cc.cmbc.order.common.util.ThreadMdcUtil;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author 拓仲 on 2020/3/28
 */
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolTaskExecutor {

    final private boolean useFixedContext;
    final private Map<String, String> fixedContext;

    /**
     * Pool where task threads take MDC from the submitting thread.
     */
    public static ThreadPoolExecutorMdcWrapper newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        return new ThreadPoolExecutorMdcWrapper(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, queueCapacity);
    }

    private ThreadPoolExecutorMdcWrapper(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        setCorePoolSize(corePoolSize);
        setMaxPoolSize(maximumPoolSize);
        setKeepAliveSeconds((int) unit.toSeconds(keepAliveTime));
        setQueueCapacity(queueCapacity);
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }

    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /**
     * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
     * all delegate to this.
     */
    @Override
    public void execute(@NonNull Runnable command) {
        super.execute(wrap(command, getContextForTask()));
    }

    @NonNull
    @Override
    public Future<?> submit(@NonNull Runnable task) {
        return super.submit(wrap(task, getContextForTask()));
    }

    @NonNull
    @Override
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return super.submit(wrap(task, getContextForTask()));
    }

    private static <T> Callable<T> wrap(final Callable<T> task, final Map<String, String> context) {
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return task.call();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }

    private static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }
}
