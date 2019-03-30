package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.core.TuzConfig;
import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.DiPlugin;

import java.io.IOException;

/**
 * <p>全局配置例子</p>
 * <p>Global config demo</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/29 17:19:38</p>
 */
public class TuzConfigDemo {

    public static void main(String[] args) throws IOException {

        // 我们先以 cn.com.fishin.tuz.demo.TuzSimpleDemo2 作为切入点
        // 先看原本的例子：
        Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service = DiPlugin.useInstance(xxxService.class);
        //service.say("Hello, Tuz!");

        // 默认情况下，创建的对象是单例模式的
        // 参考 cn.com.fishin.tuz.core.TuzConfig
        xxxService service1 = DiPlugin.useInstance(xxxService.class);
        xxxService service2 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service1 == service2); // 返回 ===> true

        // 由于 Tuz 有一个默认的配置，里面有一个属性
        // 获取类的实例形式，默认是 true，也就是单例模式
        //private boolean singleton = true;
        // 你可以直接设置 Tuz 中的默认配置，但是不被推荐
        Tuz.getConfig().setSingleton(false);

        // 这样获得的对象就是多例模式的
        xxxService service3 = DiPlugin.useInstance(xxxService.class);
        xxxService service4 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service3 == service4); // 返回 ===> false

        // 上面说过，你可以直接设置 Tuz 中的默认配置，但是不被推荐
        // 正确的做法是新创建一个配置对象
        TuzConfig newConfig = new TuzConfig();
        newConfig.setSingleton(true); // 设置为单例模式

        // 设置配置
        Tuz.setConfig(newConfig);

        // 这样获得的对象又是单例模式啦！
        xxxService service5 = DiPlugin.useInstance(xxxService.class);
        xxxService service6 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service5 == service6); // 返回 ===> true
    }
}
