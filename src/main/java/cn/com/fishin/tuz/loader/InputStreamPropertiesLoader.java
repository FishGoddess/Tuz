package cn.com.fishin.tuz.loader;

import cn.com.fishin.tuz.helper.LogHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>有可能造成字符乱码问题！！已废弃！！</p>
 * <p>
 *     这个加载器适用于使用 InputStream 加载资源文件的加载器，
 *     子类必须实现 getInputStream(String resourceFileName) 方法
 * </p>
 * <p>Properties file loader</p>
 * <p>It may cause charset problem!! Deprecated!!</p>
 * <p>
 *     This loader uses inputStream to load resource,
 *     subclass must implement method getInputStream(String resourceFileName)
 * </p>
 *
 * @see cn.com.fishin.tuz.loader.ReaderPropertiesLoader
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 20:35:59</p>
 */
@Deprecated
public abstract class InputStreamPropertiesLoader extends AbstractPropertiesLoader {

    // 资源文件输入流
    // the inputStream of resource
    private InputStream resourceInputStream = null;
    private String namespace = null;

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    protected InputStreamPropertiesLoader(String resourceFileName) {
        try {
            this.namespace = resourceFileName;
            this.resourceInputStream = getInputStream(resourceFileName);

            // 日志输出
            LogHelper.debug("Namespace [" + namespace + "] resource inputStream ===> " + resourceInputStream);
        } catch (IOException e) {
            LogHelper.error("ResourceInputStream create error!", e);
        }
    }

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     *
     * @param namespace <p>指定的命名空间</p>
     *                  <p>Appointed namespace</p>
     */
    protected InputStreamPropertiesLoader(String resourceFileName, String namespace) {
        try {
            this.namespace = namespace;
            this.resourceInputStream = getInputStream(resourceFileName);

            // 日志输出
            LogHelper.debug("Namespace [" + namespace + "] resource inputStream ===> " + resourceInputStream);
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
        properties.load(resourceInputStream);

        // 日志输出
        LogHelper.info("Load resource ===> " + properties);
        return properties;
    }

    /**
     * <p>获得输入流</p>
     * <p>Get inputStream</p>
     *
     * @param resourceFileName <p>资源文件名</p>
     *                         <p>resource file name</p>
     *
     * @return <p>文件输入流</p>
     *         <p>file inputStream</p>
     * @throws IOException <p>输入流创建错误就会抛出这个异常</p>
     *                     <p>If the inputStream created failed</p>
     */
    protected abstract InputStream getInputStream(String resourceFileName) throws IOException;
}
