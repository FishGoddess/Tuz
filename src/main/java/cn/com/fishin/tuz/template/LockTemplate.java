package cn.com.fishin.tuz.template;

import java.util.concurrent.locks.Lock;

/**
 * <p>Lock 代码块模板类</p>
 * <p>这个类同样是为了保证使用锁的优雅性</p>
 * <p>Lock code block template class</p>
 * <p>This class is for coding gracefully</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/25 15:48:21</p>
 */
@Deprecated
public abstract class LockTemplate<Result> {

    /**
     * <p>使用给定的 lock 来执行线程安全的操作</p>
     * <p>Use given lock to avoid the concurrent problems</p>
     *
     * @param lock <p>给定的 lock</p>
     *             <p>Given lock</p>
     * @return <p>执行的返回值</p><p>Return result</p>
     */
    public Result lockWith(Lock lock) {
        lock.lock();
        return new TryBlockTemplate<Result>() {
            @Override
            protected Result doInTry(Object... args) throws Throwable {
                return doInLock();
            }

            @Override
            protected void doInFinally(Object... args) {
                lock.unlock();
            }
        }.tryWith();
    }

    protected abstract Result doInLock() throws Throwable;
}
