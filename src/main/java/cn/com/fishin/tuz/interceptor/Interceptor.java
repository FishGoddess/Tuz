package cn.com.fishin.tuz.interceptor;

import cn.com.fishin.tuz.entity.InterceptedMethod;

/**
 * <p>拦截器接口</p>
 * <p>这个拦截器可以拦截到对象的所有方法，进行业务的主次解耦</p>
 * <p>建议直接使用默认的拦截器，选择性覆盖方法</p>
 * <p>注意：</p>
 * <p>如果 before 层级的拦截器返回 false，那么 after 级别的拦截器是不会被执行的
 * 但是 afterReturning 是可以被执行的</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 13:41:06</p>
 * @see DefaultInterceptor
 *
 * <p>Interceptor interface</p>
 * <p>It will intercept all methods in an object</p>
 * <p>You may use default interceptor, and override methods seletively</p>
 * <p>Attention:</p>
 * <p>
 * If level before return false, then level after will not be executed,
 * but level afterReturning will
 * </p>
 * @see DefaultInterceptor
 */
public interface Interceptor {

    /**
     * <p>在执行切面方法之前执行这个方法</p>
     * <p>
     * <strong>注意：</strong>
     * <p>在执行这个方法的时候，由于被拦截的方法还没有执行，所以这个时候是拿不到返回值的，</p>
     * <p>如果你在做缓存-数据库的拦截，需要将从缓存中拿到的数据设置进 InterceptedMethod#result，</p>
     * <p>然后返回 false 以阻止继续执行被拦截的方法</p>
     *
     * <p>Invoke before intercepted method</p>
     * <p>
     * <strong>Notice:</strong>
     * <p>When this method is invoking, you can not get the result of the intercepted method, </p>
     * <p>so if you are develop a cache-database service, you should set the result from cache by </p>
     * <p>cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object), </p>
     * <p>and return false to prevent invoking intercepted method</p>
     *
     * @param method <p>被拦截方法的信息</p><p>Intercepted method</p>
     * @return <p>true 允许下一个拦截器继续执行，false 反之</p><p>true allow next interceptor invoking</p>
     * @see cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object)
     */
    boolean before(InterceptedMethod method);

    /**
     * <p>在执行切面方法之后执行这个方法</p>
     * <p>
     * <strong>注意：</strong>
     * <p>如果需要获取被拦截方法的执行结果，调用 cn.com.fishin.tuz.entity.InterceptedMethod#getResult()。</p>
     * <p>返回 false 可以阻止继续下一个拦截器执行</p>
     *
     * <p>Invoke after intercepted method</p>
     * <p>
     * <strong>Notice:</strong>
     * <p>If you want the result of intercepted method, </p>
     * <p>call cn.com.fishin.tuz.entity.InterceptedMethod#getResult().</p>
     * <p>return false to prevent invoking next interceptor</p>
     *
     * @param method <p>被拦截方法的信息</p><p>Intercepted method</p>
     * @return <p>true 允许下一个拦截器继续执行，false 反之</p><p>true allow next interceptor invoking</p>
     * @see cn.com.fishin.tuz.entity.InterceptedMethod#getResult()
     */
    boolean after(InterceptedMethod method);

    /**
     * <p>在执行切面方法抛出异常之后执行这个方法</p>
     * <p>
     * <strong>注意：</strong>
     * <p>在执行这个方法的时候，由于被拦截的方法抛出了异常，所以这个时候有可能是拿不到返回值的，</p>
     * <p>如果你需要返回结果，请调用 cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object)。</p>
     * <p>返回 false 可以阻止继续下一个拦截器执行</p>
     *
     * <p>Invoke this method when intercepted method throws an exception</p>
     * <p>
     * <strong>Notice:</strong>
     * <p>When this method is invoking, you may not get the result of the intercepted method, </p>
     * <p>so if you want to return a result, you should set the result by invoking </p>
     * <p>cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object). </p>
     * <p>return false to prevent invoking intercepted method</p>
     *
     * @param method <p>被拦截方法的信息</p><p>Intercepted method</p>
     * @return <p>true 允许下一个拦截器继续执行，false 反之</p><p>true allow next interceptor invoking</p>
     * @see cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object)
     */
    boolean afterThrowing(InterceptedMethod method);

    /**
     * <p>在方法返回之后执行，实质是在 finally 块中执行</p>
     * <p>
     * <strong>注意：</strong>
     * <p>在执行这个方法的时候，由于被拦截的方法可能抛出了异常，所以这个时候有可能是拿不到返回值的，</p>
     * <p><strong>不建议在这个方法中操作被拦截方法的执行结果</strong></p>
     *
     * <p>Invoke this method when intercepted method throws an exception</p>
     * <p>
     * <strong>Notice:</strong>
     * <p>When this method is invoking, you may not get the result of the intercepted method. </p>
     * <p><strong>Update result in this method is not recommended</strong></p>
     * <p>return false to prevent invoking intercepted method</p>
     *
     * @param method <p>被拦截方法的信息</p><p>Intercepted method</p>
     * @return <p>true 允许下一个拦截器继续执行，false 反之</p><p>true allow next interceptor invoking</p>
     * @see cn.com.fishin.tuz.entity.InterceptedMethod#setResult(java.lang.Object)
     */
    boolean afterReturning(InterceptedMethod method);
}
