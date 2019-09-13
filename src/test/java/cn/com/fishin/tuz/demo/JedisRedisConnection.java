package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.loader.redis.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Map;

/**
 * <p>默认实现的 redis 连接器</p>
 * <p>使用 Jedis 作为 客户端</p>
 * <p>Default redis connection</p>
 * <p>It is implemented by Jedis</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 12:20:17</p>
 */
public class JedisRedisConnection implements RedisConnection<String, String> {

    // Jedis 资源池
    // Jedis resource pool
    private Jedis jedis = null;

    public JedisRedisConnection(Jedis jedis) {
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
