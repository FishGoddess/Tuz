package cn.com.fishin.tuz.loader.json;

import cn.com.fishin.tuz.helper.IOHelper;
import cn.com.fishin.tuz.helper.LogHelper;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * <p>JSON 配置文件加载器</p>
 * <p>默认使用 UTF8 字符集</p>
 * <p>
 * 这个加载器适用于使用 Reader 加载资源文件的加载器，
 * 子类必须实现 getReader(String resourceFileName) 方法
 * </p>
 * <p>JSON file loader</p>
 * <p>
 * This loader uses Reader to load resource,
 * subclass must implement method getReader(String resourceFileName)
 * </p>
 * <p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 18:41:41</p>
 */
public abstract class ReaderJSONLoader extends AbstractJSONLoader {

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
    protected ReaderJSONLoader(Path resourceFileName, String namespace, Charset charset) {
        super(IOHelper.linesOf(resourceFileName, charset));
        this.namespace = namespace;

        // 日志输出
        LogHelper.debug("Namespace [" + namespace + "] uses [" + charset + "] load resource!");
    }

    @Override
    public String namespace() {
        return this.namespace;
    }
}
