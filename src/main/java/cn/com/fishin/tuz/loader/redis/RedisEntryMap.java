package cn.com.fishin.tuz.loader.redis;

import cn.com.fishin.tuz.entity.SimpleLruCache;

import java.util.*;

/**
 * <p>这是 Redis 资源管理器使用的 Map</p>
 * <p>
 *     <strong>注意：</strong>
 *     <p>这个类的方法始终会通过网络去获取 redis 主机的资源
 * </p>
 * <p>This is for redis resource manager</p>
 * <p>
 *     <strong>Notice:</strong>
 *     <p>The method of this class will get resource by internet to reach redis host
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/25 17:47:19</p>
 */
public class RedisEntryMap implements Map<String, String> {

    // Redis 连接
    private RedisConnection<String, String> connection = null;

    // 加入一级缓存，将得到的值暂时缓存，可以大幅度提高短时间大量读取的性能
    // 缓存策略为：
    // 1. 当执行写操作时清空对应的 key 缓存
    // 2. 每隔一定的时间清除不常用的 key 缓存
    // 默认缓存维持的是访问顺序，但由于加入了缓存，所以下面所有方法都会是弱一致性的，也就是不能保证百分百的实时准确
    private final Map<String, String> CACHE = new SimpleLruCache<>();

    /**
     * <p>使用一个 redis 连接构建这个 map</p>
     * <p>Use one redis connection to initialize this map</p>
     *
     * @param connection <p>Redis 连接</p><p>Redis connection</p>
     */
    public RedisEntryMap(RedisConnection<String, String> connection) {
        this.connection = connection;
    }

    @Override
    public boolean containsKey(Object key) {
        return connection.containsKey((String) key);
    }

    @Override
    public String get(Object key) {
        return connection.get((String) key);
    }

    @Override
    public String put(String key, String value) {
        return connection.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return connection.remove((String) key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putAll(Map map) {
        connection.putAll(map);
    }

    @Override
    public void clear() {
        // 回收资源，这个方法会在资源被卸载时使用
        connection.close();
    }

    // 以上是有使用到的方法
    // #######################################################
    // 以下是没有使用到的方法

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> values() {
        return Collections.emptyList();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return Collections.emptySet();
    }
}
