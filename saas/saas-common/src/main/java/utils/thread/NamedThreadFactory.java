package utils.thread;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:线程命名工厂类
 **/
public class NamedThreadFactory implements ThreadFactory {
    public final AtomicInteger poolNumber = new AtomicInteger(0);
    public final AtomicInteger threadNumber = new AtomicInteger(0);
    private ThreadGroup group;

    private String businessName;
    private String prefixName;

    public NamedThreadFactory(String businessName) {
        if (Objects.isNull(businessName)) {
            businessName = "";
        }
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.businessName = businessName;
        prefixName = "pool_" + poolNumber.incrementAndGet() + "_thread_";
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadInfoName = prefixName + threadNumber.incrementAndGet();
        Thread t = new Thread(group, r, threadInfoName.concat("_").concat(businessName));
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
