package cn.com.fishin.tuz.template;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>Jedis 操作模板类</p>
 * <p>Jedis operation template class</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 12:53:16</p>
 */
public class JedisTemplate<Result> {

    /**
     * <p>执行 Redis 操作</p>
     * <p>Execute redis operations</p>
     *
     * @param pool <p>Jedis 资源池</p><p>Jedis resource pool</p>
     * @param block <p>要在 redis 中做的操作</p><p>The operations will be did in redis</p>
     * @return <p>返回执行结果</p><p>Return result</p>
     */
    public Result execute(JedisPool pool, RedisBlock<Result> block) {
        // 释放资源，回收到 pool
        try (Jedis jedis = pool.getResource()) {
            return block.doInRedis(jedis);
        }
    }

    /**
     * <p>要在 redis 中做的操作</p>
     * <p>The operations will be did in redis</p>
     *
     * @param <Result> <p>返回值的类型</p><p>The type of result</p>
     */
    public interface RedisBlock<Result> {

        /**
         * <p>要在 redis 中做的操作</p>
         * <p>The operations will be did in redis</p>
         *
         * @param jedis <p>获得的 jedis 资源</p><p>The real tool to do operations</p>
         * @return <p>返回执行的结果</p><p>Return result</p>
         */
        Result doInRedis(Jedis jedis);
    }
}
