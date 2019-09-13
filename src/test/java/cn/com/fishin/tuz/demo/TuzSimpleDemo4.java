package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Loader;
import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.redis.RedisLoader;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试 Redis 加载器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 15:05:08</p>
 */
public class TuzSimpleDemo4 {

    public static void main(String[] args) throws Throwable {

        // 使用 RedisLoader 来加载 Redis 上的资源
        // 注意这个加载操作其实并不是真的加载
        // 而只是创建一个连接池，每一次获取数据都从网络中获取
        // 现在加入了缓存，将得到的值暂时缓存，可以大幅度提高短时间大量读取的性能
        final String namespace = "192.168.32.131:6379";

        Tuz tuz = Tuz.instance();

        // 注意：
        // 这里的 JedisRedisConnection 是需要您去实现的，由于每个人所使用的 redis 客户端不一样
        // 所以这里没有去使用特定的一种客户端，让用户自己去实现，其实实现起来也很简单，就只需要实现特定的几个方法，
        // 可以参考这里例子的 JedisRedisConnection 类
        Loader redisLoader = new RedisLoader(namespace,
                new JedisRedisConnection(new Jedis("192.168.32.131", 6379)));
        tuz.load(redisLoader);

        // 下面你就可以像使用普通资源一样使用 redis 上的资源了
        // 你可以向资源池加入新资源
        // 也就是向 redis 中存储数据，比如你可以添加一对数据
        // 注意：这里指定命名空间为 192.168.32.129:6379
        tuz.appendResource(redisLoader, "key", "哈哈哈");

        // 当然，你可能想添加一堆数据
        // 这里可以使用批量添加，内部使用 pipeline 管道来批量新增，
        // 所以不用担心批量新增会造成大量网络堵塞
        tuz.appendResource(redisLoader, manyEntries());

        // 当你确保已经存在数据时，就可以正常使用了
        System.out.println("key ===> " + tuz.use("key"));
        System.out.println("key1 ===> " + tuz.use("key1"));
        System.out.println("key2 ===> " + tuz.use("key2"));

        // 如果你已经不需要使用这个资源库，就可以考虑卸载这个资源库了
        tuz.unLoad(redisLoader);
        System.out.println("key ===> " + tuz.use("key"));
    }

    // 准备一堆数据
    private static Map<String, String> manyEntries() {
        Map<String, String> entries = new HashMap<>(8);
        for (int i = 0; i < 4; i++) {
            entries.put("key" + i, "value" + i);
        }
        return entries;
    }
}
