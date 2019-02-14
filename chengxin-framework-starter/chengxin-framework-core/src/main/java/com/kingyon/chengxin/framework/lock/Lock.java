package com.kingyon.chengxin.framework.lock;

/**
 * Created by Administrator on 16-10-9.
 */
public interface Lock {

    /**
     * 阻塞性的获取锁, 不响应中断
     */
    public void lock();

    /**
     * 阻塞性的获取锁, 响应中断
     * @throws InterruptedException
     */
    public void lockInterruptible() throws InterruptedException;

    /**
     * 尝试获取锁, 获取不到会等待锁的超时时间，直到成功获取, 不阻塞
     * @return  {@code true} ,若成功获取到锁, {@code false} 若在指定时间内未���取到锁
     */
    public boolean tryLock();

    /**
     * 超时自动返回的阻塞性的获取锁, 不响应中断
     * @param time  获取锁的超时时间（在此时间范围内，超出时间则无论成功与否均返回）
     * @return      {@code true} ,若成功获取到锁, {@code false} 若在指定时间内未���取到锁
     */
    public boolean tryLock(long time);

    /**
     * 超时自动返回的阻塞性的获取锁, 响应中断
     * @param time
     * @return  {@code true} 若成功获取到锁, {@code false} 若在指定时间内未获取到锁
     * @throws InterruptedException
     */
    public boolean tryLockInterruptible(long time) throws InterruptedException;

    /**
     * 释放锁
     */
    public void unlock();

}
