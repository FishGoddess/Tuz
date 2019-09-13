package cn.com.fishin.tuz.plugin;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.factory.ProxyFactory;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.helper.LogHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import net.sf.cglib.proxy.InvocationHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>动态代理插件</p>
 * <p>提供对象拦截器的功能，可以做到 AOP 的业务解耦</p>
 * <p>
 *     <strong>注意：</strong>
 *     <p>由于使用的是 CGlib，所以被代理的类不能是 final 修饰的，</p>
 *     <p>也就是说必须要可以被继承，因为 CGlib 就是使用 ASM 产生子类和多态来达到动态代理的效果的，</p>
 *     <p>另外，动态代理产生的实例是不是单例的和 Tuz 的配置有关，</p>
 *     <p>详情请参考 cn.com.fishin.tuz.core.TuzConfig.isSingleton，</p>
 *     <p>默认情况下是单例模式的，也就是说不管你创建多少代理对象都是同一个，</p>
 *     <p>如果需要多例的模式，可以修改 Tuz 的配置，调用 cn.com.fishin.tuz.core.TuzConfig.setSingleton</p>
 *
 * <p>Dynamic proxy plugin</p>
 * <p>It offers interceptor and aop functions</p>
 * <p>
 *     <strong>Notice:</strong>
 *     <p>Your target class must be not final, due to CGlib. </p>
 *     <p>That means your target class must be extendable. </p>
 *     <p>Another point to notice, this plugin generates proxy instance in two mode, </p>
 *     <p>one is singleton, and the other one is non-singleton. </p>
 *     <p>So, if you want to switch one to the other one, see cn.com.fishin.tuz.core.TuzConfig.setSingleton</p>
 *
 *
 * @see cn.com.fishin.tuz.core.TuzConfig#setSingleton
 * @see ProxyFactory#wrapByCGlib(Object, InvocationHandler)
 * @see ProxyFactory#wrapByJDK(Object, java.lang.reflect.InvocationHandler)
 * @see ProxyFactory#wrap(Object, Interceptor[])
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 23:26:03</p>
 */
public class ProxyPlugin {

    // 存储所有实例化并代理之后的类对象，每一个元素都是一个对象
    // Store all instances after proxy, every element is a instance
    private static final Map<String, Object> proxyInstances = new ConcurrentHashMap<>(32);

    // 保证并发安全
    // Keep concurrent safe
    private static final Lock newProxyInstanceLock = new ReentrantLock();

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 classType 的 getSimpleName() 去获取 key，并且利用反射生成实例对象</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given classType's simpleName</p>
     *
     * @param classType    <p>类对象的实际类类型</p>
     *                     <p>The real type of class instance</p>
     * @param singleton    <p>是否以单例模式创建代理对象实例</p>
     *                     <p>Use singleton mode to create proxy instance or not</p>
     * @param <T>          <p>实际类型</p>
     *                     <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(Class<T> classType, boolean singleton, Interceptor[] interceptors) {
        return useInstanceInternal(DiPlugin.useInstance(classType), classType, singleton, interceptors);
    }

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 classType 的 getSimpleName() 去获取 key，并且利用反射生成实例对象</p>
     * <p>注意这里的实例模式是根据 Tuz.getConfig().isSingleton() 中的设置来决定的</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given classType's simpleName</p>
     * <p>Notice that the mode creates the instance depends on Tuz.getConfig().isSingleton()</p>
     *
     * @see cn.com.fishin.tuz.core.Tuz#getConfig()
     * @see cn.com.fishin.tuz.core.TuzConfig#isSingleton()
     *
     * @param classType    <p>类对象的实际类类型</p>
     *                     <p>The real type of class instance</p>
     * @param <T>          <p>实际类型</p>
     *                     <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(Class<T> classType, Interceptor[] interceptors) {
        return useInstance(classType, Tuz.instance().getConfig().isSingleton(), interceptors);
    }

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     *
     * @param key       <p>根据这个 key 找到类的全名</p>
     *                  <p>Find the class by this key</p>
     * @param classType <p>类对象的实际类类型</p>
     *                  <p>The real type of class instance</p>
     * @param singleton    <p>是否以单例模式创建代理对象实例</p>
     *                     <p>Use singleton mode to create proxy instance or not</p>
     * @param <T>       <p>实际类型</p>
     *                  <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(String key, Class<T> classType, boolean singleton, Interceptor[] interceptors) {
        return useInstanceInternal(DiPlugin.useInstance(key, classType), classType, singleton, interceptors);
    }

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>注意这里的实例模式是根据 Tuz.getConfig().isSingleton() 中的设置来决定的</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     * <p>Notice that the mode creates the instance depends on Tuz.getConfig().isSingleton()</p>
     *
     * @see cn.com.fishin.tuz.core.Tuz#getConfig()
     * @see cn.com.fishin.tuz.core.TuzConfig#isSingleton()
     *
     * @param key          <p>根据这个 key 找到类的全名</p>
     *                     <p>Find the class by this key</p>
     * @param classType    <p>类对象的实际类类型</p>
     *                     <p>The real type of class instance</p>
     * @param <T>          <p>实际类型</p>
     *                     <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(String key, Class<T> classType, Interceptor[] interceptors) {
        return useInstance(key, classType, Tuz.instance().getConfig().isSingleton(), interceptors);
    }

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     *
     * @param key       <p>根据这个 key 找到类的全名</p>
     *                  <p>Find the class by this key</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     * @param classType <p>类对象的实际类类型</p>
     *                  <p>The real type of class instance</p>
     * @param singleton    <p>是否以单例模式创建代理对象实例</p>
     *                     <p>Use singleton mode to create proxy instance or not</p>
     * @param <T>       <p>实际类型</p>
     *                  <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(String key, String namespace, Class<T> classType, boolean singleton, Interceptor[] interceptors) {
        return useInstanceInternal(DiPlugin.useInstance(key, namespace, classType), classType, singleton, interceptors);
    }

