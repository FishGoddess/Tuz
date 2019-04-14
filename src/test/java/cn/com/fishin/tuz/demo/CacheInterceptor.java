package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.entity.InterceptedMethod;
import cn.com.fishin.tuz.interceptor.DefaultInterceptor;

/**
 * 演示缓存拦截器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 22:36:50</p>
 */
public class CacheInterceptor extends DefaultInterceptor {

    // 这个方法仅仅只是简单演示逻辑，并没有真的执行操作
    // 实际代码写法需要按实际业务场景来定
    private static Object findInCacheSystem(Object[] args) {
        System.out.println("在缓存中查找数据：" + args);

        if (args.length == 1) {
            System.out.println("缓存中找不到数据！");
            return null;
        }

        System.out.println("缓存中找到了数据！");
        return new Object();
    }

    @Override
    public boolean before(InterceptedMethod method) {

        if (findInCacheSystem(method.getArgs()) == null) {
            // 缓存中找不到数据
            return true;
        }

        // 缓存中找到了数据，阻止被拦截方法继续执行
        return false;
    }

    @Override
    public boolean after(InterceptedMethod method) {
        System.out.println("加载数据 " + method.getResult() + " 到缓存...");
        return true;
    }
}
