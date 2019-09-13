package cn.com.fishin.tuz.helper;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.interceptor.Interceptor;

/**
 * <p>拦截器帮助类</p>
 * <p>Interceptor helper</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 14:52:08</p>
 */
public final class InterceptorHelper {

    /**
     * <p>完成一组拦截器的 before 方法</p>
     * <p>Invoke before method of these interceptors</p>
     *
     * @param interceptors <p>全部需要调用的拦截器</p><p>All interceptors that need to invoke</p>
     * @param method       <p>拦截的具体方法信息</p><p>All infomation of intercepted method</p>
     * @return <p>true 全部执行完毕，false 反之</p><p>true if all interceptors invoke successfully, otherwise not</p>
     */
    public static boolean before(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.before(method));
    }

    /**
     * <p>完成一组拦截器的 after 方法</p>
     * <p>Invoke after method of these interceptors</p>
     *
     * @param interceptors <p>全部需要调用的拦截器</p><p>All interceptors that need to invoke</p>
     * @param method       <p>拦截的具体方法信息</p><p>All infomation of intercepted method</p>
     * @return <p>true 全部执行完毕，false 反之</p><p>true if all interceptors invoke successfully, otherwise not</p>
     */
    public static boolean after(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.after(method));
    }

    /**
     * <p>完成一组拦截器的 afterThrowing 方法</p>
     * <p>Invoke afterThrowing method of these interceptors</p>
     *
     * @param interceptors <p>全部需要调用的拦截器</p><p>All interceptors that need to invoke</p>
     * @param method       <p>拦截的具体方法信息</p><p>All infomation of intercepted method</p>
     * @return <p>true 全部执行完毕，false 反之</p><p>true if all interceptors invoke successfully, otherwise not</p>
     */
    public static boolean afterThrowing(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.afterThrowing(method));
    }

    //

    /**
     * <p>完成一组拦截器的 afterReturning 方法</p>
     * <p>Invoke afterReturning method of these interceptors</p>
     *
     * @param interceptors <p>全部需要调用的拦截器</p><p>All interceptors that need to invoke</p>
     * @param method       <p>拦截的具体方法信息</p><p>All infomation of intercepted method</p>
     * @return <p>true 全部执行完毕，false 反之</p><p>true if all interceptors invoke successfully, otherwise not</p>
     */
    public static boolean afterReturning(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.afterReturning(method));
    }

    // 拦截器调用模板类
    // Interceptor invoke template class
    private static class InterceptorInvokeTemplate {

        // 所有拦截器
        // All interceptors
        private Interceptor[] interceptors = null;

        // 使用所有拦截器构造这个拦截器调用模板
        // Use all interceptors to init this template
        InterceptorInvokeTemplate(Interceptor[] interceptors) {
            this.interceptors = interceptors;
        }

        // 拦截器拦截方法，拦截器具体调用哪个方法由具体的拦截器决定
        // Interceptor method, you need implementing this interface
        interface Invokable {
            boolean invoke(Interceptor interceptor);
        }

        // 执行所有拦截器的这个拦截方法
        // Invoke all interceptors
        boolean intercept(Invokable invokable) {

            // 如果有一个拦截器返回 false，就不会继续往下执行了
            boolean result = true;
            for (Interceptor interceptor : interceptors) {
                if (!invokable.invoke(interceptor)) {
                    result = false;
                }
            }

            // 返回 true 意味着这个拦截方法全部执行成功
            return result;
        }
    }
}
