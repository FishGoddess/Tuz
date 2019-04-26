package cn.com.fishin.tuz.factory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * <p>JedisPool 工厂类</p>
 * <p>Jedis Factory</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/26 14:12:20</p>
 */
public class JedisPoolFactory {

    /**
     * <p>获得 JedisPool 对象</p>
     * <p>Get JedisPool instance</p>
     *
     * @param host <p>主机</p><p>Host</p>
     * @param port <p>端口号</p><p>Port</p>
     * @param password <p>主机密码</p><p>Password</p>
     * @param database <p>数据库编号</p><p>Database</p>
     * @return <p>返回构建好的 JedisPool</p><p>Return JedisPool instance</p>
     */
    public static JedisPool defaultPool(String host, int port, String password, int database) {
        return new JedisPool(new JedisPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, password, database);
    }

    /**
     * <p>获得 JedisPool 对象</p>
     * <p>Get JedisPool instance</p>
     *
     * @param host     <p>主机</p><p>Host</p>
     * @param port     <p>端口号</p><p>Port</p>
     * @param database <p>数据库编号</p><p>Database</p>
     * @return <p>返回构建好的 JedisPool</p><p>Return JedisPool instance</p>
     */
    public static JedisPool defaultPool(String host, int port, int database) {
        return defaultPool(host, port, null, database);
    }

    /**
     * <p>获得 JedisPool 对象</p>
     * <p>Get JedisPool instance</p>
     *
     * @param host     <p>主机</p><p>Host</p>
     * @param port     <p>端口号</p><p>Port</p>
     * @param password <p>主机密码</p><p>Password</p>
     * @return <p>返回构建好的 JedisPool</p><p>Return JedisPool instance</p>
     */
    public static JedisPool defaultPool(String host, int port, String password) {
        return defaultPool(host, port, password, Protocol.DEFAULT_DATABASE);
    }

    /**
     * <p>获得 JedisPool 对象</p>
     * <p>Get JedisPool instance</p>
     *
     * @param host     <p>主机</p><p>Host</p>
     * @param port     <p>端口号</p><p>Port</p>
     * @return <p>返回构建好的 JedisPool</p><p>Return JedisPool instance</p>
     */
    public static JedisPool defaultPool(String host, int port) {
        return defaultPool(host, port, null, Protocol.DEFAULT_DATABASE);
    }
}
