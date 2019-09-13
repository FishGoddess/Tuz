package cn.com.fishin.tuz.loader.redis;

import cn.com.fishin.tuz.core.Loader;
import java.util.Map;

/**
 * <p>Redis 资源加载器</p>
 * <p>这个加载器并不会把 redis 上的资源拷贝到本机内存，</p>
 * <p>而是采用延迟加载的形式，就是每一次使用都去加载</p>
 * <p>Redis resource loader</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 12:22:38</p>
 */
public class RedisLoader implements Loader {

    // 命名空间
    // Namespace
    private String namespace = null;

    // 中间 map
    // Middle map
    private Map<String, String> map = null;

    @Override
    public String namespace() {
        return namespace;
    }

    @Override
    public Map<String, String> load() throws Throwable {
        return map;
    }

    /**
     * <p>使用命名空间和 redis 连接来初始化</p>
     * <p>Use namespace and redis connection to initialize this loader</p>
     *
     * @param namespace  <p>命名空间</p><p>namespace</p>
     * @param connection <p>connection 资源</p><p>connection resource</p>
     */
    public RedisLoader(String namespace, RedisConnection<String, String> connection) {
        this.namespace = namespace;
        this.map = mapOf(connection);
    }

    // 使用 connection 构建 redis map
    // Use connection to redis map
    private static Map<String, String> mapOf(RedisConnection<String, String> connection) {
        return new RedisEntryMap(connection);
    }
}
