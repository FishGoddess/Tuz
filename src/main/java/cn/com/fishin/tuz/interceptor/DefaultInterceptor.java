package cn.com.fishin.tuz.interceptor;

import cn.com.fishin.tuz.entity.InterceptedMethod;

/**
 * <p>默认的拦截器</p>
 * <p>建议继承这个类，选择性覆盖要拦截的方法</p>
 * <p>Default interceptor</p>
 * <p>Recommend to extend this class, and override methods selectively</p>
 *
 * @see cn.com.fishin.tuz.interceptor.Interceptor
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 16:21:27</p>
 */
public class DefaultInterceptor implements Interceptor {

    @Override
    /**
     * @see cn.com.fishin.tuz.interceptor.Interceptor#before(InterceptedMethod)
     */
    public boolean before(InterceptedMethod method) {
        return true;
    }

    @Override
    /**
     * @see cn.com.fishin.tuz.interceptor.Interceptor#after(InterceptedMethod)
     */
    public boolean after(InterceptedMethod method) {
        return true;
    }

    @Override
    /**
     * @see cn.com.fishin.tuz.interceptor.Interceptor#afterThrowing(InterceptedMethod)
     */
    public boolean afterThrowing(InterceptedMethod method) {
        return true;
    }

    @Override
    /**
     * @see cn.com.fishin.tuz.interceptor.Interceptor#afterReturning(InterceptedMethod)
     */
    public boolean afterReturning(InterceptedMethod method) {
        return true;
    }
}
