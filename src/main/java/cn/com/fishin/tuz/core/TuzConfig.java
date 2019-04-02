package cn.com.fishin.tuz.core;

/**
 * <p>全局配置</p>
 * <p>所有设置都可以通过这个类去设置，tuz 中会从这个配置类中获取配置信息</p>
 * <p>Global config</p>
 * <p>This class includes all settings, and tuz will fetch setting from it</p>
 *
 * <p>
 * 我们先以 cn.com.fishin.tuz.demo.TuzSimpleDemo2 作为切入点
 * 先看原本的例子：
 * //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
 * //xxxService service = DiPlugin.useInstance(xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * <p>
 * 默认情况下，创建的对象是单例模式的
 * 参考 cn.com.fishin.tuz.core.TuzConfig
 * </p>
 *
 * <pre>
 * xxxService service1 = DiPlugin.useInstance(xxxService.class);
 * xxxService service2 = DiPlugin.useInstance(xxxService.class);
 * System.out.println(service1 == service2); // 返回 ===&gt; true
 * </pre>
 *
 * <p>
 * 由于 Tuz 有一个默认的配置，里面有一个属性
 * 获取类的实例形式，默认是 true，也就是单例模式
 * //private boolean singleton = true;
 * 你可以直接设置 Tuz 中的默认配置，但是不被推荐
 * </p>
 *
 * <pre>Tuz.getConfig().setSingleton(false);</pre>
 *
 * <p>这样获得的对象就是多例模式的</p>
 *
 * <pre>
 * xxxService service3 = DiPlugin.useInstance(xxxService.class);
 * xxxService service4 = DiPlugin.useInstance(xxxService.class);
 * System.out.println(service3 == service4); // 返回 ===&gt; false
 * </pre>
 *
 * <p>
 * 上面说过，你可以直接设置 Tuz 中的默认配置，但是不被推荐
 * 正确的做法是新创建一个配置对象
 * </p>
 *
 * <pre>
 * TuzConfig newConfig = new TuzConfig();
 * newConfig.setSingleton(true); // 设置为单例模式
 * </pre>
 *
 * <p>设置配置</p>
 * <pre>Tuz.setConfig(newConfig);</pre>
 *
 * <p>这样获得的对象又是单例模式啦！</p>
 * <pre>
 * xxxService service5 = DiPlugin.useInstance(xxxService.class);
 * xxxService service6 = DiPlugin.useInstance(xxxService.class);
 * System.out.println(service5 == service6); // 返回 ===&gt; true
 * </pre>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 22:01:35</p>
 */
public class TuzConfig {

    // 获取类的实例形式，默认是 true，也就是单例模式
    // The way of creating instance, default is true
    private boolean singleton = true;

    // FTP 主机地址，比如 127.0.0.1
    // FTP host address, such as 127.0.0.1
    private String ftpHost = Tuz.useGracefully("ftp.host", "127.0.0.1");

    // FTP 使用端口，比如 21
    // FTP port, such as 21
    private int ftpPort = Integer.valueOf(Tuz.useGracefully("ftp.port", "21"));

    // 登录到 FTP 服务器的用户名
    // FTP login user
    private String ftpUser = Tuz.useGracefully("ftp.user", "'You should config a user to login FTP server!'");

    // 登录到 FTP 服务器的用户的密码
    // FTP login password
    private String ftpPassword = Tuz.useGracefully("ftp.password", "'You should config a password to login FTP server!'");

    // 连接通信使用的字符编码，FTPClient 默认使用的是 "ISO-8859-1"，
    // 中文的文件名可能出现乱码，所以这里将默认编码设置为 UTF-8
    // The encoding used to net transport, due to FTPClient (default is ISO-8859-1)
    // Non-English character may wrong in this encoding, such as file name
    // So I set it to UTF-8 in order to support this kind of file name
    private String ftpControlEncoding = "UTF-8";

