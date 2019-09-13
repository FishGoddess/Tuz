package cn.com.fishin.tuz.handler;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.helper.InterceptorHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * <p>拦截器专用的代理调用处理器</p>
 * <p>它实现了父类中的 invoke 方法：</p>
 * @see cn.com.fishin.tuz.handler.InterceptorInvocationHandler#invoke
 * @see cn.com.fishin.tuz.handler.AbstractInvocationHandler#invoke
 *
 * <p>Interceptor invocation handler</p>
 * <p>It implements the super method called invoke:</p>
 * @see cn.com.fishin.tuz.handler.InterceptorInvocationHandler#invoke
 * @see cn.com.fishin.tuz.handler.AbstractInvocationHandler#invoke
 *
 * @see cn.com.fishin.tuz.handler.AbstractInvocationHandler
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 15:37:14</p>
 */
public class InterceptorInvocationHandler extends AbstractInvocationHandler {

    // 所有拦截器
    // All interceptors
    private Interceptor[] interceptors = null;

    /**
     * <p>使用目标代理对象和拦截器进行初始化</p>
     * <p>Use target object and interceptors to init</p>
     *
     * @param target <p>目标代理对象</p>
     *               <p>target object</p>
     * @param interceptors <p>拦截器</p>
     *                     <p>interceptors</p>
     */
    public InterceptorInvocationHandler(Object target, Interceptor[] interceptors) {
        super(target);
        this.interceptors = interceptors;
    }

    @Override
    /**
     * <p>实现这个方法来实现切面功能</p>
     * <p>
     *     <p>注意：</p>
     *     <p>如果 before 层级的拦截器返回 false，那么 after 级别的拦截器是不会被执行的
     *     但是 afterReturning 是可以被执行的</p>
     * </p>
     * <p>Implements this method to get the power of aspect</p>
     * <p>Attention:</p>
     * <p>
     *     If level before return false, then level after will not be executed,
     *     but level afterReturning will
     * </p>
     *
     * @see InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 这是被拦截的方法
        InterceptedMethod interceptedMethod = new InterceptedMethod(getTarget(), method, args);
        try {

            // 调用前置拦截
            if (!InterceptorHelper.before(interceptors, interceptedMethod)) {

                // 还没有开始执行方法
                // 如果你需要返回数据，就设置进 before 的 InterceptedMethod 对象中
                // 这在实现缓存的时候经常用到
                return interceptedMethod.getResult();
            }

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
