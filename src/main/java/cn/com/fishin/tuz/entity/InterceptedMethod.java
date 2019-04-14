package cn.com.fishin.tuz.entity;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>代理中的方法实体类</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 14:43:45</p>
 */
public class InterceptedMethod {

    private Object executingObject = null;
    private Method thisMethod = null;
    private Object[] args = null;
    private Object result = null;
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
