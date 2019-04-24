package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.ClasspathPropertiesLoader;

import java.io.IOException;

/**
 * <p>简单的使用例子</p>
 * <p>Simple demo</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/28 19:49:48</p>
 */
public class TuzSimpleDemo {

    public static void main(String[] args) throws IOException {

        // 加载资源文件
        // API: load(Loadable resource) throws IOException
        // test 是命名空间，后面一个是资源加载器
        // "test.properties" 文件中有一个属性：number=16

        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 当然，你也可以不指定命名空间，内部会自动生成一个命名空间
        // 不过，为了性能和正确性，还是建议您使用自定义的命名空间
        // 当你不指定命名空间时，就会使用文件名作为命名空间
        //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //String number = Tuz.use("number", "test.properties");

        // 或者，您也可以使用另外一个加载器去加载文件系统中的资源文件
        //Tuz.load(new FileSystemPropertiesLoader("Z:/test.properties", "test"));

        // 下面就是激动人心的时刻了！您可以在任意地方使用您的资源！
        // 上面提到，初始化的资源文件中有一个属性：number=16
        // 您可以在任意地方使用这个资源，像这样：
        // API: use(String key, String namespace)
        // 其中上面的代码中 number 是资源名字，test 是命名空间

        String number = Tuz.use("number", "test"); // ===> 返回 16
        System.out.println(number);

        // 同样，您可以不指定命名空间，但是这不被推荐
        // 具体原因请看 cn.com.fishin.tuz.core.Tuz.use(java.lang.String)
        // 关于用不用命名空间，完全取决于你对运行效率和开发效率的权衡
        //String number = Tuz.use("number"); // ===> 返回 16

        // 如果你需要更新重载这个资源文件，就调用这个方法
        // 注意这里的 new ClasspathPropertiesLoader("test.properties", "test") 应该要和上面 load 的一致
        //Tuz.reLoad(new ClasspathPropertiesLoader("test.properties", "test"));

        // 如果你不再需要这个资源文件，就调用这个方法
        // 注意这里的 new ClasspathPropertiesLoader("test.properties", "test") 应该要和上面 load 的一致
        //Tuz.unLoad(new ClasspathPropertiesLoader("test.properties", "test"));
    }
}
