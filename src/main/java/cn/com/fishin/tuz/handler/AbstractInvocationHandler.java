package cn.com.fishin.tuz.handler;

/**
 * <p>抽象的调用处理器</p>
 * <p>主要是为了动态代理模式准备的模板处理器</p>
 * <p>具体实现类参考：</p>
 * @see InterceptorInvocationHandler
 *
 * <p>Abstract invocation handler</p>
 * <p>For dynamic proxy, actually it is a 'templated' handler</p>
 * <p>See implemented class:</p>
 * @see InterceptorInvocationHandler
 *
 * @see net.sf.cglib.proxy.InvocationHandler
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 15:41:47</p>
 */
public abstract class AbstractInvocationHandler implements net.sf.cglib.proxy.InvocationHandler, java.lang.reflect.InvocationHandler {

    // 被代理的目标对象
    // Proxy target
    private Object target = null;

    /**
     * <p>使用被代理的目标对象来初始化这个处理器</p>
     * <p>Use a target object to init this handler</p>
     *
     * @param target <p>被代理的目标对象</p><p>Proxy target</p>
     */
    protected AbstractInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * <p>获得被代理的目标对象</p>
     * <p>Get target object</p>
     *
     * @return <p>返回被代理的目标对象</p><p>Return target object</p>
     */
    protected Object getTarget() {
        return target;
    }

    /**
     * <p>设置被代理的目标对象</p>
     * <p>Set target object</p>
     *
     * @param target <p>被代理的目标对象</p>
     *               <p>Target object</p>
     */
    protected void setTarget(Object target) {
        this.target = target;
    }
}
