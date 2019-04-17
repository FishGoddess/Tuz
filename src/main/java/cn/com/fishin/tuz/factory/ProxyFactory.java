package cn.com.fishin.tuz.factory;

import cn.com.fishin.tuz.handler.AbstractInvocationHandler;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.helper.ClassHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;


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
    public static Object wrapByCGlib(Object target, net.sf.cglib.proxy.InvocationHandler handler) {
        return make(target, handler).create();
    }

    /**
     * <p>返回实际产生的动态代理类对象</p>
     * <p>这里会进行目标对象判断，如果是有接口的，就直接使用 JDK 内置的动态代理</p>
     * <p>如果没有实现接口，就是用 CGlib 进行动态代理</p>
     * <p>Return a dynamic proxy instance</p>
     * <p>If target object has interface, then use JDK dynamic proxy,
     * else use CGlib to proxy
     * </p>
     *
     * @param target  <p>要被代理的目标对象</p>
     *                <p>Target object to proxy</p>
     * @param interceptors <p>拦截器数组</p><p>Interceptors</p>
     * @return <p>返回实际产生的动态代理类对象</p><p>Return a dynamic proxy instance</p>
     * @see AbstractInvocationHandler <p>抽象的调用处理器</p><p>Abstarct invocation handler</p>
     * @see InterceptorInvocationHandler <p>拦截器使用的调用处理器</p><p>Interceptor invocation handler</p>
     */
    public static Object wrap(Object target, Interceptor[] interceptors) {
        // 如果目标对象不可以被继承，使用 JDK 的动态代理
        if (ClassHelper.isFinal(target)) {
            return wrapByJDK(target, new InterceptorInvocationHandler(target, interceptors));
        }

        // 如果可以继承，使用 CGlib 动态代理
        return wrapByCGlib(target, new InterceptorInvocationHandler(target, interceptors));
    }

    // 根据目标对象和调用处理器实例化一个 Enhancer 对象
    // Init a Enhancer object with given target and handler
    private static Enhancer make(Object target, net.sf.cglib.proxy.InvocationHandler handler) {

        // 设置父类 Class，这样子类以多态的形式实现父类的功能
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassHelper.classOf(target));
        enhancer.setCallback(handler);
        return enhancer;
    }

    /**
     * <p>返回实际产生的动态代理类对象</p>
     * <p>这里是使用 JDK 进行动态代理，要求这个目标对象类必须有实现接口！</p>
     * <p>Return a dynamic proxy instance</p>
     * <p>Due to JDK, this target must be implementing interface!</p>
     *
     * @param target  <p>要被代理的目标对象</p>
     *                <p>Target object to proxy</p>
     * @param handler <p>调用处理器</p><p>Invocation handler</p>
     * @return <p>返回实际产生的动态代理类对象</p><p>Return a dynamic proxy instance</p>
     * @see AbstractInvocationHandler <p>抽象的调用处理器</p><p>Abstarct invocation handler</p>
     * @see InterceptorInvocationHandler <p>拦截器使用的调用处理器</p><p>Interceptor invocation handler</p>
     */
    public static Object wrapByJDK(Object target, java.lang.reflect.InvocationHandler handler) {
        return Proxy.newProxyInstance(ClassHelper.classLoaderOf(target), ClassHelper.interfacesOf(target), handler);
    }
}
