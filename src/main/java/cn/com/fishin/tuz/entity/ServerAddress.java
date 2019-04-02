package cn.com.fishin.tuz.entity;

/**
 * <p>服务器地址实体类</p>
 * <p>用来表示一个服务器地址，比如 ip + port</p>
 * <p>Server address entity</p>
 * <p>It is used to find a server by ip and port</p>
 *
 * <p>在使用的时候，推荐直接使用有参构造器</p>
 * <p>看起来就像这样：</p>
 * <pre>
 *     // 指定网络地址的端口
 *     new ServerAddress("127.0.0.1", "21");
 * </pre>
 *
 * <p>Advise using the constructor with arguments</p>
 * <p>It looks like this: </p>
 * <pre>
 *     // Appointed address and port
 *     new ServerAddress("127.0.0.1", "21");
 * </pre>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 13:59:34</p>
 */
public class ServerAddress {

    // 服务器的网络地址
    // The ip of this server
    private String ip = null;

    // 服务器应用的端口
    // The port of this server application
    private int port = 0;

    public ServerAddress() {}

    public ServerAddress(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerAddress{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
