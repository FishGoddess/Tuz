package cn.com.fishin.loader;

import cn.com.fishin.helper.LogHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Properties 配置文件加载器</p>
 * <p>这个加载器用于使用 InputStream 加载，子类必须实现</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 20:35:59
 */
public abstract class InputStreamPropertiesLoader extends AbstractPropertiesLoader {

    // 资源文件输入流
    // the inputStream of resource
    private InputStream resourceInputStream = null;

    /**
     * <p>根据资源文件名称构建</p>
     * <p>Construct with resource file</p>
     *
     * @param resourceFileName <p>资源文件名称</p>
     *                         <p>resource file</p>
     */
    protected InputStreamPropertiesLoader(String resourceFileName) {
        try {
            this.resourceInputStream = getInputStream(resourceFileName);

            // 日志输出
            LogHelper.debug("Resource inputStream ===> " + resourceInputStream);
        } catch (IOException e) {
            LogHelper.error("ResourceInputStream create error!", e);
        }
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
