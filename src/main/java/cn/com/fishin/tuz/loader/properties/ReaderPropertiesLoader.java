package cn.com.fishin.tuz.loader.properties;

import cn.com.fishin.tuz.helper.LogHelper;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>
 * 这个相比 cn.com.fishin.tuz.loader.properties.InputStreamPropertiesLoader
 * 多了一个字符集属性，可以避免非英文字符出现乱码问题，默认使用 UTF8 字符集
 *
 * </p>
 * <p>
 * 这个加载器适用于使用 Reader 加载资源文件的加载器，
 * 子类必须实现 getReader(String resourceFileName) 方法
 * </p>
 * <p>Properties file loader</p>
 * <p>
 * This loader uses Reader to load resource,
 * subclass must implement method getReader(String resourceFileName)
 * </p>
 * <p>
 *
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/29 23:55:59</p>
 */
public abstract class ReaderPropertiesLoader extends AbstractPropertiesLoader {

    // 资源文件输入端
    // the reader of this resource
    private Reader resourceReader = null;

    // 命名空间
    // the namespace of this resource
    private String namespace = null;

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
    protected ReaderPropertiesLoader(String resourceFileName, String namespace, Charset charset) {
        try {
            this.namespace = namespace;
            this.resourceReader = getReader(resourceFileName, charset);

            // 日志输出
            LogHelper.debug("Namespace [" + namespace + "] uses [" + charset + "] load resource? " + (resourceReader != null));
        } catch (IOException e) {
            LogHelper.error("ResourceInputStream create error!", e);
        }
    }

    @Override
    public String namespace() {
        return this.namespace;
    }

    @Override
    protected Properties loadProperties() throws IOException {
        // 加载配置文件
        Properties properties = new Properties();
        properties.load(resourceReader);

        // 日志输出
        LogHelper.info("Load resource ===> " + properties);
        return properties;
    }

    /**
     * <p>获得输入端</p>
     * <p>Get reader</p>
     *
     * @param resourceFileName <p>资源文件名</p>
     *                         <p>resource file name</p>
     *
     * @param charset <p>字符集</p>
     *                <p>Charset</p>
     * @return <p>文件输入端</p>
     * <p>file reader</p>
     * @throws IOException <p>输入端创建错误就会抛出这个异常</p>
     *                     <p>If the reader created failed</p>
     */
    protected abstract Reader getReader(String resourceFileName, Charset charset) throws IOException;
}
