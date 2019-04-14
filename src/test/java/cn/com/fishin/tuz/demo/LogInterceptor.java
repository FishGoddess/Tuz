package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.interceptor.DefaultInterceptor;

/**
 * 演示日志拦截器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 22:36:50</p>
 */
public class LogInterceptor extends DefaultInterceptor {

    @Override
    public boolean afterThrowing(InterceptedMethod method) {
        System.out.println("日志记录异常错误 ===> " + method.getException());
        return true;
    }

    @Override
    public boolean before(InterceptedMethod method) {
        System.out.println("日志记录正常操作 ===> " + method.getThisMethod());
        return true;
    }
}
