package cn.com.fishin.tuz.helper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>命名空间帮助器</p>
 * <p>To help making a namespace</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 17:18:51</p>
 */
public class NameSpaceHelper {

    // 默认命名空间，如果不指定命名空间，就使用这个默认的命名空间
    // 这里加了时间戳是为了防止和用户的命名空间重复
    // If you do not use a special namespace, this gonna be used
    // System.currentTimeMillis() is to avoid the same namespace user used
    private static final String DEFAULT_NAMESPACE_PREFIX = "Tuz_Namespace: ";

    // 计数器，用来记录命名空间的个数，同时也是命名空间的后缀
    // Namespace counter, also the suffix of namespace
    private static final AtomicInteger counter = new AtomicInteger(0);

    // 生成命名空间的名字
    // Generate namespace
    public static String generateNameSpace() {
        return namespacePrefix() + namespaceSuffix();
    }

    // 获取命名空间的前缀
    // Get the prefix of namespace
    private static String namespacePrefix() {
        return DEFAULT_NAMESPACE_PREFIX;
    }

    // 获取命名空间的后缀
    // Get the suffix of namespace
    private static String namespaceSuffix() {

        // 一开始使用的是 UUID 生成的命名空间，但是这样太随机的
        // 如果用户不想使用默认的文件名作为命名空间，但是在使用的时候又不想指定命名空间
        // 使用这个生成器生成的命名空间就必须具有一致性，比如按顺序递增
        // 于是就改为了使用

        //return UUID.randomUUID().toString().replaceAll("-", "");
        return String.valueOf(counter.incrementAndGet());
    }
}
