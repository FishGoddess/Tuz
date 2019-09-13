package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.properties.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.DiPlugin;

//@SpringBootApplication
public class TuzSpringBootDemo  {

    /*
    * 这个类仅仅是演示与 SpringBoot 一起使用的例子，并不代表这是唯一用法
    *
    * 曾经，在使用 Spring 的时候，配置文件可以做到不改代码更改实现类的功能，
    * 后来推出了 SpringBoot，的确简化了大量的配置文件操作，而且对于微服务来说，
    * 更改实现类往往意味着服务的改变，所以当发生需要更改需求而更改代码的事情的时候，
    * 我们往往可以理解和接受，而且就算是要做到从 .properties 配置文件中读取配置
    * 从而达到不更改代码更改实现类的目的也不难，毕竟 Spring 读取配置文件已经很简单，
    * 但是这些业务其实是重复而且有意义的，这时候 Tuz 就体现出很好的复用和业务性了
    * */

    // 你可以选择使用 static 块初始化 Tuz
    // 当然你也可以选择在 main 方法中初始化
    static {
        try {
            Tuz tuz = Tuz.instance();
            tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
        } catch (Throwable t) {
            // Do something...
            System.err.println(t.getMessage());
        }
    }

    public static void main(String[] args) {

        // 除了在 static {} 中初始化，你也可以在这里初始化，甚至可以配合参数 args
        // 做到选择不同的环境去初始化 Tuz
        //Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 这是 SpringBoot 的用法，这里不作讨论
        //SpringApplication.run(TuzSpringBootDemo.class, args);
    }

    // Spring 初始化 Bean
    //@Bean
    public xxxService getXxxService() {

        // 直接返回 xxxService 即可
        // 这样，你只需在 test.properties 中更改这个类的实现即可
        // 不需要更改代码，也不需要写多余的代码
        return DiPlugin.useInstance(xxxService.class);
    }
}
