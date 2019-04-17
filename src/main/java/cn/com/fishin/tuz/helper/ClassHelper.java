package cn.com.fishin.tuz.helper;

import java.lang.reflect.Modifier;

/**
 * <p>类帮助器</p>
 * <p>一些操作类的方法</p>
 * <p>Class helper</p>
 * <p>Some methods that used to operate class</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 22:31:45</p>
 */
public class ClassHelper {

    /**
     * <p>加载一个类，并返回一个实例化对象</p>
     * <p>Load a class, and return a instance of this class</p>
     *
     * @param className <p>要被加载的类名</p>
     *                  <p>Name of loaded class</p>
     *
     * @return <p>返回这个被加载类的实例对象</p>
     * <p>Return the instance of loaded class</p>
     *
     * @throws ClassNotFoundException <p>如果找不到这个类</p><p>If the class is not found</p>
     * @throws IllegalAccessException <p>如果这个类不允许访问</p><p>If the class is not allowed to access</p>
     * @throws InstantiationException <p>如果这个类没有默认构造函数</p><p>If the class do not has default constructor</p>
     */
    public static Object newInstance(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        // 加载类
        Class clazz = Class.forName(className);
        return clazz.newInstance();
    }

    /**
     * <p>加载一个类，并返回一个实例化对象</p>
     * <p>出现异常就会返回 null</p>
     * <p>Load a class, and return a instance of this class</p>
     * <p>If exception happened, return null object</p>
     *
     * @param className <p>要被加载的类名</p>
     *                  <p>Name of loaded class</p>
     *
     * @param classType <p>类的实际类型，用于类型转换</p>
     *                  <p>The type of class, used to cast</p>
     * @param <T> <p>实际类型</p>
     *            <p>Real type</p>
     * @return <p>返回这个被加载类的实例对象</p>
     * <p>Return the instance of loaded class</p>
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className, Class<T> classType) {
        try {
            return (T) newInstance(className);
        } catch (ClassNotFoundException e) {
            LogHelper.error("The class is not found", e);
        } catch (IllegalAccessException e) {
            LogHelper.error("the class is not allowed to access", e);
        } catch (InstantiationException e) {
            LogHelper.error("the class do not has default constructor", e);
        }

        // 出现异常就会返回 null
        return null;
    }

    /**
     * <p>获得这个对象的类的类型</p>
     * <p>实际上它就是在内部调用 obj.getClass() 来获取类的类型的</p>
     * <p>这个方法的目的在于，提供<strong>更好的代码可读性</strong>和<strong>代码意义</strong></p>
     * <p>Get the class of this object</p>
     * <p>In fact, it invokes obj.getClass() to get what it needs</p>
     * <p>This method is to get more human-being and mean of the code</p>
     *
     * @see java.lang.Object#getClass()
     *
     * @param obj <p>想获得 class 的对象</p>
     *            <p>The target object</p>
     *
     * @return <p>返回这个对象的类的类型</p><p>Return the class of this object</p>
     */
    public static Class<?> classOf(Object obj) {
        return obj.getClass();
    }

    /**
     * <p>获得这个对象的类加载器</p>
     * <p>实际上它就是在内部调用 classOf(obj).getClassLoader() 来获取类的类加载器的</p>
     * <p>这个方法的目的在于，提供<strong>更好的代码可读性</strong>和<strong>代码意义</strong></p>
     * <p>Get the classloader of this object</p>
     * <p>In fact, it invokes classOf(obj).getClassLoader() to get what it needs</p>
     * <p>This method is to get more <strong>human-being</strong> and <strong>mean of the code</strong></p>
     *
     * @param obj <p>想获得类加载器的对象</p>
     *            <p>The target object</p>
     *
     * @return <p>返回这个对象的类加载器</p><p>Return the classloader of this object</p>
     *
     * @see Class#getClassLoader()
     * @see ClassHelper#classOf(java.lang.Object)
     */
    public static ClassLoader classLoaderOf(Object obj) {
        return classOf(obj).getClassLoader();
    }

    /**
     * <p>获得这个对象实现的所有接口</p>
     * <p>实际上它就是在内部调用 classOf(obj).getInterfaces() 来获取类的所有接口的</p>
     * <p>这个方法的目的在于，提供<strong>更好的代码可读性</strong>和<strong>代码意义</strong></p>
     * <p>Get the interfaces of this object</p>
     * <p>In fact, it invokes classOf(obj).getClassLoader() to get what it needs</p>
     * <p>This method is to get more <strong>human-being</strong> and <strong>mean of the code</strong></p>
     *
     * @param obj <p>想获得实现的所有接口的对象</p>
     *            <p>The target object</p>
     *
     * @return <p>返回这个对象实现的所有接口</p><p>Return the interfaces of this object</p>
     * @see Class#getInterfaces()
     * @see ClassHelper#classOf(java.lang.Object)
     */
    public static Class<?>[] interfacesOf(Object obj) {
        return classOf(obj).getInterfaces();
    }

    /**
     * <p>判断一个对象是否有实现接口</p>
     * <p>If target object has interface, return true</p>
     *
     * @param obj <p>被判断的对象</p><p>The target object</p>
     * @return <p>true 如果有实现接口，反之 false</p><p>true if target has interfaces, false if not</p>
     */
    public static boolean hasInterface(Object obj) {
        return interfacesOf(obj).length > 0;
    }

    // 判断一个类是否是 final 修饰的，也就是否可以被继承
    public static boolean isFinal(Object obj) {
        return Modifier.isFinal(classOf(obj).getModifiers());
    }
}
