package cn.com.fishin.tuz.interceptor;

import cn.com.fishin.tuz.entity.InterceptedMethod;

/**
 *
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 13:41:06</p>
 */
public interface Interceptor {

    // 在执行切面方法之前执行这个
    boolean before(InterceptedMethod method);

    // 在执行切面方法之后执行这个
    boolean after(InterceptedMethod method);

    // 在执行切面方法抛出异常之后执行这个方法
    boolean afterThrowing(InterceptedMethod method);

    // 在方法返回之后执行，实质是在 finally 块中执行
    boolean afterReturning(InterceptedMethod method);
}
