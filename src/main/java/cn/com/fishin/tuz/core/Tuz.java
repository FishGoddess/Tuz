package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.helper.LogHelper;

import java.util.Collections;
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
 * Tuz.instance().load(new ClasspathPropertiesLoader("test.properties", "test"));
 * </pre>
 *
 * <p>
 * 当然，你也可以不指定命名空间，内部会自动生成一个命名空间
 * 不过，为了性能和正确性，还是建议您使用自定义的命名空间
 * 当你不指定命名空间时，就会使用文件名作为命名空间
 * </p>
 * Tuz.instance().load(new ClasspathPropertiesLoader("test2.properties"));
 *
 * <p>
 * 或者，您也可以使用另外一个加载器去加载文件系统中的资源文件
 * Tuz.instance().load(new FileSystemPropertiesLoader("Z:/test.properties", "test"));
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
 * String number = Tuz.instance().use("number", "test"); // ===&gt; 返回 16
 * System.out.println(number);
 * </pre>
 *
 * <p>
 * 同样，您可以不指定命名空间，但是这不被推荐
 * 具体原因请看 cn.com.fishin.tuz.core.Tuz.use(java.lang.String)
 * String number = Tuz.instance().use("number"); // ===&gt; 返回 16
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
 * Tuz.instance().load(new ClasspathPropertiesLoader("test.properties", "test"));
 *
 * // 直接获取实现类，而不用注入实现类的细节
 * xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * service.say("Hello, Tuz!");
 * </pre>
 *
 * <p>
 * 同样的，你可以不指定命名空间，但是，真的不推荐！！！
 * 注意这里的 xxxService 在配置文件中的 key 就是 xxxService
 * //Tuz.instance().load(new ClasspathPropertiesLoader("test.properties"));
 * //xxxService service = DiPlugin.useInstance(xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 14:20:32</p>
 */
public final class Tuz {

    // =========================== 使用内部类作单例 =============================
    private static final class InstanceHolder {
        static final Tuz INSTANCE = new Tuz();
    }

    // 禁止外部实例化
    private Tuz() {
    }

    /**
     * <p>获取 Tuz 的实例对象，如果你需要使用 Tuz，这个方法是必须被调用的</p>
     * <p>Obtain the instance of Tuz, this method must be invoked if you use Tuz</p>
     *
     * @return <p>Tuz 实例对象</p><p>The instance of Tuz</p>
     */
    public static Tuz instance() {
        return InstanceHolder.INSTANCE;
    }
    // =======================================================================

    // ============================= 内部成员变量 ===============================
    // 存储所有资源文件，每一个元素都是一个资源文件
    // Store all resources, every element is a resource
    private final Map<String, Map<String, String>> resources = new HashMap<>();

    // 原本使用 java.util.ConcurrentHashMap 来保证存储资源空间的线程安全
    // 但是下面的操作有多步，而且需要保证原子性，所以需要加锁，这时候使用 HashMap 性能会更高
    // At first, java.util.ConcurrentHashMap is used to provide thread safe
    // However, more than one step need atomicity, so lock is needing
    // This time HashMap is faster than ConcurrentHashMap
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    // 全局配置信息，拥有一个默认配置
    // Global Config, it has a default config
    private TuzConfig config = new TuzConfig();

    /**
     * <p>获取全局配置</p>
     * <p>Get global setting</p>
     *
     * @return <p>全局配置信息</p><p>Global config</p>
     */
    public TuzConfig getConfig() {
        return this.config;
    }

    /**
     * <p>设置全局配置</p>
     * <p>Set global setting</p>
     *
     * @param config <p>全局配置信息</p>
     *               <p>Global config</p>
     */
    public void setConfig(TuzConfig config) {
        this.config = config;
    }
    // ========================================================================

    // 返回这个加载器的命名空间
    // Return the namespace of this loader
    private String namespaceOf(Loader loader) {
        return loader.namespace();
    }

    // 返回这个加载器加载的资源
    // 加载资源有可能出现异常，所以需要抛出异常
    // Return the resources loaded
    // Load may fail so exception is needed
    private Map<String, String> resourcesOf(Loader loader) throws Throwable {
        return loader.load();
    }

