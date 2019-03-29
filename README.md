# Tuz 轻量资源（配置、对象）管理器

## 介绍
    Tuz 轻量资源（配置、对象）管理器，用于管理您的资源，

    比如 .properties 文件，一次加载，随处可用，并且使用非常简单！

    不仅如此，您还可以轻松扩展功能，只需要简单地实现一些接口即可！

官网：[Tuz 官方网站](https://www.fishin.com.cn/Tuz) (正在建立。。。)

联系方式：fishinlove@163.com

## 使用说明
#### 1. 简单的资源管理，这里主要使用 .properties 文件
```java
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
        //String number = Tuz.use("number"); // ===> 返回 16
    }
}
```

#### 2. 简单的依赖注入管理
```java
// 参考 test 文件夹下的 cn.com.fishin.demo.TuzSimpleDemo
public class TuzSimpleDemo2 {

    public static void main(String[] args) throws IOException {

        // 传统的方式使用接口：
        //xxxService service = new xxxServiceImpl();
        // 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
        // 而使用 Tuz 可以轻松做到解耦，请看下面：
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        service.say("Hello, tuz!");

        // 同样的，你可以不指定命名空间，但是，真的不推荐！！！
        //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service = DiPlugin.useInstance("xxxService", xxxService.class);
        //service.say("Hello, tuz!");
    }
}
```

#### 3. 配置设置
```java
// 参考 test 文件夹下的 cn.com.fishin.tuz.demo.TuzConfigDemo
public class TuzConfigDemo {

    public static void main(String[] args) throws IOException {

        // 我们先以 cn.com.fishin.tuz.demo.TuzSimpleDemo2 作为切入点
        // 先看原本的例子：
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
        //xxxService service = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        //service.say("Hello, Tuz!");

        // 默认情况下，创建的对象是单例模式的
        // 参考 cn.com.fishin.tuz.core.TuzConfig
        xxxService service1 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        xxxService service2 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        System.out.println(service1 == service2); // 返回 ===> true

        // 由于 Tuz 有一个默认的配置，里面有一个属性
        // 获取类的实例形式，默认是 true，也就是单例模式
        //private boolean singleton = true;
        // 你可以直接设置 Tuz 中的默认配置，但是不被推荐
        Tuz.getConfig().setSingleton(false);

        // 这样获得的对象就是多例模式的
        xxxService service3 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        xxxService service4 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        System.out.println(service3 == service4); // 返回 ===> false

        // 上面说过，你可以直接设置 Tuz 中的默认配置，但是不被推荐
        // 正确的做法是新创建一个配置对象
        TuzConfig newConfig = new TuzConfig();
        newConfig.setSingleton(true); // 设置为单例模式

        // 设置配置
        Tuz.setConfig(newConfig);

        // 这样获得的对象又是单例模式啦！
        xxxService service5 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        xxxService service6 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        System.out.println(service5 == service6); // 返回 ===> true
    }
}
```

#### 4. 更多用法开发中，你也可以轻松定制自己的插件
内置插件位于 `cn.com.fishin.tuz.plugin` 包下，常用的有 `DiPlugin` 依赖注入插件

您可以参考这个插件的实现来定制自己的插件！

## 更新日志
#### *2019-3-29:*
    1. 加入了依赖注入插件，参考 cn.com.fishin.tuz.plugin.DiPlugin
    2. 实现了文件系统资源加载器，可以从文件系统中加载资源文件了
    3. 修改 cn.com.fishin.tuz.core.Loadable 接口，使得它具有命名空间的功能

