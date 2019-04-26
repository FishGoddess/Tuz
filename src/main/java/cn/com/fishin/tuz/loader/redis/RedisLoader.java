package cn.com.fishin.tuz.loader.redis;

import cn.com.fishin.tuz.core.Loadable;
import cn.com.fishin.tuz.factory.JedisPoolFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
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
public class RedisLoader implements Loadable {

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
    public Map<String, String> load() throws IOException {
        return map;
    }

    /**
     * <p>使用命名空间和 redis 资源 map 来初始化</p>
     * <p>Use namespace and redis resource to initialize this loader</p>
     *
     * @param namespace <p>命名空间</p><p>namespace</p>
     * @param map       <p>Redis 资源</p><p>Redis resource</p>
     */
    private RedisLoader(String namespace, Map<String, String> map) {
        this.namespace = namespace;
        this.map = map;
    }

    /**
     * <p>使用命名空间和 redis 连接来初始化</p>
     * <p>Use namespace and redis connection to initialize this loader</p>
     *
     * @param namespace  <p>命名空间</p><p>namespace</p>
     * @param connection <p>connection 资源</p><p>connection resource</p>
     */
    public RedisLoader(String namespace, RedisConnection<String, String> connection) {
        this(namespace, redisMap(connection));
    }

    /**
     * <p>使用主机地址和端口号来初始化</p>
     * <p>
     * <strong>注意：</strong>
     * 如果 redis 主机配置了密码，需要在这里传进去
     * </p>
     * <p>Use host and port to initialize this loader</p>
     * <p>
     * <strong>Notice:</strong>
     * If redis host has password, you need it
     * </p>
     *
     * @param namespace <p>命名空间</p><p>namespace</p>
     * @param host      <p>主机地址</p><p>host</p>
     * @param port      <p>使用的端口号</p><p>port</p>
     * @param password  <p>认证信息</p><p>password</p>
     * @param database  <p>数据库编号</p><p>database</p>
     */
    public RedisLoader(String namespace, String host, int port, String password, int database) {
        this(namespace, connection(JedisPoolFactory.defaultPool(host, port, password, database)));
    }

    /**
     * <p>使用主机地址和端口号来初始化</p>
     * <p>
     * <strong>注意：</strong>
     * 如果 redis 主机配置了密码，需要在这里传进去，命名空间默认是 host:port[database]
     * </p>
     * <p>Use host and port to initialize this loader</p>
     * <p>
     * <strong>Notice:</strong>
     * If redis host has password, you need it. The default namespace is host:port[database]
     * </p>
     *
     * @param host     <p>主机地址</p><p>host</p>
     * @param port     <p>使用的端口号</p><p>port</p>
     * @param password <p>认证信息</p><p>password</p>
     * @param database <p>数据库编号</p><p>database</p>
     */
    public RedisLoader(String host, int port, String password, int database) {
        this(namespace(host, port, database), connection(JedisPoolFactory.defaultPool(host, port, password, database)));
    }

    /**
     * <p>使用主机地址和端口号来初始化</p>
     * <p>
     * <strong>注意：</strong>
     * 如果 redis 主机配置了密码，需要在这里传进去，命名空间默认是 host:port。
     * 默认数据库是 0 号数据库
     * </p>
     * <p>Use host and port to initialize this loader</p>
     * <p>
     * <strong>Notice:</strong>
     * If redis host has password, you need it. The default namespace is host:port.
     * Default database is zero
     * </p>
     *
     * @param host     <p>主机地址</p><p>host</p>
     * @param port     <p>使用的端口号</p><p>port</p>
     * @param password <p>认证信息</p><p>password</p>
     */
    public RedisLoader(String host, int port, String password) {
        this(namespace(host, port), connection(JedisPoolFactory.defaultPool(host, port, password)));
    }

    /**
     * <p>使用主机地址和端口号来初始化</p>
     * <p>
     * <strong>注意：</strong>
     * 如果 redis 主机配置了密码，需要在这里传进去，命名空间默认是 host:port[database]
     * </p>
     * <p>Use host and port to initialize this loader</p>
     * <p>
     * <strong>Notice:</strong>
     * If redis host has password, you need it. The default namespace is host:port[database]
     * </p>
     *
     * @param host     <p>主机地址</p><p>host</p>
     * @param port     <p>使用的端口号</p><p>port</p>
     * @param database <p>数据库编号</p><p>database</p>
     */
    public RedisLoader(String host, int port, int database) {
        this(namespace(host, port, database), connection(JedisPoolFactory.defaultPool(host, port, database)));
    }

    /**
     * <p>使用主机地址和端口号来初始化</p>
     * <p>
     * <strong>注意：</strong>
     * 如果 redis 主机配置了密码，需要在这里传进去，命名空间默认是 host:port。
     * 默认数据库是 0 号数据库
     * </p>
     * <p>Use host and port to initialize this loader</p>
     * <p>
     * <strong>Notice:</strong>
     * If redis host has password, you need it. The default namespace is host:port.
     * Default database is zero
     * </p>
     *
     * @param host <p>主机地址</p><p>host</p>
     * @param port <p>使用的端口号</p><p>port</p>
     */
    public RedisLoader(String host, int port) {
        this(namespace(host, port), connection(JedisPoolFactory.defaultPool(host, port)));
    }

    // 获得默认命名空间  ===>  127.0.0.1:6379[0]
    // Get default namespace  ===>  127.0.0.1:6379[0]
    private static String namespace(String host, int port, int database) {
        return host + ":" + port + "[" + database + "]";
    }

    // 获得默认命名空间  ===>  127.0.0.1:6379
    // Get default namespace  ===>  127.0.0.1:6379
    private static String namespace(String host, int port) {
        return host + ":" + port;
    }

    // 使用 connection 构建 redis map
    // Use connection to redis map
    private static Map<String, String> redisMap(RedisConnection<String, String> connection) {
        return new RedisEntryMap(connection);
    }

    // 使用 JedisPool 构建 connection
    // Use JedisPool to connection
    private static RedisConnection<String, String> connection(JedisPool pool) {
        return new DefaultRedisConnection(pool);
    }
}
