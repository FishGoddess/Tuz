package cn.com.fishin.core;

import java.io.IOException;
import java.util.Map;

/**
 * <p>可加载的接口</p>
 * <p>实现接口中的加载方法，以获得成为 Tuz 插件的资格</p>
 * <p>Loadable interface</p>
 * <p>You can obtain the power of being a loader of Tuz by implementing this interface</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 14:25:12
 */
public interface Loadable extends Tuzable {

    /**
     * <p>加载资源，主要是配置文件</p>
     * <p>返回一个 Map 集合，键值就是 key，而 value 就是键值对应的属性值</p>
     * <p>Load resources</p>
     * <p>Return a Map Collection, the key is the key, and the value is the attribute of the key</p>
     *
     * @return <p>注意这个集合的泛型是 &lt;String, Object&gt; 的</p>
     *         <p>Notice: The map is String and Object</p>
     *
     * @throws IOException <p>找不到资源文件就会抛出这个异常</p>
     *                     <p>The resource is not found</p>
     */
    Map<String, Object> load() throws IOException;
}
