package cn.com.fishin.loader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>这个加载器专门用来加载文件系统中的 .properties 配置文件，并返回 Map 集合</p>
 * <p>Properties file loader</p>
 * <p>This loader is used to load .properties file in file System</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 20:11:03
 */
public class FileSystemPropertiesLoader extends InputStreamPropertiesLoader {

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    public FileSystemPropertiesLoader(String resourceFileName) {
        super(resourceFileName);
    }

    @Override
    protected InputStream getInputStream(String resourceFileName) throws IOException {
        return Files.newInputStream(Paths.get(resourceFileName));
    }
}
