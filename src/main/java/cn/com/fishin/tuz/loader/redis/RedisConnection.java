package cn.com.fishin.tuz.loader.redis;

import java.util.Map;

/**
 * <p>这个接口代表一次 Redis 连接</p>
 * <p>这个接口存在的意义在于：</p>
 * <p>
 *     你可以使用我默认的 redis 加载器，但是我是用 Jedis 的，
 *     如果你不喜欢使用它，就需要自己自定义一个加载器，尽管你只是不喜欢其中一个小零件。
 *     所以我将 redis 连接抽象出来，这样你只需要替换掉我默认的 redis 实现即可
 * </p>
 * <p>This interface represents one time of redis connection</p>
 * <p>The meaning of it is :</p>
 * <p>
 *     You may not like my default redis loader because it is using Jedis,
 *     then you may rewrite redis loader by yourself, although it is a small
 *     class... So I take it out and make it abstract, then you just implement it
 * </p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 11:51:32</p>
 */
public interface RedisConnection<K, V> {

    // 使用例子，这里以 Jedis 为例：
    // For example:
    /*
    public class DefaultRedisConnection implements RedisConnection<String, String> {

        // Jedis 客户端
        // Jedis client
        private Jedis jedis = null;

        public DefaultRedisConnection(Jedis jedis) {
            this.jedis = jedis;
        }

        @Override
        public String get(String key) {
            return jedis.get(key);
        }

        @Override
        public boolean containsKey(String key) {
            return jedis.exists(key);
        }

        @Override
        public String put(String key, String value) {
            jedis.set(key, value);
            return value;
        }

        @Override
        public void putAll(Map<? extends String, ? extends String> map) {
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
                pipeline.set(entry.getKey(), entry.getValue());
            }

            pipeline.sync(); // 执行批量插入
            pipeline.close(); // 关闭管道
        }

        @Override
        public String remove(String key) {
            jedis.del(key);
            return null;
        }

        @Override
        public void close() {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    */

    /**
     * <p>根据 key 获得一个值</p>
     * <p>Get a value by given key</p>
     *
     * @param key <p>给定的 key</p><p>Given key</p>
     * @return <p>返回获取到的值</p><p>Return result</p>
     */
    V get(K key);

    /**
     * <p>判断一个 key 是否存在</p>
     * <p>Judge the key that exists or not</p>
     *
     * @param key <p>要被判断的 key</p><p>Given key</p>
     * @return <p>true 存在，反之不存在</p><p>true if exists, false if not exists</p>
     */
    boolean containsKey(K key);

    /**
     * <p>用给定的 key 和 value 保存一个值</p>
     * <p>Put an entry by given key and value</p>
     *
     * @param key <p>给定的 key</p><p>Given key</p>
     * @param value <p>给定的 value</p><p>Given value</p>
     * @return <p>可能是返回保存的值？</p><p>May be the given value?</p>
     */
    V put(K key, V value);

    /**
     * <p>将一个 Map 保存到 redis</p>
     * <p>Put the whole map to redis</p>
     *
     * @param map <p>要被保存的 map</p><p>The given map</p>
     */
    void putAll(Map<? extends K, ? extends V> map);

    /**
     * <p>删除指定 key 的数据</p>
     * <p>Remove the given value of given key</p>
     *
     * @param key <p>指定的 key</p><p>Given key</p>
     * @return <p>返回这个被移除的值</p><p>Return the removed value</p>
     */
    V remove(K key);

    /**
     * <p>断开连接</p>
     * <p>Close the connection</p>
     */
    void close();
}
