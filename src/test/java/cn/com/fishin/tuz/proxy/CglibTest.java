package cn.com.fishin.tuz.proxy;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.factory.ProxyFactory;
import cn.com.fishin.tuz.handler.InterceptorInvocationHandler;
import cn.com.fishin.tuz.helper.ClassHelper;
import cn.com.fishin.tuz.interceptor.Interceptor;
import cn.com.fishin.tuz.loader.properties.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.ProxyPlugin;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试 cglib 的动态代理
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 18:55:52</p>
 */
public class CglibTest {

    @Before
    public void before() {
        try {
            Tuz tuz = Tuz.instance();
            tuz.load(new ClasspathPropertiesLoader("test2.properties"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
    }

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
    public void testProxyFactoryCGlib() {

        SimpleClass simpleClass = new SimpleClass();

        SimpleClass simpleClass1 = (SimpleClass) ProxyFactory.wrapByCGlib(simpleClass,
                new InterceptorInvocationHandler(simpleClass, new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                }));

        simpleClass1.test();
    }

    @Test
    public void testProxyFactoryJDK() {

        SimpleClassWithInterface simpleClass = new SimpleClassWithInterface();

        SimpleInterface SimpleInterface = (SimpleInterface) ProxyFactory.wrapByJDK(simpleClass,
                new InterceptorInvocationHandler(simpleClass, new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                }));

        SimpleInterface.test();
    }

    @Test
    public void testProxyFactoryWrap() {

        SimpleClass simpleClass = new SimpleClass();

        simpleClass = (SimpleClass) ProxyFactory.wrap(simpleClass,
                new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                });

        simpleClass.test();

        SimpleInterface simpleInterface = (SimpleInterface) ProxyFactory.wrap(new SimpleClassWithInterface(),
                new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                });

        simpleInterface.test();

    }

    @Test
    public void testProxyPlugin() {

        SimpleClass simpleClass = ProxyPlugin.useInstance(SimpleClass.class,
                new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                });

        simpleClass.test();

        SimpleInterface simpleInterface = (SimpleInterface) ProxyPlugin.useInstance(SimpleInterface.class,
                new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                });

        simpleInterface.test();

        //System.out.println(Modifier.isFinal(SimpleClass.class.getModifiers()));
    }
}
