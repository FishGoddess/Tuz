package cn.com.fishin.tuz.loader.properties;

import cn.com.fishin.tuz.core.Loader;
import cn.com.fishin.tuz.helper.LogHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>这个抽象类用于加载 .properties 文件</p>
 * <p>子类需要实现 loadProperties() 方法继而完善工作 </p>
 * <p>This class is used to load .properties file</p>
 * <p>Subclass should override loadProperties() method</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 15:35:30</p>
 */
public abstract class AbstractPropertiesLoader implements Loader {

    /**
     * <p>这个方法留给子类实现</p>
     * <p>这里不管子类从哪里获取 Properties，只要保证能获得就行了</p>
     * <p>This method is abstract, its subclass should implement</p>
     *
     * @return <p>返回一个 Properties 对象</p>
     *         <p>Return a Properties instance</p>
     * @throws IOException <p>如果加载过程出错，比如文件找不到，流创建失败等都有可能导致这个异常</p>
     * <p>If loading has error or file not found, even inputStream created failed causes this exception</p>
     */
    protected abstract Properties loadProperties() throws IOException;

    protected AbstractPropertiesLoader() {}

    @Override
    public Map<String, String> load() throws Throwable {
        return properties2Map(loadProperties());
    }

    // 获得这个配置文件中的 key 数量
    // get the number of properties key
    private int sizeOfPropertiesKey(Properties properties) {
        return properties.stringPropertyNames().size();
    }

    private int defaultSizeOfMap(Properties properties) {
        return sizeOfPropertiesKey(properties) >= 32 ? 64 : 32;
    }

    // 将一个 Properties 转换成 Map
    // you give it a Properties, and it returns a Map
    private Map<String, String> properties2Map(Properties properties) {

        // 这里原本选择使用 java.helper.concurrent.ConcurrentHashMap 来保证并发性能和安全性
        // 由于在 Tuz 中已经对操作加了读写锁，所以这里就不用 ConcurrentHashMap 了
        // 因为加了锁就没必要再多余使用 CAS 的操作了
        Map<String, String> result = new HashMap<>(defaultSizeOfMap(properties));

        // 将 Properties 属性保存在 Map 中
        for (String key : properties.stringPropertyNames()) {
            result.put(key, properties.getProperty(key));
        }

        // 日志输出
        LogHelper.debug(properties + " <=== (Properties to Map) ===> " + result);
        return result;
    }
}
