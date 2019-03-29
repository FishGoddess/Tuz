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
 * //Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
 * //xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * //service.say("Hello, Tuz!");
 * </p>
 *
 * <p>
 * 默认情况下，创建的对象是单例模式的
 * 参考 cn.com.fishin.tuz.core.TuzConfig
 * </p>
 *
 * <pre>
 * xxxService service1 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * xxxService service2 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * System.out.println(service1 == service2); // 返回 ===> true
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
 * xxxService service3 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * xxxService service4 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * System.out.println(service3 == service4); // 返回 ===> false
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
 * xxxService service5 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * xxxService service6 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
 * System.out.println(service5 == service6); // 返回 ===> true
 * </pre>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 22:01:35
 */
public class TuzConfig {

    // 获取类的实例形式，默认是 true，也就是单例模式
    private boolean singleton = true;

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

    @Override
    public String toString() {
        return "TuzConfig{" +
                "singleton=" + singleton +
                '}';
    }
}
