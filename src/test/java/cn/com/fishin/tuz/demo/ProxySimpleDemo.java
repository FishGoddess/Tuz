package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.interceptor.Interceptor;
import cn.com.fishin.tuz.loader.properties.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.DiPlugin;
import cn.com.fishin.tuz.plugin.ProxyPlugin;

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

    public static void main(String[] args) throws Throwable {

        // 同样的，先加载资源文件，这样可以获取到具体的实现类
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service = DiPlugin.useInstance(xxxService.class);

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
        // 不推荐直接使用这个工厂类，建议使用 cn.com.fishin.tuz.plugin.ProxyPlugin 中的方法来获得代理之后的对象
        /*
        xxxService xxxService = ProxyPlugin.useInstance(xxxService.class,
                new Interceptor[]{
                        new CacheInterceptor(),
                        new LogInterceptor()
        });
        */
        // 注意：当这个类可以被继承时，使用 CGlib 来实现动态代理
        // 如果不可以被继承，就使用 JDK 来动态代理，这时要求该类必须实现接口，而且使用接口来接收
        xxxService serviceProxy = ProxyPlugin.useInstance(xxxService.class, new Interceptor[]{
                new LogInterceptor(), // 日志拦截器
                new CacheInterceptor() // 缓存拦截器
        });

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

        // 需要注意的是，由于使用的是 CGlib，所以被代理的类不能是 final 修饰的
        // 也就是说必须要可以被继承，因为 CGlib 就是使用 ASM 产生子类和多态来达到动态代理的效果的
        // 另外，动态代理产生的实例是不是单例的和 Tuz 的配置有关
        // 详情请参考 cn.com.fishin.tuz.core.TuzConfig.isSingleton
        // 默认情况下是单例模式的，也就是说不管你创建多少代理对象都是同一个
        // 如果需要多例的模式，可以修改 Tuz 的配置，调用 cn.com.fishin.tuz.core.TuzConfig.setSingleton
    }
}
