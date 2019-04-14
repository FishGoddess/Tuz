package cn.com.fishin.tuz.proxy;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.interceptor.Interceptor;

/**
 * 测试使用的拦截器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 16:21:27</p>
 */
public class TestInterceptor implements Interceptor {

    @Override
    public boolean before(InterceptedMethod method) {
        System.out.println("before ==> " + method);
        return true;
    }

    @Override
    public boolean after(InterceptedMethod method) {
        System.out.println("after ==> " + method);
        return true;
    }

    @Override
    public boolean afterThrowing(InterceptedMethod method) {
        System.out.println("afterThrowing ==> " + method);
        return true;
    }

    @Override
    public boolean afterReturning(InterceptedMethod method) {
        System.out.println("afterReturning ==> " + method);
        return true;
    }
}