    /**
     * <p>获取类的实例形式，默认是 true，也就是单例模式</p>
     * <p>Get class instance form, default is singleton</p>
     *
     * @return <p>类的实例形式</p><p>class instance form</p>
     */
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * <p>设置类的实例形式，默认是 true，也就是单例模式</p>
     * <p>Set class instance form, default is singleton</p>
     *
     * @param singleton <p>类的实例形式，true 表示单例模式</p>
     *                  <p>class instance form, true is singleton</p>
     */
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    /**
     * <p>获取 FTP 服务器主机地址</p>
     * <p>Get the host of FTP server</p>
     *
     * @return <p>返回 FTP 服务器主机地址</p><p>Return the host of FTP server</p>
     */
    public String getFtpHost() {
        return ftpHost;
    }

    /**
     * <p>设置 FTP 服务器主机地址</p>
     * <p>Set the host of FTP server</p>
     *
     * @param ftpHost <p>FTP 服务器主机地址</p>
     *                <p>The host of FTP server</p>
     */
    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    /**
     * <p>获取 FTP 服务器应用端口</p>
     * <p>Get the port of FTP server application</p>
     *
     * @return <p>返回 FTP 服务器应用端口</p><p>Return the port of FTP server application</p>
     */
    public int getFtpPort() {
        return ftpPort;
    }

    /**
     * <p>设置 FTP 服务器应用端口</p>
     * <p>Set the port of FTP server application</p>
     *
     * @param ftpPort <p>FTP 服务器应用端口</p>
     *                <p>The port of FTP server application</p>
     */
    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    /**
     * <p>获取 FTP 服务器登录用户</p>
     * <p>Get the port of FTP server login user</p>
     *
     * @return <p>返回 FTP 服务器登录用户</p><p>Return the port of FTP server login user</p>
     */
    public String getFtpUser() {
        return ftpUser;
    }

    /**
     * <p>设置 FTP 服务器登录用户</p>
     * <p>Set the port of FTP server login user</p>
     *
     * @param ftpUser <p>FTP 服务器登录用户</p>
     *                <p>The port of FTP server login user</p>
     */
    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    /**
     * <p>获取 FTP 服务器登录用户密码</p>
     * <p>Get the port of FTP server login password</p>
     *
     * @return <p>返回 FTP 服务器登录用户密码</p><p>Return the port of FTP server login password</p>
     */
    public String getFtpPassword() {
        return ftpPassword;
    }

    /**
     * <p>设置 FTP 服务器登录用户密码</p>
     * <p>Set the port of FTP server login password</p>
     *
     * @param ftpPassword <p>FTP 服务器登录用户密码</p>
     *                    <p>The port of FTP server login password</p>
     */
    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    /**
     * <p>获取 FTP 服务器网络通信使用的编码</p>
     * <p>Get the encoding of FTP server's net transport</p>
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     *
     * @return <p>返回 FTP 服务器网络通信使用的编码</p><p>Return the encoding of FTP server's net transport</p>
     */
    public String getFtpControlEncoding() {
        return ftpControlEncoding;
    }

    /**
     * <p>设置 FTP 服务器网络通信使用的编码</p>
     * <p>Set the encoding of FTP server's net transport</p>
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     *
     * @param ftpControlEncoding <p>FTP 服务器网络通信使用的编码</p>
     *                           <p>The encoding of FTP server's net transport</p>
     */
    public void setFtpControlEncoding(String ftpControlEncoding) {
        this.ftpControlEncoding = ftpControlEncoding;
    }

    @Override
    public String toString() {
        return "TuzConfig{" +
                "singleton=" + singleton +
                ", ftpHost='" + ftpHost + '\'' +
                ", ftpPort=" + ftpPort +
                ", ftpUser='" + ftpUser + '\'' +
                ", ftpPassword='" + ftpPassword + '\'' +
                ", ftpControlEncoding='" + ftpControlEncoding + '\'' +
                '}';
    }
}
