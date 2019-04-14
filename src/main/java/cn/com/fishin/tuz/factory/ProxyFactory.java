package cn.com.fishin.tuz.factory;

import cn.com.fishin.tuz.helper.ClassHelper;

import java.lang.reflect.InvocationHandler;
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

    // 返回实际产生的动态代理类对象
    public static Object wrap(Object target, InvocationHandler handler) {
        return Proxy.newProxyInstance(ClassHelper.classLoaderOf(target), ClassHelper.interfacesOf(target), handler);
    }
}
