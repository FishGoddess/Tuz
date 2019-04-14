package cn.com.fishin.tuz.proxy;

import cn.com.fishin.tuz.factory.ProxyFactory;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.helper.ClassHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

/**
 * 测试 cglib 的动态代理
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 18:55:52</p>
 */
public class CglibTest {

    @Test
    public void testCglib() {

        SimpleClass simpleClass = new SimpleClass();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassHelper.classOf(simpleClass));
        /*enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });*/
        enhancer.setCallback(new InterceptorInvocationHandler(simpleClass, new Interceptor[] {
                new TestInterceptor(),
                new TestInterceptor(),
                new TestInterceptor()
        }));

        SimpleClass sample = (SimpleClass) enhancer.create();
        sample.test();
    }

    @Test
    public void testProxyFactory() {

        SimpleClass simpleClass = new SimpleClass();

        SimpleClass simpleClass1 = (SimpleClass) ProxyFactory.wrap(simpleClass,
                new InterceptorInvocationHandler(simpleClass, new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                }));

        simpleClass1.test();
    }
}
