package cn.com.fishin.tuz.core;

import java.io.IOException;
import java.util.Map;

/**
 * <p>可加载的接口</p>
 * <p>实现接口中的加载方法，以获得成为 tuz 插件的资格</p>
 * <p>Loadable interface</p>
 * <p>You can obtain the power of being a loader of tuz by implementing this interface</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 14:25:12</p>
 */
public interface Loadable extends Tuzable {

    /**
     * <p>获取这个加载资源的命名空间</p>
     * <p>Get the namespace</p>
     *
     * @return <p>这个资源的命名空间</p><p>The namespace of this resource</p>
     */
    String namespace();

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
    Map<String, String> load() throws IOException;
}
