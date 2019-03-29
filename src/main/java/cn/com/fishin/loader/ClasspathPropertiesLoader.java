package cn.com.fishin.loader;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>这个加载器专门用来加载类路径下的 .properties 配置文件，并返回 Map 集合</p>
 * <p>Properties file loader</p>
 * <p>This loader is used to load .properties file in classpath</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 15:31:03
 */
public class ClasspathPropertiesLoader extends InputStreamPropertiesLoader {

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName) {
        super(resourceFileName);
    }

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     * @param namespace <p>指定的命名空间</p>
     *                  <p>Appointed namespace</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName, String namespace) {
        super(resourceFileName, namespace);
    }

    @Override
    protected InputStream getInputStream(String resourceFileName) throws IOException {
        return getClass().getClassLoader().getResourceAsStream(resourceFileName);
    }
}
