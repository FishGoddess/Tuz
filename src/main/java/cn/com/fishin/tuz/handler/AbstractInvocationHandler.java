package cn.com.fishin.tuz.handler;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 15:41:47</p>
 */
public abstract class AbstractInvocationHandler implements InvocationHandler {

    // 被代理的目标对象
    private Object target = null;

    // 使用被代理的目标对象来初始化这个处理器
    protected AbstractInvocationHandler(Object target) {
        this.target = target;
    }

    protected Object getTarget() {
        return target;
    }

    protected void setTarget(Object target) {
        this.target = target;
    }
}
