package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.loader.FileSystemPropertiesLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试 tuz 类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 16:11:12
 */
public class TuzTest {

    @Before
    public void testLoad() {
        try {
            // 加载配置文件
            Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
            //Tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));
            Tuz.load(new FileSystemPropertiesLoader("E:/JavaProject/tuz/src/test/resources/test2.properties", "test2"));
            //Tuz.load(new ClasspathPropertiesLoader("test2.properties"));
        } catch (IOException e) {
            e.printStackTrace();
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
        Assert.assertNull(Tuz.use("str", "test"));
        Assert.assertNull(Tuz.use("instance", "test2"));
        Assert.assertNull(Tuz.use("hello", "test"));
        Assert.assertNull(Tuz.use("hello", "test2"));
        Assert.assertNull(Tuz.use("hello"));
    }
}
