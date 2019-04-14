package cn.com.fishin.tuz.handler;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.helper.InterceptorHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;

import java.lang.reflect.Method;

/**
 * 拦截器专用的代理调用处理器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 15:37:14</p>
 */
public class InterceptorInvocationHandler extends AbstractInvocationHandler {

    // 所有拦截器
    private Interceptor[] interceptors = null;

    public InterceptorInvocationHandler(Object target, Interceptor[] interceptors) {
        super(target);
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 这是被拦截的方法
        InterceptedMethod interceptedMethod = new InterceptedMethod(getTarget(), method, args);

        // 调用前置拦截
        if (!InterceptorHelper.before(interceptors, interceptedMethod)) {

            // 还没有开始执行方法
            // 如果你需要返回数据，就设置进 before 的 InterceptedMethod 对象中
            // 这在实现缓存的时候经常用到
            return interceptedMethod.getResult();
        }

        try {
            // 调用实际的方法，并将结果存在 interceptedMethod.result 中
            interceptedMethod.setResult(method.invoke(getTarget(), args));

            // 调用后置拦截
            InterceptorHelper.after(interceptors, interceptedMethod);

            // 调用完之后返回结果
            return interceptedMethod.getResult();
        } catch (Exception ex) {

            // 发生异常时的拦截
            interceptedMethod.setException(ex);
            InterceptorHelper.afterThrowing(interceptors, interceptedMethod);

            // 拦截完之后返回上一次执行结果
            return interceptedMethod.getResult();
        } finally {

            // 最终返回的拦截
            InterceptorHelper.afterReturning(interceptors, interceptedMethod);
        }
    }
}
