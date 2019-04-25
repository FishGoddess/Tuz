package cn.com.fishin.tuz.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>这是 Redis 资源管理器使用的 Map</p>
 * <p>
 *     <strong>注意：</strong>
 *     <p>这个类的方法始终会通过网络去获取 redis 主机的资源</p>
 * </p>
 * <p>This is for redis resource manager</p>
 * <p>
 *     <strong>Notice:</strong>
 *     <p>The method of this class will get resource by internet to reach redis host</p>
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/25 17:47:19</p>
 */
public class RedisEntryMap<K, V> extends HashMap<K, V> {

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);
    }

    @Override
    public V remove(Object key) {
        return super.remove(key);
    }

    @Override
    public String toString() {
        return "RedisEntryMap";
    }
}
