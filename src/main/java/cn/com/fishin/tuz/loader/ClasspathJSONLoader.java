package cn.com.fishin.tuz.loader;

import cn.com.fishin.tuz.helper.IOHelper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>JSON 配置文件加载器</p>
 * <p>这个加载器专门用来加载类路径下的 json 配置文件，并返回 Map 集合</p>
 * <p>JSON file loader</p>
 * <p>This loader is used to load json file in classpath</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 19:05:11</p>
 */
public class ClasspathJSONLoader extends ReaderJSONLoader {

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    public ClasspathJSONLoader(String resourceFileName) {
        this(resourceFileName, resourceFileName, StandardCharsets.UTF_8);
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
    public ClasspathJSONLoader(String resourceFileName, String namespace) {
        this(resourceFileName, resourceFileName, StandardCharsets.UTF_8);
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
    public ClasspathJSONLoader(String resourceFileName, String namespace, Charset charset) {
        super(IOHelper.getResourceFromClasspath(resourceFileName), namespace, charset);
    }

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     * @param charset          <p>读取资源的字符集</p>
     *                         <p>The charset of this resource</p>
     */
    public ClasspathJSONLoader(String resourceFileName, Charset charset) {
        this(resourceFileName, resourceFileName, charset);
    }
}
