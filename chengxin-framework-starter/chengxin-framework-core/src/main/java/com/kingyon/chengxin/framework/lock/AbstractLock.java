package com.kingyon.chengxin.framework.lock;

/**
 * Created by Administrator on 16-10-9.
 */
public abstract class AbstractLock implements Lock {
    /**
     * <pre>
     * 这里需不需要保证可见性值得讨论, 因为是分布式的锁,
     * 1.同一个jvm的多个线程使用不同的锁对象其实也是可以的, 这种情况下不需要保证可见性
     * 2.同一个jvm的多个线程使用同一个锁对象, 那可见性就必须要保证了.
     * </pre>
     */
    protected volatile boolean locked;

    /**
     * 当前jvm内持有该锁的线程(if have one)
     */
    private Thread exclusiveOwnerThread;

    @Override
    public void lock() {
        try {
            lock(0, false);
        } catch (InterruptedException e) {
            // TODO ignore
        }
    }

    @Override
    public void lockInterruptible() throws InterruptedException {
        lock(0, true);
    }

    @Override
    public boolean tryLock() {
        return tryLock(0L);
    }

    @Override
    public boolean tryLock(long time) {
        try {
            return lock(time, false);
        } catch (InterruptedException e) {
            // TODO ignore
        }
        return false;
    }

    @Override
    public boolean tryLockInterruptible(long time) throws InterruptedException {
        return lock(time, true);
//        return false;
    }

    @Override
    public void unlock() {
        // TODO 检查当前线程是否持有锁
        if (Thread.currentThread() != getExclusiveOwnerThread() ) {
//            throw new IllegalMonitorStateException("current thread does not hold the lock");
            return;
        }

        unlock0();
        setExclusiveOwnerThread(null);
    }

    protected void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    protected abstract void unlock0();

    /**
     * 阻塞式获取锁的实现
     *
     * @param time
     * @param interrupt 是否响应中断
     * @return
     * @throws InterruptedException
     */
    protected abstract boolean lock(long time, boolean interrupt) throws InterruptedException;

}
