package cn.com.fishin.tuz.loader;

import cn.com.fishin.tuz.helper.IOHelper;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>这个加载器专门用来加载类路径下的 .properties 配置文件，并返回 Map 集合</p>
 * <p>Properties file loader</p>
 * <p>This loader is used to load .properties file in classpath</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 15:31:03</p>
 */
public class ClasspathPropertiesLoader extends ReaderPropertiesLoader {

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName) {
        super(resourceFileName, resourceFileName, StandardCharsets.UTF_8);
    }

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     * @param namespace        <p>指定的命名空间</p>
     *                         <p>Appointed namespace</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName, String namespace) {
        super(resourceFileName, namespace, StandardCharsets.UTF_8);
    }

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     * @param namespace        <p>指定的命名空间</p>
     *                         <p>Appointed namespace</p>
     * @param charset          <p>读取资源的字符集</p>
     *                         <p>The charset of this resource</p>
     */
    public ClasspathPropertiesLoader(String resourceFileName, String namespace, Charset charset) {
        super(resourceFileName, namespace, charset);
    }

    @Override
    protected Reader getReader(String resourceFileName, Charset charset) throws IOException {
        return IOHelper.newReaderToClasspath(resourceFileName, charset);
    }
}
