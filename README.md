# Tuz 轻量资源（配置、对象）管理器

### 介绍
Tuz 轻量资源（配置、对象）管理器，用于管理您的资源，

比如 .properties 文件，一次加载，随处可用，并且使用非常简单！

不仅如此，您还可以轻松扩展功能，只需要简单地实现一些接口即可！


### 使用说明
```java
// 参考 test 文件夹下的 cn.com.fishin.demo.TuzSimpleDemo
public class TuzSimpleDemo {

    public static void main(String[] args) throws IOException {

        // 加载资源文件
        // API: load(String namespace, Loadable resource) throws IOException
        // test 是命名空间，后面一个是资源加载器
        // "test.properties" 文件中有一个属性：number=16
        
        Tuz.load("test", new ClasspathPropertiesLoader("test.properties"));

        // 或者，您也可以使用另外一个加载器去加载文件系统中的资源文件
        //Tuz.load("test", new FileSystemPropertiesLoader("Z:/test.properties"));

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
```