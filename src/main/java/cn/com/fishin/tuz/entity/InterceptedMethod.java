package cn.com.fishin.tuz.entity;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>拦截器中的方法实体类</p>
 * <p>主要是包含一些拦截器中需要用到的，比如当前执行方法，被拦截方法的返回结果等等</p>
 * <p>Entity of intercepted method</p>
 * <p>Some attributes that interceptor will use,
 * such as current method and invoking result</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 14:43:45</p>
 */
public class InterceptedMethod {

    // 执行这个方法的对象
    // The object who executes the method
    private Object executingObject = null;

    // 这个方法对象
    // The method itself
    private Method thisMethod = null;

    // 执行方法的参数，可能为空
    // The arguments passed, maybe null
    private Object[] args = null;

    // 方法执行的结果，可能为空
    // The result returned, maybe null
    private Object result = null;

    // 方法执行的异常，可能为空
    // The exception happened, maybe null
    private Exception exception = null;

    public InterceptedMethod() {}

    public InterceptedMethod(Object executingObject, Method thisMethod, Object[] args) {
        this.executingObject = executingObject;
        this.thisMethod = thisMethod;
        this.args = args;
    }

    public Object getExecutingObject() {
        return executingObject;
    }

    public void setExecutingObject(Object executingObject) {
        this.executingObject = executingObject;
    }

    public Method getThisMethod() {
        return thisMethod;
    }

    public void setThisMethod(Method thisMethod) {
        this.thisMethod = thisMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "InterceptedMethod{" +
                "executingObject=" + executingObject +
                ", thisMethod=" + thisMethod +
                ", args=" + Arrays.toString(args) +
                ", result=" + result +
                ", exception=" + exception +
                '}';
    }
}
