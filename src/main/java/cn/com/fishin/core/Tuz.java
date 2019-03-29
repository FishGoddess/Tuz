package cn.com.fishin.core;

import cn.com.fishin.helper.ClassHelper;
import cn.com.fishin.helper.LogHelper;
import cn.com.fishin.helper.NamespaceHelper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>整个项目的核心类</p>
 * <p>The core of the whole project</p>
 *
 * <p><strong>Example 1: </strong></p>
 * <p>
 * 加载资源文件
 * API: load(String namespace, Loadable resource) throws IOException
 * test 是命名空间，后面一个是资源加载器
 * "test.properties" 文件中有一个属性：number=16
 * </p>
 *
 * <pre>
 * Tuz.load("test", new ClasspathPropertiesLoader("test.properties"));
 * </pre>
 *
 * <p>
 * 当然，你也可以不指定命名空间，内部会自动生成一个命名空间
 * 不过，为了性能和正确性，还是建议您使用自定义的命名空间
 * Tuz.load(new ClasspathPropertiesLoader("test2.properties"));
 * <p>
 * 或者，您也可以使用另外一个加载器去加载文件系统中的资源文件
 * Tuz.load("test", new FileSystemPropertiesLoader("Z:/test.properties"));
 * <p>
 * 下面就是激动人心的时刻了！您可以在任意地方使用您的资源！
 * 上面提到，初始化的资源文件中有一个属性：number=16
 * 您可以在任意地方使用这个资源，像这样：
 * API: use(String key, String namespace)
 * 其中上面的代码中 number 是资源名字，test 是命名空间
 * </p>
 *
 * <pre>
 * String number = Tuz.use("number", "test"); // ===> 返回 16
 * System.out.println(number);
 * </pre>
 *
 * <p>
 * 同样，您可以不指定命名空间，但是这不被推荐
 * 具体原因请看 cn.com.fishin.core.Tuz.use(java.lang.String)
 * String number = Tuz.use("number"); // ===> 返回 16
 * </p>
 *
 * <p>Example 2: </p>
 * <p>
 * 传统的方式使用接口：
 * xxxService service = new xxxServiceImpl();
 * 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
 * 而使用 Tuz 可以轻松做到解耦，请看下面：
 * </p>
 *
 * <pre>
 * Tuz.load("test", new ClasspathPropertiesLoader("test.properties"));
 *
 * // 直接获取实现类，而不用注入实现类的细节
 * xxxService service = Tuz.useInstance("xxxService", "test", xxxService.class);
 * service.say("Hello, Tuz!");
 * </pre>
 *
 * <p>
 * 同样的，你可以不指定命名空间，但是，真的不推荐！！！
 * //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
 * //xxxService service = Tuz.useInstance("xxxService", xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 14:20:32
 */
public class Tuz {

    // 存储所有资源文件，每一个元素都是一个资源文件
    // Store all resources, every element is a resource
    private static final Map<String, Map<String, String>> resources = new ConcurrentHashMap<>();

    // 存储所有实例化的类对象，每一个元素都是一个对象
    // Store all instances, every element is a instance
    private static final Map<String, Object> instances = new ConcurrentHashMap<>(32);

    // 全局配置信息，拥有一个默认配置
    // Global Config, it has a default config
    private static TuzConfig config = new TuzConfig();

    // 保证并发安全
    // Keep concurrent safe
    private static final Lock newInstanceLock = new ReentrantLock();

    /**
     * <p>获取全局配置</p>
     * <p>Get global setting</p>
     *
     * @return <p>全局配置信息</p><p>Global config</p>
     */
    public static TuzConfig getConfig() {
        return config;
    }

    /**
     * <p>设置全局配置</p>
     * <p>Set global setting</p>
     *
     * @param config <p>全局配置信息</p>
     *               <p>Global config</p>
     */
    public static void setConfig(TuzConfig config) {
        Tuz.config = config;
    }

