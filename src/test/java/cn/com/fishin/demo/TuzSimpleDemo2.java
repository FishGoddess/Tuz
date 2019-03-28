package cn.com.fishin.demo;

import cn.com.fishin.core.Tuz;
import cn.com.fishin.loader.ClasspathPropertiesLoader;

import java.io.IOException;

/**
 * <p>简单的使用例子 二</p>
 * <p>Simple demo 2</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/29 00:09:38
 */
public class TuzSimpleDemo2 {

    public static void main(String[] args) throws IOException {

        // 传统的方式使用接口：
        //xxxService service = new xxxServiceImpl();
        // 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
        // 而使用 Tuz 可以轻松做到解耦，请看下面：
        Tuz.load("test", new ClasspathPropertiesLoader("test.properties"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service = Tuz.useInstance("xxxService", "test", xxxService.class);
        service.say("Hello, Tuz!");

        // 同样的，你可以不指定命名空间，但是，真的不推荐！！！
        //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service = Tuz.useInstance("xxxService", xxxService.class);
        //service.say("Hello, Tuz!");
    }
}
