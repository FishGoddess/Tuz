package cn.com.fishin.tuz.template;

import java.util.concurrent.locks.Lock;

/**
 * <p>Lock 代码块模板类</p>
 * <p>这个类同样是为了保证使用锁的优雅性</p>
 * <p>Lock code block template class</p>
 * <p>This class is for coding gracefully</p>
 *
 * <pre>
 *     // return string result
 *     return new LockTemplate&lt;String&gt;().lockWith(
 *                 lock,
 *                 () -&gt; "HelloWorld!"
 *     );
 * </pre>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/25 15:48:21</p>
 */
public final class LockTemplate<Result> {

    /**
     * <p>使用给定的 lock 来执行线程安全的操作</p>
     * <p>Use given lock to avoid the concurrent problems</p>
     *
     * @param lock <p>给定的 lock</p>
     *             <p>Given lock</p>
     * @param block <p>要执行的代码块</p><p>The code to be executed</p>
     * @throws Throwable <p>如果执行出现异常</p><p>If execute meets error</p>
     * @return <p>执行的返回值</p><p>Return result</p>
     */
    public Result lockWith(Lock lock, LockBlock<Result> block) throws Throwable {
        lock.lock();
        try {
            return block.doInLock();
        } finally {
            lock.unlock();
        }
    }

    /**
     * <p>为了使用更加优雅，特意加入了接口，这样可以使用 Lambda 表达式</p>
     * <p>To be graceful, this interface will be replace by Lambda</p>
     *
     * @param <Result> <p>返回值的类型</p>
     *                <p>Return type</p>
     */
    public interface LockBlock<Result> {

        /**
         * <p>这个方法中的操作时线程安全的</p>
         * <p>The operations in this method is safe</p>
         *
         * @return <p>返回执行结果</p><p>Return result</p>
         * @throws Throwable <p>如果执行出现异常</p><p>If execute meets error</p>
         */
        Result doInLock() throws Throwable;
    }
}
