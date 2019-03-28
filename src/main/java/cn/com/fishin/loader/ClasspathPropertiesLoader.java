package cn.com.fishin.loader;

import cn.com.fishin.helper.LogHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>这个加载器专门用来加载 .properties 配置文件，并返回 Map 集合</p>
 * <p>Properties file loader</p>
 * <p>This loader is used to load .properties file</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 15:31:03
 */
public class ClasspathPropertiesLoader extends AbstractPropertiesLoader {

    // 资源文件输入流
    // the inputStream of resource
    private InputStream resourceInputStream = null;

    /**
     * <p>传入一个资源文件名，这个文件必须是在 classpath 下面</p>
     * <p>The resourceFile must be put on classpath</p>
     *
     * @param resourceFileName <p>资源文件名</p>
     *                         <p>The name of resource</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName) {
        super();
        this.resourceInputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);

        // 日志输出
        LogHelper.debug("Resource inputStream ===> " + resourceInputStream);
    }

    @Override
    protected Properties loadProperties() throws IOException {

        // 加载配置文件
        Properties properties = new Properties();
        properties.load(resourceInputStream);

        // 日志输出
        LogHelper.info("Load resource ===> " + properties);
        return properties;
    }
}
