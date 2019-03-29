package cn.com.fishin.tuz.helper;

import java.util.UUID;

/**
 * <p>命名空间帮助器</p>
 * <p>To help making a namespace</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 17:18:51</p>
 */
public class NamespaceHelper {

    // 默认命名空间，如果不指定命名空间，就使用这个默认的命名空间
    // 这里加了时间戳是为了防止和用户的命名空间重复
    // If you do not use a special namespace, this gonna be used
    // System.currentTimeMillis() is to avoid the same namespace user used
    private static final String DEFAULT_NAMESPACE_PREFIX = "Tuz_Namespace: ";

    // 生成命名空间的名字
    // Generate namespace
    public static String generateNamespace() {
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
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
