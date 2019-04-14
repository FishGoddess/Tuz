package cn.com.fishin.tuz.helper;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.interceptor.Interceptor;

/**
 * 拦截器帮助类
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 14:52:08</p>
 */
public class InterceptorHelper {

    // 完成一组拦截器的 before 方法
    public static boolean before(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.before(method));
    }

    // 完成一组拦截器的 after 方法
    public static boolean after(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.after(method));
    }

    // 完成一组拦截器的 afterThrowing 方法
    public static boolean afterThrowing(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.afterThrowing(method));
    }

    // 完成一组拦截器的 afterReturning 方法
    public static boolean afterReturning(Interceptor[] interceptors, InterceptedMethod method) {
        return new InterceptorInvokeTemplate(interceptors).
                intercept(interceptor -> interceptor.afterReturning(method));
    }

    // 拦截器调用模板类
    private static class InterceptorInvokeTemplate {

        // 所有拦截器
        private Interceptor[] interceptors = null;

        // 使用所有拦截器构造这个拦截器调用模板
        InterceptorInvokeTemplate(Interceptor[] interceptors) {
            this.interceptors = interceptors;
        }

        // 拦截器拦截方法，拦截器具体调用哪个方法由具体的拦截器决定
        interface Invokable {
            boolean invoke(Interceptor interceptor);
        }

        // 执行所有拦截器的这个拦截方法
        boolean intercept(Invokable invokable) {
            // 如果有一个拦截器返回 false，就不会继续往下执行了
            for (Interceptor interceptor : interceptors) {
                if (!invokable.invoke(interceptor)) {
                    return false;
                }
            }

            // 返回 true 意味着这个拦截方法全部执行成功
            return true;
        }
    }
}
