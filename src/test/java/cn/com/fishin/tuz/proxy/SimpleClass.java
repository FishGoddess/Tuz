package cn.com.fishin.tuz.proxy;

import java.io.Serializable;

/**
 * Cglib 测试类
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/14 19:00:15</p>
 */
public class SimpleClass implements Serializable {

    public void test() {
        System.out.println("SimpleClass.test() invoked...");
    }
}
