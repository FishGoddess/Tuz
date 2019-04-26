package cn.com.fishin.tuz.plugin;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.factory.FTPUploadFileFactory;
import cn.com.fishin.tuz.helper.IOHelper;
import cn.com.fishin.tuz.loader.properties.ClasspathPropertiesLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试 NetPlugin 的类
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/03 00:02:27</p>
 */
public class NetPluginTest {

    @Before
    public void init() {
        try {
            // 加载资源
            Tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));
            Tuz.init();
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testUploadToServer() {
        String file = "测试中文.txt";
        try {
            System.out.println(NetPlugin.uploadToServer(FTPUploadFileFactory.makeAsciiFile("", file,
                    IOHelper.newInputStreamToFileSystem("Z:/" + file))));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
