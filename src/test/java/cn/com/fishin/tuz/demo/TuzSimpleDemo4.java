package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.redis.RedisLoader;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

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
        // 后面会考虑加入缓存，将得到的值暂时缓存，可以大幅度提高短时间大量读取的性能
        // 注意：这里没有指定命名空间，默认使用 192.168.32.129:6379
        // 如果你指定了数据库编号，那就默认为 192.168.32.129:6379[0]
        final String namespace = "192.168.32.129:6379";
        Tuz.load(new RedisLoader("192.168.32.129", 6379));

        // 下面你就可以像使用普通资源一样使用 redis 上的资源了
        // 你可以向资源池加入新资源
        // 也就是向 redis 中存储数据，比如你可以添加一对数据
        // 注意：这里指定命名空间为 192.168.32.129:6379
        Tuz.appendResource("key", "哈哈哈", namespace);

        // 当然，你可能想添加一堆数据
        // 这里可以使用批量添加，内部使用 pipeline 管道来批量新增，
        // 所以不用担心批量新增会造成大量网络堵塞
        Tuz.appendResource(manyEntries(), namespace);

        // 当你确保已经存在数据时，就可以正常使用了
        System.out.println("key ===> " + Tuz.use("key"));
        System.out.println("key1 ===> " + Tuz.use("key1"));
        System.out.println("key2 ===> " + Tuz.use("key2"));

        // 当然，对于资源的管理，处理使用之外，还有写操作
        // 比如，这个资源已经不需要了，就可以移除掉
        System.out.println("key3 ===> " + Tuz.use("key3"));
        System.out.println(Tuz.unUse("key3", namespace));
        System.out.println("key3 ===> " + Tuz.use("key3"));

        // 如果你已经不需要使用这个资源库，就可以考虑卸载这个资源库了
        Tuz.unLoad(namespace);
        System.out.println("key ===> " + Tuz.use("key"));

        // 释放资源
        Tuz.destroy();
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
