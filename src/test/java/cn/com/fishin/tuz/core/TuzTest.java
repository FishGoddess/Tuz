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

    // Tuz 实例
    private Tuz tuz = Tuz.instance();

    @Before
    public void testLoad() {
        try {
            // 加载配置文件
            tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
            //tuz.load(new ClasspathPropertiesLoader("test.properties", "test", StandardCharsets.UTF_8));
            //tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));
            tuz.load(new FileSystemPropertiesLoader("E:/JavaProject/Tuz/src/test/resources/test2.properties", "test2"));
            //tuz.load(new ClasspathPropertiesLoader("test2.properties"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Test
    public void testUse() {

        Assert.assertEquals(tuz.use("number", "test"), "16");
        Assert.assertEquals(tuz.use("number"), "16");
        Assert.assertEquals(tuz.use("instance", "test"), "cn.com.fishin.tuz.core.Loader");
        Assert.assertEquals(tuz.use("instance"), "cn.com.fishin.tuz.core.Loader");
        Assert.assertEquals(tuz.use("str", "test2"), "world");
        Assert.assertEquals(tuz.use("str"), "world");
        Assert.assertEquals(tuz.use("country", "test"), "中国");
        Assert.assertEquals(tuz.use("country"), "中国");
        Assert.assertEquals(tuz.use("键值"), "属性");
        Assert.assertEquals(tuz.use("键值", "test"), "属性");
        Assert.assertNull(tuz.use("键值", "wrong"));
        Assert.assertNull(tuz.use("str", "test"));
        Assert.assertNull(tuz.use("instance", "test2"));
        Assert.assertNull(tuz.use("hello", "test"));
        Assert.assertNull(tuz.use("hello", "test2"));
        Assert.assertNull(tuz.use("hello"));

        try {
            tuz.unLoad(new ClasspathPropertiesLoader("test.properties", "test"));
        } catch (Throwable throwable) {
            System.err.println(throwable.getMessage());
        }
        Assert.assertNull(tuz.use("number", "test"));
        Assert.assertNull(tuz.use("number"));

        //Map<String, String> resource = new HashMap<>(2);
        //resource.put("xxx", "ok");
        //tuz.appendResource(resource, "test");
        Loader test = new ClasspathPropertiesLoader("test.properties", "test");
        try {
            tuz.load(test);
            tuz.appendResource(test, "xxx", "xxxValue");
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        Assert.assertEquals(tuz.use("xxx", "test"), "xxxValue");
        Assert.assertEquals(tuz.use("xxx"), "xxxValue");
    }
}
