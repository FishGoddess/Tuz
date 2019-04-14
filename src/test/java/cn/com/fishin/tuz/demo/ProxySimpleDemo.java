package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.factory.ProxyFactory;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.interceptor.Interceptor;
import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.DiPlugin;

import java.io.IOException;

/**
 * 动态代理演示案例
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 22:17:04</p>
 */
public class ProxySimpleDemo {

    /*
        // 在 test 包中有使用这个 ProxyFactory.wrap 方法的案例
        // 但是不建议直接使用这个方法，因为这个方法更多是一个工厂方法，提供内部使用
        SimpleClass simpleClass = new SimpleClass();

        SimpleClass simpleClass1 = (SimpleClass) ProxyFactory.wrap(simpleClass,
                new InterceptorInvocationHandler(simpleClass, new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                }));

        simpleClass1.test();
    */

    public static void main(String[] args) throws IOException {

        // 同样的，先加载资源文件，这样可以获取到具体的实现类
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service = DiPlugin.useInstance(xxxService.class);
        //service.say("Hello, tuz!");

        // 以上演示了最单纯的使用依赖注入的方法
        // 但是这时候你来了新需求，需要在原本的基础之上加入两个需求：
        // 1. 现在的系统加入了 redis 当缓存系统，在调用方法之前先在缓存系统找
        //    找不到在从数据库找，并且将数据加载到缓存系统
        // 2. 需要在业务方法上都加入日志记录功能
        // 以上两点都是次要业务，不建议直接写入到当前已存在的业务中或者你无法改动现有代码
        // 这时候你就需要使用面向切面的思想去更新你的业务类
        // Tuz 中使用拦截器来达到这个目的
        // 顶级拦截器接口：cn.com.fishin.tuz.interceptor.Interceptor
        // 但是不建议直接使用这个拦截器，建议使用 cn.com.fishin.tuz.interceptor.DefaultInterceptor
        // 然后选择性地复写特定方法来达到特定业务功能
        xxxService serviceProxy = (xxxService) ProxyFactory.wrap(service,
                new InterceptorInvocationHandler(service, new Interceptor[]{
                        new LogInterceptor(), // 日志拦截器
                        new CacheInterceptor() // 缓存拦截器
                })
        );

        // 这是一个对比
        System.out.println("====================== 使用拦截器之前的业务 ===================");
        service.say("没用拦截器...");
        System.out.println("====================== 使用拦截器之前的业务 ===================");

        System.out.println("====================== 使用拦截器之后的业务 ===================");
        serviceProxy.say("使用拦截器...");
        System.out.println("====================== 使用拦截器之后的业务 ===================");

        // 这是第二个对比
        System.out.println("====================== 使用拦截器之前的业务 ===================");
        service.hung(18, 16);
        System.out.println("====================== 使用拦截器之前的业务 ===================");

        System.out.println("====================== 使用拦截器之后的业务 ===================");
        serviceProxy.hung(81, 61);
        System.out.println("====================== 使用拦截器之后的业务 ===================");

        /*
        // 执行结果：
        ====================== 使用拦截器之前的业务 ===================
        没用拦截器...
        ====================== 使用拦截器之前的业务 ===================
        ====================== 使用拦截器之后的业务 ===================
        日志记录正常操作 ===> public void cn.com.fishin.tuz.demo.xxxServiceImpl.say(java.lang.String)
        在缓存中查找数据：[Ljava.lang.Object;@29774679
        缓存中找不到数据！
        使用拦截器...
        加载数据 null 到缓存...
        ====================== 使用拦截器之后的业务 ===================
        ====================== 使用拦截器之前的业务 ===================
        18 hung 16
        ====================== 使用拦截器之前的业务 ===================
        ====================== 使用拦截器之后的业务 ===================
        日志记录正常操作 ===> public void cn.com.fishin.tuz.demo.xxxServiceImpl.hung(int,int)
        在缓存中查找数据：[Ljava.lang.Object;@26653222
        缓存中找到了数据！
        ====================== 使用拦截器之后的业务 ===================
        */
    }
}
