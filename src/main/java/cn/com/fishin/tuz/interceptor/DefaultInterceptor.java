package cn.com.fishin.tuz.interceptor;

import cn.com.fishin.tuz.entity.InterceptedMethod;

/**
 * 默认的拦截器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 16:21:27</p>
 */
public class DefaultInterceptor implements Interceptor {

    @Override
    public boolean before(InterceptedMethod method) {
        return true;
    }

    @Override
    public boolean after(InterceptedMethod method) {
        return true;
    }

    @Override
    public boolean afterThrowing(InterceptedMethod method) {
        return true;
    }

    @Override
    public boolean afterReturning(InterceptedMethod method) {
        return true;
    }
}
