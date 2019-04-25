package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.helper.LogHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>整个项目的核心类</p>
 * <p>The core of the whole project</p>
 *
 * <p><strong>Example 1: </strong></p>
 * <p>
 * 加载资源文件
 * API: load(Loadable resource) throws IOException
 * test 是命名空间，后面一个是资源加载器
 * "test.properties" 文件中有一个属性：number=16
 * </p>
 *
 * <pre>
 * Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
 * </pre>
 *
 * <p>
 * 当然，你也可以不指定命名空间，内部会自动生成一个命名空间
 * 不过，为了性能和正确性，还是建议您使用自定义的命名空间
 * 当你不指定命名空间时，就会使用文件名作为命名空间
 * </p>
 * Tuz.load(new ClasspathPropertiesLoader("test2.properties"));
 *
 * <p>
 * 或者，您也可以使用另外一个加载器去加载文件系统中的资源文件
 * Tuz.load(new FileSystemPropertiesLoader("Z:/test.properties", "test"));
 * </p>
 *
 * <p>
 * 下面就是激动人心的时刻了！您可以在任意地方使用您的资源！
 * 上面提到，初始化的资源文件中有一个属性：number=16
 * 您可以在任意地方使用这个资源，像这样：
 * API: use(String key, String namespace)
 * 其中上面的代码中 number 是资源名字，test 是命名空间
 * </p>
 *
 * <pre>
 * String number = Tuz.use("number", "test"); // ===&gt; 返回 16
 * System.out.println(number);
 * </pre>
 *
 * <p>
 * 同样，您可以不指定命名空间，但是这不被推荐
 * 具体原因请看 cn.com.fishin.tuz.core.Tuz.use(java.lang.String)
 * String number = Tuz.use("number"); // ===&gt; 返回 16
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
 * Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
 *
 * // 直接获取实现类，而不用注入实现类的细节
 * xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * service.say("Hello, Tuz!");
 * </pre>
 *
 * <p>
 * 同样的，你可以不指定命名空间，但是，真的不推荐！！！
 * 注意这里的 xxxService 在配置文件中的 key 就是 xxxService
 * //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
 * //xxxService service = DiPlugin.useInstance(xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 14:20:32</p>
 */
public class Tuz {

    // 存储所有资源文件，每一个元素都是一个资源文件
    // Store all resources, every element is a resource
    private static final Map<String, Map<String, String>> resources = new HashMap<>();

    // 原本使用 java.util.ConcurrentHashMap 来保证存储资源空间的线程安全
    // 但是下面的操作有多步，而且需要保证原子性，所以需要加锁，这时候使用 HashMap 性能会更高
    // At first, java.util.ConcurrentHashMap is used to provide thread safe
    // However, more than one step need atomicity, so lock is needing
    // This time HashMap is faster than ConcurrentHashMap
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();

    // 全局配置信息，拥有一个默认配置
    // Global Config, it has a default config
    private static TuzConfig config = new TuzConfig();

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
        writeLock.lock();
        try {
            resources.put(resource.namespace(), resource.load());
        } finally {
            writeLock.unlock();
        }