    /**
     * <p>加载一个资源文件</p>
     * <p>Load a resource</p>
     *
     * @param resource <p>要被加载的资源文件</p>
     *                 <p>The resource to be loaded</p>
     * @throws IOException <p>找不到资源文件就会抛出这个异常</p>
     *                     <p>The resource is not found</p>
     */
    public static void load(Loadable resource) throws IOException {
        resources.put(resource.namespace(), resource.load());

        // 日志输出
        LogHelper.debug("Namespace [" + resource.namespace() + "] loaded resource added!");
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>fetch the value of the key</p>
     *
     * @param key       <p>指定的 key</p>
     *                  <p>The key of the value</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String use(String key, String namespace) {
        return useGracefully(key, namespace, null);
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>fetch the value of the key</p>
     *
     * @param key          <p>指定的 key</p>
     *                     <p>The key of the value</p>
     * @param namespace    <p>指定的命名空间，用于区分不同的资源文件</p>
     *                     <p>Appointed namespace to different resource</p>
     * @param defaultValue <p>如果找不到这个 key 返回的默认值</p>
     *                     <p>If the key is not found, return defaultValue</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String useGracefully(String key, String namespace, String defaultValue) {
        return resources.containsKey(namespace) ? resources.get(namespace).get(key) : defaultValue;
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>
     * <string>强烈注意：</string>
     * 如果不指定命名空间，当多个配置文件中的 key 值一样的时候，
     * 这个方法无法保证返回你需要的那个 key 值对应的 value！！
     * 由于哈希算法存在随机性，所以有可能返回另外一个值！！
     * 因此，不管是出于性能还是运行的正常性，强烈推荐使用自定义命名空间！:)
     * </p>
     * <p>fetch the value of the key</p>
     * <p>
     * <strong>Notice: </strong>
     * If you do not appoint a namespace, and you have some same key
     * in some resources, then you may get a wrong value of the key!
     * Due to hash, this gonna be a possible thing!
     * So, listen to me, you are recommended to use a namespace!
     * That's my boy:)
     * </p>
     *
     * @param key <p>指定的 key</p>
     *            <p>The key of the value</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String use(String key) {
        return useGracefully(key, null);
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>
     * <string>强烈注意：</string>
     * 如果不指定命名空间，当多个配置文件中的 key 值一样的时候，
     * 这个方法无法保证返回你需要的那个 key 值对应的 value！！
     * 由于哈希算法存在随机性，所以有可能返回另外一个值！！
     * 因此，不管是出于性能还是运行的正常性，强烈推荐使用自定义命名空间！:)
     * </p>
     * <p>fetch the value of the key</p>
     * <p>
     * <strong>Notice: </strong>
     * If you do not appoint a namespace, and you have some same key
     * in some resources, then you may get a wrong value of the key!
     * Due to hash, this gonna be a possible thing!
     * So, listen to me, you are recommended to use a namespace!
     * That's my boy:)
     * </p>
     *
     * @param key <p>指定的 key</p>
     *            <p>The key of the value</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String useGracefully(String key, String defaultValue) {

        // 由于没有指定命名空间，所以需要遍历所有命名空间
        for (String namespace : resources.keySet()) {

            Map<String, String> resource = resources.get(namespace);
            if (resource.containsKey(key)) {
                return resource.get(key);
            }
        }

        // 找不到返回 null
        return defaultValue;
    }

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
        if (config.isSingleton()) {
            // 单例模式
            return singletonInstance(useGracefully(key, ""), classType);
        }

        // 多例模式，直接每一次都新构造一个对象
        return ClassHelper.newInstance(useGracefully(key, ""), classType);
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
        if (config.isSingleton()) {
            // 单例模式
            return singletonInstance(useGracefully(key, namespace, ""), classType);
        }

        // 多例模式，直接每一次都新构造一个对象
        return ClassHelper.newInstance(useGracefully(key, namespace, ""), classType);
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

                // 日志输出
                LogHelper.info("Instance created ===> " + classType.getName());
            }
        } finally {
            newInstanceLock.unlock();
        }

        // 直接返回，因为上面已经保证生成过了
        return (T) instances.get(classType.getName());
    }
}
