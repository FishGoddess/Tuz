package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.loader.properties.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.loader.properties.FileSystemPropertiesLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试 tuz 类
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 16:11:12</p>
 */
public class TuzTest {

    @Before
    public void testLoad() {
        try {
            // 加载配置文件
            Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
            //Tuz.load(new ClasspathPropertiesLoader("test.properties", "test", StandardCharsets.UTF_8));
            //Tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));
            Tuz.load(new FileSystemPropertiesLoader("E:/JavaProject/tuz/src/test/resources/test2.properties", "test2"));
            //Tuz.load(new ClasspathPropertiesLoader("test2.properties"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Test
    public void testUse() {
        Assert.assertEquals(Tuz.use("number", "test"), "16");
        Assert.assertEquals(Tuz.use("number"), "16");
        Assert.assertEquals(Tuz.use("instance", "test"), "cn.com.fishin.tuz.core.Loadable");
        Assert.assertEquals(Tuz.use("instance"), "cn.com.fishin.tuz.core.Loadable");
        Assert.assertEquals(Tuz.use("str", "test2"), "world");
        Assert.assertEquals(Tuz.use("str"), "world");
        Assert.assertEquals(Tuz.use("country", "test"), "中国");
        Assert.assertEquals(Tuz.use("country"), "中国");
        Assert.assertEquals(Tuz.use("键值"), "属性");
        Assert.assertEquals(Tuz.use("键值", "test"), "属性");
        Assert.assertNull(Tuz.use("键值", "wrong"));
        Assert.assertNull(Tuz.use("str", "test"));
        Assert.assertNull(Tuz.use("instance", "test2"));
        Assert.assertNull(Tuz.use("hello", "test"));
        Assert.assertNull(Tuz.use("hello", "test2"));
        Assert.assertNull(Tuz.use("hello"));

        // 移除指定资源
        try {
            System.out.println(Tuz.unUse("number", "test"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        Assert.assertNull(Tuz.use("number", "test"));
        Assert.assertNull(Tuz.use("number"));

        try {
            Tuz.unLoad(new ClasspathPropertiesLoader("test.properties", "test"));
        } catch (Throwable throwable) {
            System.err.println(throwable.getMessage());
        }
        Assert.assertNull(Tuz.use("number", "test"));
        Assert.assertNull(Tuz.use("number"));

        try {
            Tuz.reLoad(new ClasspathPropertiesLoader("test.properties", "test"));
            Assert.assertEquals(Tuz.use("number", "test"), "16");
            Assert.assertEquals(Tuz.use("number"), "16");
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        Assert.assertNull(Tuz.use("xxx", "test"));
        Assert.assertEquals(Tuz.use("number", "test"), "16");
        //Map<String, String> resource = new HashMap<>(2);
        //resource.put("xxx", "ok");
        //Tuz.appendResource(resource, "test");
        try {
            Tuz.appendResource("xxx", "ok", "test");
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        Assert.assertEquals(Tuz.use("xxx", "test"), "ok");
        Assert.assertEquals(Tuz.use("xxx"), "ok");

        Assert.assertEquals(Tuz.use("number", "test"), "16");
        try {
            Tuz.unUse("number", "test");
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        Assert.assertNull(Tuz.use("number", "test"));
    }
}
