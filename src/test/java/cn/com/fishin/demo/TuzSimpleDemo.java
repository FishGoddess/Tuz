package cn.com.fishin.demo;

import cn.com.fishin.core.Tuz;
import cn.com.fishin.loader.ClasspathPropertiesLoader;

import java.io.IOException;

/**
 * <p>简单的使用例子</p>
 * <p>Simple demo</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 19:49:48
 */
public class TuzSimpleDemo {

    public static void main(String[] args) throws IOException {

        // 加载资源文件
        // API: load(String namespace, Loadable resource) throws IOException
        // test 是命名空间，后面一个是资源加载器
        // "test.properties" 文件中有一个属性：number=16

        Tuz.load("test", new ClasspathPropertiesLoader("test.properties"));

        // 当然，你也可以不指定命名空间，内部会自动生成一个命名空间
        // 不过，为了性能和正确性，还是建议您使用自定义的命名空间
        //Tuz.load(new ClasspathPropertiesLoader("test2.properties"));

        // 下面就是激动人心的时刻了！您可以在任意地方使用您的资源！
        // 上面提到，初始化的资源文件中有一个属性：number=16
        // 您可以在任意地方使用这个资源，像这样：
        // API: use(String key, String namespace)
        // 其中上面的代码中 number 是资源名字，test 是命名空间

        Tuz.use("number", "test"); // ===> 返回 16

        // 同样，您可以不指定命名空间，但是这不被推荐
        // 具体原因请看 cn.com.fishin.core.Tuz.use(java.lang.String)
        //Tuz.use("number"); // ===> 返回 16
    }
}