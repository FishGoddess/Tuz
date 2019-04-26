package cn.com.fishin.tuz.loader.redis;

import cn.com.fishin.tuz.helper.LogHelper;
import cn.com.fishin.tuz.template.JedisTemplate;
import redis.clients.jedis.JedisPool;
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
public class DefaultRedisConnection implements RedisConnection<String, String> {

    // Jedis 资源池
    // Jedis resource pool
    private JedisPool pool = null;

    public DefaultRedisConnection(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public String get(String key) {
        return new JedisTemplate<String>().execute(pool, jedis -> jedis.get(key));
    }

    @Override
    public boolean containsKey(String key) {
        return new JedisTemplate<Boolean>().execute(pool, jedis -> jedis.exists(key));
    }

    @Override
    public String put(String key, String value) {
        return new JedisTemplate<String>().execute(pool, jedis -> {
            jedis.set(key, value);
            return value;
        });
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> map) {
        new JedisTemplate<Void>().execute(pool, jedis -> {
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
                pipeline.set(entry.getKey(), entry.getValue());
            }

            pipeline.sync(); // 执行批量插入
            pipeline.close(); // 关闭管道
            return null;
        });
    }

    @Override
    public String remove(String key) {
        return new JedisTemplate<String>().execute(pool, jedis -> {
            jedis.del(key);
            return null;
        });
    }

    @Override
    public void close() {
        if (pool != null) {
            pool.close();

            // 日志记录
            LogHelper.debug("JedisPool is closed ? " + pool.isClosed());
        }
    }
}
