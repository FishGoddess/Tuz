package cn.com.fishin.core;

import cn.com.fishin.helper.LogHelper;
import cn.com.fishin.helper.NamespaceHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>整个项目的核心类</p>
 * <p>The core of the whole project</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 14:20:32
 */
public class Tuz {

    // 存储所有资源文件，每一个元素都是一个资源文件
    // Store all resources, every element is a resource
    private static final Map<String, Map<String, Object>> resources = new HashMap<>();

    /**
     * <p>加载一个资源文件</p>
     * <p>Load a resource</p>
     *
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     *
     * @param resource <p>要被加载的资源文件</p>
     *                 <p>The resource to be loaded</p>
     *
     * @throws IOException <p>找不到资源文件就会抛出这个异常</p>
     *                     <p>The resource is not found</p>
     */
    public static void load(String namespace, Loadable resource) throws IOException {
        resources.put(namespace, resource.load());

        // 日志输出
        LogHelper.debug("Namespace [" + namespace + "] loaded resource ===> " + resources);
    }

    /**
     * <p>加载一个资源文件</p>
     * <p>Load a resource</p>
     *
     * @param resource  <p>要被加载的资源文件</p>
     *                  <p>The resource to be loaded</p>
     *
     * @throws IOException <p>找不到资源文件就会抛出这个异常</p>
     *                     <p>The resource is not found</p>
     */
    public static void load(Loadable resource) throws IOException {
        load(NamespaceHelper.generateNamespace(), resource);
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>fetch the value of the key</p>
     *
     * @param key <p>指定的 key</p>
     *            <p>The key of the value</p>
     *
     * @param namespace <p>指定的命名空间，用于区分不同的资源文件</p>
     *                  <p>Appointed namespace to different resource</p>
     *
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     *         <p>Return the value of the key, null if not found</p>
     */
    public static Object use(String key, String namespace) {
        return resources.containsKey(namespace) ? resources.get(namespace).get(key) : null;
    }

    /**
     * <p>获取指定 key 的属性值</p>
     * <p>
     *     <string>强烈注意：</string>
     *     如果不指定命名空间，当多个配置文件中的 key 值一样的时候，
     *     这个方法无法保证返回你需要的那个 key 值对应的 value！！
     *     由于哈希算法存在随机性，所以有可能返回另外一个值！！
     *     因此，不管是出于性能还是运行的正常性，强烈推荐使用自定义命名空间！:)
     * </p>
     * <p>fetch the value of the key</p>
     * <p>
     *     <strong>Notice: </strong>
     *     If you do not appoint a namespace, and you have some same key
     *     in some resources, then you may get a wrong value of the key!
     *     Due to hash, this gonna be a possible thing!
     *     So, listen to me, you are recommended to use a namespace!
     *     That's my boy:)
     * </p>
     *
     * @param key       <p>指定的 key</p>
     *                  <p>The key of the value</p>
     *
     * @return <p>返回获取到的属性值，找不到返回 null</p>
     *         <p>Return the value of the key, null if not found</p>
     */
    public static Object use(String key) {

        // 由于没有指定命名空间，所以需要遍历所有命名空间
        for (String namespace : resources.keySet()) {

            Map<String, Object> resource = resources.get(namespace);
            if (resource.containsKey(key)) {
                return resource.get(key);
            }
        }

        // 找不到返回 null
        return null;
    }
}
