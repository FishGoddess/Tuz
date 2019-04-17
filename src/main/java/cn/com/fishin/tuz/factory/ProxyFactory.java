package cn.com.fishin.tuz.factory;

import cn.com.fishin.tuz.handler.AbstractInvocationHandler;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.helper.ClassHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;


/**
 * <p>代理工厂</p>
 * <p>可以将现有对象进行一次代理</p>
 * <p>Proxy Factory</p>
 * <p>It has power to proxy a object</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 14:04:06</p>
 */
public class ProxyFactory {

    /**
     * <p>返回实际产生的动态代理类对象</p>
     * <p>这里是使用 CGlib 进行动态代理，要求这个目标对象类必须是可继承的！</p>
     * <p>Return a dynamic proxy instance</p>
     * <p>Due to CGlib, this target must be not final!</p>
     *
     * @see AbstractInvocationHandler <p>抽象的调用处理器</p><p>Abstarct invocation handler</p>
     * @see InterceptorInvocationHandler <p>拦截器使用的调用处理器</p><p>Interceptor invocation handler</p>
     *
     * @param target <p>要被代理的目标对象</p>
     *               <p>Target object to proxy</p>
     * @param handler <p>调用处理器</p><p>Invocation handler</p>
     * @return <p>返回实际产生的动态代理类对象</p><p>Return a dynamic proxy instance</p>
     */
    public static Object wrap(Object target, InvocationHandler handler) {
        return make(target, handler).create();
    }

    /**
     * <p>返回实际产生的动态代理类对象</p>
     * <p>这里是使用 CGlib 进行动态代理，要求这个目标对象类必须是可继承的！</p>
     * <p>Return a dynamic proxy instance</p>
     * <p>Due to CGlib, this target must be not final!</p>
     *
     * @param target  <p>要被代理的目标对象</p>
     *                <p>Target object to proxy</p>
     * @param interceptors <p>拦截器数组</p><p>Interceptors</p>
     * @return <p>返回实际产生的动态代理类对象</p><p>Return a dynamic proxy instance</p>
     * @see AbstractInvocationHandler <p>抽象的调用处理器</p><p>Abstarct invocation handler</p>
     * @see InterceptorInvocationHandler <p>拦截器使用的调用处理器</p><p>Interceptor invocation handler</p>
     */
    public static Object wrap(Object target, Interceptor[] interceptors) {
        return make(target, new InterceptorInvocationHandler(target, interceptors)).create();
    }

    // 根据目标对象和调用处理器实例化一个 Enhancer 对象
    // Init a Enhancer object with given target and handler
    private static Enhancer make(Object target, InvocationHandler handler) {

        // 设置父类 Class，这样子类以多态的形式实现父类的功能
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassHelper.classOf(target));
        enhancer.setCallback(handler);
        return enhancer;
    }
}
