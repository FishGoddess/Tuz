package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.DiPlugin;

import java.io.IOException;

/**
 * <p>简单的使用例子 二</p>
 * <p>Simple demo 2</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/29 00:09:38</p>
 */
public class TuzSimpleDemo2 {

    public static void main(String[] args) throws Throwable {

        // 传统的方式使用接口：
        //xxxService service = new xxxServiceImpl();
        // 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
        // 而使用 Tuz 可以轻松做到解耦，请看下面：
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service1 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        service1.say("Hello, tuz!");

        // 你也许会觉得上面的方法有些参数多余了
        // 对没错，就是 xxxService 和 xxxService.class
        // 当我的 key 和类的名字一样的时候，其实这个 key 值是可以省略的
        // 注意这里的 xxxService 在配置文件中的 key 就是 xxxService
        xxxService service2 = DiPlugin.useInstance(xxxService.class);
        service2.say("Hello, tuz!");

        // 同样的，你可以不指定命名空间，但是，这样会导致多一次的查找！
        //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service3 = DiPlugin.useInstance("xxxService", xxxService.class);
        //service3.say("Hello, Tuz!");
    }
}
