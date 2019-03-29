package cn.com.fishin.tuz.plugin;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.helper.ClassHelper;
import cn.com.fishin.tuz.helper.LogHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>依赖注入插件</p>
 * <p>使用过 Spring 的一定知道 DI 和 IOC，这个插件功能类似，但是更加简易化操作，更方便</p>
 * <p>
 *     <strong>注意：</strong>
 *     在使用这个插件之前必须保证 Tuz 已经初始化！
 * </p>
 * <p>Dependency inject plugin</p>
 * <p>If you have used Spring framework, you must know DI and IOC</p>
 * <p>This plugin can do the same work as Spring do, but more easy</p>
 * <p>
 *     <strong>Notice: </strong>
 *     Init Tuz before using this plugin!
 * </p>
 *
 * <p>
 * 传统的方式使用接口：
 * xxxService service = new xxxServiceImpl();
 * 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
 * 而使用 Tuz 可以轻松做到解耦，请看下面：
 * </p>
 *
 * <pre>
 * Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
 *
 * // 直接获取实现类，而不用注入实现类的细节
 * xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * service.say("Hello, Tuz!");
 * </pre>
 *
 * <p>
 * 同样的，你可以不指定命名空间，但是，真的不推荐！！！
 * //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
 * //xxxService service = DiPlugin.useInstance("xxxService", xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/29 12:50:46</p>
 */
public class DiPlugin {

    // 存储所有实例化的类对象，每一个元素都是一个对象
    // Store all instances, every element is a instance
    private static final Map<String, Object> instances = new ConcurrentHashMap<>(32);

    // 保证并发安全
    // Keep concurrent safe
    private static final Lock newInstanceLock = new ReentrantLock();

    /**
     * <p>得到类实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>Get class instance</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     *
     * @param key       <p>根据这个 key 找到类的全名</p>
     *                  <p>Find the class by this key</p>
     * @param classType <p>类对象的实际类类型</p>
     *                  <p>The real type of class instance</p>
     * @param <T>       <p>实际类型</p>
     *                  <p>Real type</p>
     * @return <p>返回得到的类实例</p><p>Return class instance</p>
     */
    public static <T> T useInstance(String key, Class<T> classType) {

        // 首先判断配置，是单例模式还是多例模式
        if (Tuz.getConfig().isSingleton()) {
            // 单例模式
            return singletonInstance(Tuz.useGracefully(key, ""), classType);
        }

        // 多例模式，直接每一次都新构造一个对象
        return ClassHelper.newInstance(Tuz.useGracefully(key, ""), classType);
    }

    /**
     * <p>得到类实例</p>
     * <p>这里会根据 key 值获取 value，并且利用反射生成实例对象</p>
     * <p>Get class instance</p>
     * <p>Use reflect to instance a new object with given value of key</p>
     *
     * @param key       <p>根据这个 key 找到类的全名</p>
     *                  <p>Find the class by this key</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     * @param classType <p>类对象的实际类类型</p>
     *                  <p>The real type of class instance</p>
     * @param <T>       <p>实际类型</p>
     *                  <p>Real type</p>
     * @return <p>返回得到的类实例</p><p>Return class instance</p>
     */
    public static <T> T useInstance(String key, String namespace, Class<T> classType) {

        // 首先判断配置，是单例模式还是多例模式
        if (Tuz.getConfig().isSingleton()) {
            // 单例模式
            return singletonInstance(Tuz.useGracefully(key, namespace, ""), classType);
        }

        // 多例模式，直接每一次都新构造一个对象
        return ClassHelper.newInstance(Tuz.useGracefully(key, namespace, ""), classType);
    }

    // 单例模式生成类实例，并缓存起来
    // Singleton instance, then cache
    @SuppressWarnings("unchecked")
    private static <T> T singletonInstance(String className, Class<T> classType) {
        newInstanceLock.lock();
        try {
            if (!instances.containsKey(classType.getName())) {

                // 没有生成过，生成并缓存
                T t = ClassHelper.newInstance(className, classType);
                instances.put(classType.getName(), t);
            }
        } finally {
            newInstanceLock.unlock();
        }

        // 日志输出
        LogHelper.info("Instance created ===> " + classType.getName());

        // 直接返回，因为上面已经保证生成过了
        return (T) instances.get(classType.getName());
    }
}