    /**
     * <p>得到类被拦截器代理之后的实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>注意这里的实例模式是根据 Tuz.getConfig().isSingleton() 中的设置来决定的</p>
     * <p>Get class instance after intercepting</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     * <p>Notice that the mode creates the instance depends on Tuz.getConfig().isSingleton()</p>
     *
     * @see cn.com.fishin.tuz.core.Tuz#getConfig()
     * @see cn.com.fishin.tuz.core.TuzConfig#isSingleton()
     *
     * @param key          <p>根据这个 key 找到类的全名</p>
     *                     <p>Find the class by this key</p>
     * @param namespace    <p>指定的命名空间，用于区分不同的资源文件</p>
     *                     <p>Appointed namespace to different resource</p>
     * @param classType    <p>类对象的实际类类型</p>
     *                     <p>The real type of class instance</p>
     * @param <T>          <p>实际类型</p>
     *                     <p>Real type</p>
     * @param interceptors <p>要使用的多个拦截器</p>
     *                     <p>All interceptor you want to use</p>
     * @return <p>返回得到的类被拦截器代理之后的实例</p><p>Return class instance after intercepting</p>
     */
    public static <T> T useInstance(String key, String namespace, Class<T> classType, Interceptor[] interceptors) {
        return useInstance(key, namespace, classType, Tuz.instance().getConfig().isSingleton(), interceptors);
    }

    // 单例模式生成类代理实例，并缓存起来
    // Singleton instance after proxy, then cache
    @SuppressWarnings("unchecked")
    private static <T> T singletonProxyInstance(Object target, Class<T> classType, Interceptor[] interceptors) {
        newProxyInstanceLock.lock();
        try {
            if (!proxyInstances.containsKey(classType.getName())) {

                // 没有代理过，代理并缓存
                T t = (T) ProxyFactory.wrap(target, interceptors);

                // 日志输出
                LogHelper.info("Proxy instance created ===> " + classType.getName());

                // 缓存这个实例
                proxyInstances.put(classType.getName(), t);
            }
        } finally {
            newProxyInstanceLock.unlock();
        }

        // 直接返回，因为上面已经保证生成过了
        return (T) proxyInstances.get(classType.getName());
    }

    // 内部使用的代理对象获得方法
    // It is for me, not for you:)
    @SuppressWarnings("unchecked")
    private static <T> T useInstanceInternal(Object target, Class<T> classType, boolean singleton, Interceptor[] interceptors) {

        // 首先判断配置，是单例模式还是多例模式
        if (singleton) {
            // 单例模式
            return singletonProxyInstance(target, classType, interceptors);
        }

        // 日志输出
        LogHelper.info("Proxy instance created ===> " + classType.getName());

        // 不是单例模式就直接代理就好了
        return (T) ProxyFactory.wrap(target, interceptors);
    }
}
