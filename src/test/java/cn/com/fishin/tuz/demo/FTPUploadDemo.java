package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.factory.FTPUploadFileFactory;
import cn.com.fishin.tuz.helper.IOHelper;
import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;
import cn.com.fishin.tuz.plugin.NetPlugin;

import java.io.IOException;

/**
 * 演示 FTP 上传插件
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/12 14:03:56</p>
 */
public class FTPUploadDemo {

    public static void main(String[] args) throws IOException {

        // 加载资源
        Tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));

        // 注意：这个必须要在 load 方法执行之后执行，具体原因请查看
        Tuz.init();

        // 上传到 FTP 服务器
        // 当然你可能会觉得这么使用很麻烦！
        // 正常我们使用 FTP 上传的时候，多数是上传一个二进制文件
        // 因此您可以直接使用 NetPlugin.
        NetPlugin.uploadToServer(FTPUploadFileFactory.makeAsciiFile("YourDirection", "YourFile",
                    IOHelper.newInputStreamToFileSystem("Z:/YourFile")));

        // 如果您正在使用 Servlet 或者是 SpringMVC，您可以使用这个方法上传一个二进制文件
        //NetPlugin.uploadBinaryToServer("YourDirection", "YourFile", multipartFile.getInputStream);
    }
}