        // 日志输出
        LogHelper.debug("Namespace [" + resource.namespace() + "] is loaded!");
    }

    /**
     * <p>卸载一个资源文件</p>
     * <p>UnLoad a resource</p>
     *
     * @param resource <p>要被卸载的资源文件</p>
     *                 <p>The resource to be unloaded</p>
     */
    public static void unLoad(Loadable resource) {
        unLoad(resource.namespace());
    }

    /**
     * <p>卸载一个命名空间</p>
     * <p>UnLoad a namespace</p>
     *
     * @param namespace <p>要被卸载的命名空间</p>
     *                  <p>The namespace to be unloaded</p>
     */
    public static void unLoad(String namespace) {
        writeLock.lock();
        try {
            resources.remove(namespace);
        } finally {
            writeLock.unlock();
        }

        // 日志输出
        LogHelper.debug("Namespace [" + namespace + "] is unloaded!");
    }

    /**
     * <p>重新载入一个资源文件</p>
     * <p>ReLoad a resource</p>
     *
     * @param resource <p>要被重新载入的资源文件</p>
     *                 <p>The resource to be reloaded</p>
     * @throws IOException <p>找不到资源文件就会抛出这个异常</p>
     *                     <p>The resource is not found</p>
     */
    public static void reLoad(Loadable resource) throws IOException {
        writeLock.lock();
        try {
            resources.put(resource.namespace(), resource.load());
        } finally {
            writeLock.unlock();
        }

        // 日志输出
        LogHelper.debug("Namespace [" + resource.namespace() + "] is reloaded!");
    }

    /**
     * <p>获取指定 namespace 的资源值</p>
     * <p>Fetch the value of the namespace's key</p>
     *
     * @param key       <p>指定资源值的 key</p>
     *                  <p>The key of the value</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     * @return <p>返回获取到的资源值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String use(String key, String namespace) {
        return useGracefully(key, namespace, null);
    }

    /**
     * <p>移除指定的 namespace 的资源值</p>
     * <p>Remove the value of the namespace's key</p>
     *
     * @param key       <p>指定资源值的 key</p>
     *                  <p>The key of the value</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     * @return <p>返回移除前的资源值，找不到返回 null</p>
     * <p>Return the value of the removed key, null if not found</p>
     */
    public static String unUse(String key, String namespace) {
        writeLock.lock();
        try {
            return resources.containsKey(namespace) ? resources.get(namespace).remove(key) : null;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * <p>往指定的 namespace 中添加新的资源值</p>
     * <p>Add one value to the pointed namespace</p>
     *
     * @param resource  <p>要被添加的资源值</p>
     *                  <p>The resource to be appended</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     */
    public static void appendResource(Map<String, String> resource, String namespace) {
        writeLock.lock();
        try {
            if (resources.containsKey(namespace)) {
                resources.get(namespace).putAll(resource);

                // 日志输出
                LogHelper.debug("Resource " + resource + " is appended to namespace [ " + namespace + " ] !");
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * <p>往指定的 namespace 中添加新的资源值</p>
     * <p>Add one value to the pointed namespace</p>
     *
     * @param key  <p>要被添加的资源的 key</p>
     *                  <p>The key of resource to be appended</p>
     * @param value <p>要被添加的资源值</p>、
     *              <p>The resource to be appended</p>
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     */
    public static void appendResource(String key, String value, String namespace) {
        writeLock.lock();
        try {
            if (resources.containsKey(namespace)) {
                resources.get(namespace).put(key, value);

                // 日志输出
                LogHelper.debug("Resource {" + key + "=" + value + "} is appended to namespace [ " + namespace + " ] !");
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>Fetch the value of the key</p>
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
        readLock.lock();
        try {
            return resources.containsKey(namespace) ? resources.get(namespace).get(key) : defaultValue;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>
     * <strong>强烈注意：</strong>
     * 如果不指定命名空间，当多个配置文件中的 key 值一样的时候，
     * 这个方法无法保证返回你需要的那个 key 值对应的 value！！
     * 由于哈希算法存在随机性，所以有可能返回另外一个值！！
     * 因此，不管是出于性能还是运行的正常性，强烈推荐使用自定义命名空间！:)
     * </p>
     * <p>Fetch the value of the key</p>
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
     * <strong>强烈注意：</strong>
     * 如果不指定命名空间，当多个配置文件中的 key 值一样的时候，
     * 这个方法无法保证返回你需要的那个 key 值对应的 value！！
     * 由于哈希算法存在随机性，所以有可能返回另外一个值！！
     * 因此，不管是出于性能还是运行的正常性，强烈推荐使用自定义命名空间！:)
     * </p>
     * <p>Fetch the value of the key</p>
     * <p>
     * <strong>Notice: </strong>
     * If you do not appoint a namespace, and you have some same key
     * in some resources, then you may get a wrong value of the key!
     * Due to hash, this gonna be a possible thing!
     * So, listen to me, you are recommended to use a namespace!
     * That's my boy:)
     * </p>
     *
     * @param key          <p>指定的 key</p>
     *                     <p>The key of the value</p>
     * @param defaultValue <p>找不到返回这个值</p>
     *                     <p>Return this value if not found</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public static String useGracefully(String key, String defaultValue) {

        readLock.lock();
        try {
            // 由于没有指定命名空间，所以需要遍历所有命名空间
            for (String namespace : resources.keySet()) {

                Map<String, String> resource = resources.get(namespace);
                if (resource.containsKey(key)) {
                    return resource.get(key);
                }
            }

            // 找不到返回 null
            return defaultValue;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * <p>重新载入默认配置</p>
     * <p>当你更改了 Tuz 的配置之后，希望回到最原始的默认配置</p>
     * <p>另外，这里也可以解决一下一些问题，比如，在使用 NetPlugin 的时候，</p>
     * <p>由于 config 的加载早于 load 方法执行，所以会导致默认配置有些找不到，</p>
     * <p>当你也遇到类似的问题时，可以尝试在执行 load 方法之后，执行一下这个方法！</p>
     * <p>Reload default config</p>
     * <p>If change config of Tuz, and you want it back to default.</p>
     * <p>Also, some problems happen because load method is back of initializing config.</p>
     * <p>So, if you have some problems with this config,
     * try to invoke this method after invoking load()</p>
     */
    public static void init() {
        writeLock.lock();
        try {
            config = new TuzConfig();
        } finally {
            writeLock.unlock();
        }
    }
}