    // 加载这个加载器的资源
    // 加载资源有可能出现异常，所以需要抛出异常
    // Load resources of this loader
    // Load may fail so exception is needed
    private void loadInternal(Loader loader) throws Throwable {
        resources.put(namespaceOf(loader), resourcesOf(loader));
    }

    /**
     * <p>加载一个加载器里面的资源</p>
     * <p>Load resources of loader</p>
     *
     * @param loader <p>资源所在的加载器</p>
     *               <p>The loader to be loaded</p>
     * @throws Throwable <p>加载出现问题就会抛出这个异常</p>
     *                   <p>Load failed will cause exception</p>
     */
    public void load(Loader loader) throws Throwable {
        writeLock.lock();
        try {
            loadInternal(loader);
        } finally {
            writeLock.unlock();
        }

        // 日志输出
        LogHelper.debug("Loader [" + namespaceOf(loader) + "] is loaded!");
    }

    /**
     * <p>卸载一个加载器</p>
     * <p>UnLoad a loader</p>
     *
     * @param loader <p>要被卸载的加载器</p>
     *               <p>The loader to be unloaded</p>
     */
    public void unLoad(Loader loader) {
        writeLock.lock();
        try {
            resources.remove(namespaceOf(loader)); // 然后再移除资源库
        } finally {
            writeLock.unlock();
        }

        // 日志输出
        LogHelper.debug("Loader [" + namespaceOf(loader) + "] is unloaded!");
    }

    /**
     * <p>往指定的 loader 中添加新的资源</p>
     * <p>Add new resources to the pointed loader</p>
     *
     * @param targetLoader <p>指定的加载器，用于区分不同的资源</p>
     *                     <p>Appointed loader to different resource</p>
     * @param newResources <p>新添加的资源</p>
     *                     <p>The resources to be appended</p>
     */
    public void appendResource(Loader targetLoader, Map<String, String> newResources) {
        writeLock.lock();
        try {
            if (resources.containsKey(namespaceOf(targetLoader))) {
                resources.get(namespaceOf(targetLoader)).putAll(newResources);

                // 日志输出
                LogHelper.debug("Resources " + newResources +
                        " is appended to loader [ " + namespaceOf(targetLoader) + " ] !");
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * <p>往指定的 loader 中添加新的资源</p>
     * <p>Add new resources to the pointed loader</p>
     *
     * @param targetLoader <p>指定的加载器，用于区分不同的资源</p>
     *                     <p>Appointed loader to different resource</p>
     * @param key          <p>新添加的资源 key</p>
     *                     <p>The key of resource to be appended</p>
     * @param value        <p>新添加的资源 value</p>
     *                     <p>The value of resource to be appended</p>
     */
    public void appendResource(Loader targetLoader, String key, String value) {
        appendResource(targetLoader, Collections.singletonMap(key, value));
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
    public String use(String key, String namespace) {
        return useGracefully(key, namespace, null);
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
    public String use(String key) {
        return useGracefully(key, null);
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
    public String useGracefully(String key, String namespace, String defaultValue) {
        readLock.lock();
        try {
            if (resources.containsKey(namespace)) {
                return resources.get(namespace).get(key);
            }
            return defaultValue;
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
     * @param key          <p>指定的 key</p>
     *                     <p>The key of the value</p>
     * @param defaultValue <p>找不到返回这个值</p>
     *                     <p>Return this value if not found</p>
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     * <p>Return the value of the key, null if not found</p>
     */
    public String useGracefully(String key, String defaultValue) {
        readLock.lock();
        try {
            // 由于没有指定命名空间，所以需要遍历所有命名空间
            for (Map.Entry<String, Map<String, String>> entries : resources.entrySet()) {
                Map<String, String> resource = entries.getValue();
                if (resource.containsKey(key)) {
                    return resource.get(key);
                }
            }

            // 找不到返回 defaultValue
            return defaultValue;
        } finally {
            readLock.unlock();
        }
    }
}
