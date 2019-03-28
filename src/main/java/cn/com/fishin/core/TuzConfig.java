package cn.com.fishin.core;

/**
 * <p>全局配置</p>
 * <p>所有设置都可以通过这个类去设置，Tuz 中会从这个配置类中获取配置信息</p>
 * <p>Global config</p>
 * <p>This class includes all settings, and Tuz will fetch setting from it</p>
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
