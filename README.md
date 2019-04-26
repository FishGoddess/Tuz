# Tuz 轻量级资源容器 [![Maven Central](./maven_central.svg)](https://mvnrepository.com/artifact/cn.com.fishin/Tuz) [![License](./license.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

+ 资源属性管理容器： 将资源以键值对的形式加载到内存，实现资源的轻松加载使用
+ 资源透明化容器：同样的使用方式，不一样的加载器，可以达到资源使用的透明化
+ 轻量依赖注入容器： 将对象实现类直接注入到对象引用中，实现业务的实现解耦
+ 对象方法拦截容器： 使用动态代理进行方法的拦截器，实现业务的主次解耦
+ IO 操作工具箱： 提供了常用的 IO 操作方法，操作 IO 更方便
+ 网络管理工具箱：  提供了 FTP 上传的功能，从此上传文件变得轻而易举

## 介绍 -- Introduce
    Tuz 轻量级资源管理器，用于管理您的资源，

    比如 .properties 和 JSON 文件，一次加载，随处可用，并且使用非常简单！
    
    同时还支持 Redis 资源加载以及操作，在资源使用上完全透明化！

    不仅如此，您还可以轻松扩展功能，只需要简单地实现一些接口即可！
    
    内置的依赖注入插件可以让你轻松将接口和实现类解耦，彻底进行优雅的开发！
    
    后续会加入更多资源管理的功能或插件，敬请期待！

    Tuz, a small resource manager.
    
    For example, you could load some .properties files, and use them everywhere!
    
    You could extend it by implementing some interface!
    
    Also, I have prepared some building plgins like DiPlugin.
    
    It will be perfect as time going by! 

---
+ #### Tuz 文档：[https://www.fishin.com.cn/Tuz](https://www.fishin.com.cn/Tuz)

+ #### 联系方式：fishinlove@163.com

+ #### 开源协议：[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)
---

## 如何使用 -- How to use
+ 如果您正在使用 Maven 工程，那么这一切都变得很简单了，你只需要在你的 pom.xml 中引入：
+ If you are using Maven project, then everything becomes easier, you only need add some lines in your pom.xml:
```xml
<!-- https://mvnrepository.com/artifact/cn.com.fishin/Tuz -->
<dependency>
    <groupId>cn.com.fishin</groupId>
    <artifactId>Tuz</artifactId>
    <version>0.7.4-FINAL</version>
</dependency>
```
---
+ 当然，你也可能使用的是 Gradle 工程，这样你只需要在相应的构建管理配置文件里面加入：
+ Also, you may use a Gradle project, do the same thing as a Maven project:
```groovy
// https://mvnrepository.com/artifact/cn.com.fishin/Tuz
compile group: 'cn.com.fishin', name: 'Tuz', version: '0.7.4-FINAL'
```
其他构建工具或者仓库请参考 `Maven 中心仓库`的写法，如果您不知道网址是啥，请点击
上方的 `Maven Central` 图标或者点击 [这个链接](https://mvnrepository.com/artifact/cn.com.fishin/Tuz)。

See more info in [this website](https://mvnrepository.com/artifact/cn.com.fishin/Tuz).

---
+ 或者，您使用的是普通 Java 项目，需要手动引入 lib 框架。
我们建议您迁移到 Maven 工程，如果不想切换，
可以到 [Tuz - GitHub](https://github.com/FishGoddess/Tuz/releases) 下载你需要版本的 Jar 包文件以及文档和源码。

+ 我们提供了两种 jar 可供使用，一种是不带任何依赖的，一种是带了完整依赖的，请选择一种下载。

+ Or worse, you are using a traditional Java project with adding Jar manually,
then you should change your developing way or download a Jar from [Tuz - GitHub](https://github.com/FishGoddess/Tuz/releases).

+ We provide two versions of jar, one is small but not completed, one is completed but not small, you should choose one of them.
---

## 参考例子 -- Example
#### 1. 简单的资源管理，这里主要使用 .properties 文件
```java
public class TuzSimpleDemo {

    public static void main(String[] args) throws Throwable {

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
```

#### 2. 简单的依赖注入管理
```java
// 参考 test 文件夹下的 cn.com.fishin.demo.TuzSimpleDemo
public class TuzSimpleDemo2 {

    public static void main(String[] args) throws Throwable {

        // 传统的方式使用接口：
        //xxxService service = new xxxServiceImpl();
        // 这种方式并没有真正解耦，使用 Spring 这类框架可以达到解耦效果，但是需要引入大量框架
        // 而使用 Tuz 可以轻松做到解耦，请看下面：
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service1 = DiPlugin.useInstance("xxxService", "test", xxxService.class);
        service1.say("Hello, tuz!");

        // 你也许会觉得上面的方法有些参数多余了
        // 对没错，就是 xxxService 和 xxxService.class
        // 当我的 key 和类的名字一样的时候，其实这个 key 值是可以省略的
        // 注意这里的 xxxService 在配置文件中的 key 就是 xxxService
        xxxService service2 = DiPlugin.useInstance(xxxService.class);
        service2.say("Hello, tuz!");

        // 同样的，你可以不指定命名空间，但是，这样会导致多一次的查找！
        //Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service3 = DiPlugin.useInstance("xxxService", xxxService.class);
        //service3.say("Hello, Tuz!");
    }
}
```

#### 3. 简单的资源管理，这里主要使用 JSON 文件
```java
public class TuzSimpleDemo3 {

    public static void main(String[] args) throws Throwable {

        // 加载这个 JSON 文件
        // 加载一次之后，你就可以在全局中随意使用啦
        Tuz.load(new ClasspathJSONLoader("test.json"));

        // 建议给这个文件起一个命名空间，如果没有手动指定，Tuz 在内部会使用文件名作为命名空间
        // Tuz.load(new ClasspathJSONLoader("test.json", "test"));

        // 当然，除了加载类路径下的资源文件，还可以加载文件系统中的资源
        //Tuz.load(new FileSystemJSONLoader("E:\\JavaProject\\Tuz\\src\\test\\resources\\test.json"));

        // 由于默认使用 UTF8 的字符编码，所以如果你的文件不是 UTF8 编码，就会出现乱码问题
        // 这时候需要自己手动指定这个文件的字符编码
        //Tuz.load(new ClasspathJSONLoader("test.json", StandardCharsets.ISO_8859_1));

        /*
        {
            "status": 0,
            "message": "success",
            "ok": true,
            "data": {
                "title": {
                    "id": "001",
                    "name": "test",
                    "null": {}
                },
                "content": [
                    {
                        "id": "001",
                        "value": "hello 001",
                        "arr": [1, "非你莫属尽快发你说的", true]
                    },
                    {
                        "id": "002",
                        "value": "hello 002"
                    }
                ]
             }
         }
         */

        // 如果你需要引用这个 JSON 中的值，就像下面这样引用即可
        // 是不是很方便？哈哈哈 ^_^
        System.out.println(Tuz.use("status")); // ===> 0
        System.out.println(Tuz.use("message")); // ===> success
        System.out.println(Tuz.use("ok")); // ===> true
        System.out.println(Tuz.use("data")); // ===> {"title":{"null":{},"name":"test","id":"001"},"content":[{"arr":[1,"非你莫属尽快发你说的",true],"id":"001","value":"hello 001"},{"id":"002","value":"hello 002"}]}
        System.out.println(Tuz.use("data.title")); // ===> {"null":{},"name":"test","id":"001"}
        System.out.println(Tuz.use("data.title.id")); // ===> 001
        System.out.println(Tuz.use("data.title.name")); // ===> test
        System.out.println(Tuz.use("data.title.null")); // ===> {}
        System.out.println(Tuz.use("data.content")); // ===> [{"arr":[1,"非你莫属尽快发你说的",true],"id":"001","value":"hello 001"},{"id":"002","value":"hello 002"}]
        System.out.println(Tuz.use("data.content[0].id")); // ===> 001
        System.out.println(Tuz.use("data.content[0].value")); // ===> hello 001
        System.out.println(Tuz.use("data.content[0].arr")); // ===> [1,"bfdjhsb",true]
        System.out.println(Tuz.use("data.content[0].arr[0]")); // ===> 1
        System.out.println(Tuz.use("data.content[0].arr[1]")); // ===> bfdjhsb
        System.out.println(Tuz.use("data.content[0].arr[2]")); // ===> true
        System.out.println(Tuz.use("data.content[1].id")); // ===> 002
        System.out.println(Tuz.use("data.content[1].value")); // ===> hello 002

        // 当然，如果你有多个 JSON 文件都有同样的键值，就需要加上命名空间了
        // 这里的 test 就是命名空间，在调用 Tuz.load 时就需要指定
        //Tuz.load(new ClasspathJSONLoader("test.json", "test"));
        //System.out.println(Tuz.use("status", "test")); // ===> 0

        // 当然，如果你是在程序中使用到了一个 JSON 字符串，也可以使用 AbstractJSONLoader 加载使用
        final String jsonString = "{\"title\":{\"null\":{},\"name\":\"test\",\"id\":\"001\"},\"content\":[{\"arr\":[1,\"非你莫属尽快发你说的\",true],\"id\":\"001\",\"value\":\"hello 001\"},{\"id\":\"002\",\"value\":\"hello 002\"}]}";
        Tuz.load(new AbstractJSONLoader(jsonString) {
            @Override
            public String namespace() {
                return "program";
            }
        });

        System.out.println(Tuz.use("content[0].arr")); // ===> [1,"非你莫属尽快发你说的",true]
    }
}
```

#### 4. 配置自定义的设置
```java
// 参考 test 文件夹下的 cn.com.fishin.tuz.demo.TuzConfigDemo
public class TuzConfigDemo {

    public static void main(String[] args) throws Throwable {

        // 我们先以 cn.com.fishin.tuz.demo.TuzSimpleDemo2 作为切入点
        // 先看原本的例子：
        Tuz.load(new ClasspathPropertiesLoader("test.properties"));
        //xxxService service = DiPlugin.useInstance(xxxService.class);
        //service.say("Hello, Tuz!");

        // 默认情况下，创建的对象是单例模式的
        // 参考 cn.com.fishin.tuz.core.TuzConfig
        xxxService service1 = DiPlugin.useInstance(xxxService.class);
        xxxService service2 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service1 == service2); // 返回 ===> true

        // 由于 Tuz 有一个默认的配置，里面有一个属性
        // 获取类的实例形式，默认是 true，也就是单例模式
        //private boolean singleton = true;
        // 你可以直接设置 Tuz 中的默认配置，但是不被推荐
        Tuz.getConfig().setSingleton(false);

        // 这样获得的对象就是多例模式的
        xxxService service3 = DiPlugin.useInstance(xxxService.class);
        xxxService service4 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service3 == service4); // 返回 ===> false

        // 上面说过，你可以直接设置 Tuz 中的默认配置，但是不被推荐
        // 正确的做法是新创建一个配置对象
        TuzConfig newConfig = new TuzConfig();
        newConfig.setSingleton(true); // 设置为单例模式

        // 设置配置
        Tuz.setConfig(newConfig);

        // 这样获得的对象又是单例模式啦！
        xxxService service5 = DiPlugin.useInstance(xxxService.class);
        xxxService service6 = DiPlugin.useInstance(xxxService.class);
        System.out.println(service5 == service6); // 返回 ===> true
    }
}
```

#### 5. 配合 Spring/SpringBoot 来使用
```java
//@SpringBootApplication
public class TuzSpringBootDemo  {

    /*
    * 这个类仅仅是演示与 SpringBoot 一起使用的例子，并不代表这是唯一用法
    *
    * 曾经，在使用 Spring 的时候，配置文件可以做到不改代码更改实现类的功能，
    * 后来推出了 SpringBoot，的确简化了大量的配置文件操作，而且对于微服务来说，
    * 更改实现类往往意味着服务的改变，所以当发生需要更改需求而更改代码的事情的时候，
    * 我们往往可以理解和接受，而且就算是要做到从 .properties 配置文件中读取配置
    * 从而达到不更改代码更改实现类的目的也不难，毕竟 Spring 读取配置文件已经很简单，
    * 但是这些业务其实是重复而且有意义的，这时候 Tuz 就体现出很好的复用和业务性了
    * */

    // 你可以选择使用 static 块初始化 Tuz
    // 当然你也可以选择在 main 方法中初始化
    static {
        try {
            Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));
        } catch (Throwable t) {
            // Do something...
            System.err.println(t.getMessage());
        }
    }

    public static void main(String[] args) {

        // 除了在 static {} 中初始化，你也可以在这里初始化，甚至可以配合参数 args
        // 做到选择不同的环境去初始化 Tuz
        //Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 这是 SpringBoot 的用法，这里不作讨论
        //SpringApplication.run(TuzSpringBootDemo.class, args);
    }

    // Spring 初始化 Bean
    //@Bean
    public xxxService getXxxService() {

        // 直接返回 xxxService 即可
        // 这样，你只需在 test.properties 中更改这个类的实现即可
        // 不需要更改代码，也不需要写多余的代码
        return DiPlugin.useInstance(xxxService.class);
    }
}
```

#### 6. 上传到 FTP 服务器
```java
public class FTPUploadDemo {

    public static void main(String[] args) throws Throwable {

        // 加载资源
        Tuz.load(new ClasspathPropertiesLoader("test2.properties", "test2"));

        // 注意：这个必须要在 load 方法执行之后执行，具体原因请查看
        Tuz.init();

        // 上传到 FTP 服务器
        // 当然你可能会觉得这么使用很麻烦！
        // 正常我们使用 FTP 上传的时候，多数是上传一个二进制文件
        // 因此您可以直接使用 NetPlugin.
        NetPlugin.uploadToServer(FTPUploadFileFactory.makeAsciiFile("YourDirection", "YourFile",
                    IOHelper.newInputStreamToFileSystem("Z:/YourFile")));

        // 如果您正在使用 Servlet 或者是 SpringMVC，您可以使用这个方法上传一个二进制文件
        //NetPlugin.uploadBinaryToServer("YourDirection", "YourFile", multipartFile.getInputStream);
    }
}
```

#### 7. 使用拦截器拦截方法达到业务主次解耦
```java
public class ProxySimpleDemo {

    /*
        // 在 test 包中有使用这个 ProxyFactory.wrap 方法的案例
        // 但是不建议直接使用这个方法，因为这个方法更多是一个工厂方法，提供内部使用
        SimpleClass simpleClass = new SimpleClass();

        SimpleClass simpleClass1 = (SimpleClass) ProxyFactory.wrap(simpleClass,
                new InterceptorInvocationHandler(simpleClass, new Interceptor[]{
                        new TestInterceptor(),
                        new TestInterceptor(),
                        new TestInterceptor()
                }));

        simpleClass1.test();
    */

    public static void main(String[] args) throws Throwable {

        // 同样的，先加载资源文件，这样可以获取到具体的实现类
        Tuz.load(new ClasspathPropertiesLoader("test.properties", "test"));

        // 直接获取实现类，而不用注入实现类的细节
        xxxService service = DiPlugin.useInstance(xxxService.class);

        // 以上演示了最单纯的使用依赖注入的方法
        // 但是这时候你来了新需求，需要在原本的基础之上加入两个需求：
        // 1. 现在的系统加入了 redis 当缓存系统，在调用方法之前先在缓存系统找
        //    找不到在从数据库找，并且将数据加载到缓存系统
        // 2. 需要在业务方法上都加入日志记录功能
        // 以上两点都是次要业务，不建议直接写入到当前已存在的业务中或者你无法改动现有代码
        // 这时候你就需要使用面向切面的思想去更新你的业务类
        // Tuz 中使用拦截器来达到这个目的
        // 顶级拦截器接口：cn.com.fishin.tuz.interceptor.Interceptor
        // 但是不建议直接使用这个拦截器，建议使用 cn.com.fishin.tuz.interceptor.DefaultInterceptor
        // 然后选择性地复写特定方法来达到特定业务功能
        // 不推荐直接使用这个工厂类，建议使用 cn.com.fishin.tuz.plugin.ProxyPlugin 中的方法来获得代理之后的对象
        /*
        xxxService xxxService = ProxyPlugin.useInstance(xxxService.class,
                new Interceptor[]{
                        new CacheInterceptor(),
                        new LogInterceptor()
        });
        */
        // 注意：当这个类可以被继承时，使用 CGlib 来实现动态代理
        // 如果不可以被继承，就使用 JDK 来动态代理，这时要求该类必须实现接口，而且使用接口来接收
        xxxService serviceProxy = ProxyPlugin.useInstance(xxxService.class, new Interceptor[]{
                new LogInterceptor(), // 日志拦截器
                new CacheInterceptor() // 缓存拦截器
        });

        // 这是一个对比
        System.out.println("====================== 使用拦截器之前的业务 ===================");
        service.say("没用拦截器...");
        System.out.println("====================== 使用拦截器之前的业务 ===================");

        System.out.println("====================== 使用拦截器之后的业务 ===================");
        serviceProxy.say("使用拦截器...");
        System.out.println("====================== 使用拦截器之后的业务 ===================");

        // 这是第二个对比
        System.out.println("====================== 使用拦截器之前的业务 ===================");
        service.hung(18, 16);
        System.out.println("====================== 使用拦截器之前的业务 ===================");

        System.out.println("====================== 使用拦截器之后的业务 ===================");
        serviceProxy.hung(81, 61);
        System.out.println("====================== 使用拦截器之后的业务 ===================");

        /*
        // 执行结果：
        ====================== 使用拦截器之前的业务 ===================
        没用拦截器...
        ====================== 使用拦截器之前的业务 ===================
        ====================== 使用拦截器之后的业务 ===================
        日志记录正常操作 ===> public void cn.com.fishin.tuz.demo.xxxServiceImpl.say(java.lang.String)
        在缓存中查找数据：[Ljava.lang.Object;@29774679
        缓存中找不到数据！
        使用拦截器...
        加载数据 null 到缓存...
        ====================== 使用拦截器之后的业务 ===================
        ====================== 使用拦截器之前的业务 ===================
        18 hung 16
        ====================== 使用拦截器之前的业务 ===================
        ====================== 使用拦截器之后的业务 ===================
        日志记录正常操作 ===> public void cn.com.fishin.tuz.demo.xxxServiceImpl.hung(int,int)
        在缓存中查找数据：[Ljava.lang.Object;@26653222
        缓存中找到了数据！
        ====================== 使用拦截器之后的业务 ===================
        */

        // 需要注意的是，由于使用的是 CGlib，所以被代理的类不能是 final 修饰的
        // 也就是说必须要可以被继承，因为 CGlib 就是使用 ASM 产生子类和多态来达到动态代理的效果的
        // 另外，动态代理产生的实例是不是单例的和 Tuz 的配置有关
        // 详情请参考 cn.com.fishin.tuz.core.TuzConfig.isSingleton
        // 默认情况下是单例模式的，也就是说不管你创建多少代理对象都是同一个
        // 如果需要多例的模式，可以修改 Tuz 的配置，调用 cn.com.fishin.tuz.core.TuzConfig.setSingleton
    }
}
```

#### 8. Redis 资源加载器
```java
public class TuzSimpleDemo4 {

    public static void main(String[] args) throws Throwable {

        // 使用 RedisLoader 来加载 Redis 上的资源
        // 注意这个加载操作其实并不是真的加载
        // 而只是创建一个连接池，每一次获取数据都从网络中获取
        // 后面会考虑加入缓存，将得到的值暂时缓存，可以大幅度提高短时间大量读取的性能
        // 注意：这里没有指定命名空间，默认使用 192.168.32.129:6379
        // 如果你指定了数据库编号，那就默认为 192.168.32.129:6379[0]
        final String namespace = "192.168.32.129:6379";
        Tuz.load(new RedisLoader("192.168.32.129", 6379));

        // 下面你就可以像使用普通资源一样使用 redis 上的资源了
        // 你可以向资源池加入新资源
        // 也就是向 redis 中存储数据，比如你可以添加一对数据
        // 注意：这里指定命名空间为 192.168.32.129:6379
        Tuz.appendResource("key", "哈哈哈", namespace);

        // 当然，你可能想添加一堆数据
        // 这里可以使用批量添加，内部使用 pipeline 管道来批量新增，
        // 所以不用担心批量新增会造成大量网络堵塞
        Tuz.appendResource(manyEntries(), namespace);

        // 当你确保已经存在数据时，就可以正常使用了
        System.out.println("key ===> " + Tuz.use("key"));
        System.out.println("key1 ===> " + Tuz.use("key1"));
        System.out.println("key2 ===> " + Tuz.use("key2"));

        // 当然，对于资源的管理，处理使用之外，还有写操作
        // 比如，这个资源已经不需要了，就可以移除掉
        System.out.println("key3 ===> " + Tuz.use("key3"));
        System.out.println(Tuz.unUse("key3", namespace));
        System.out.println("key3 ===> " + Tuz.use("key3"));

        // 如果你已经不需要使用这个资源库，就可以考虑卸载这个资源库了
        Tuz.unLoad(namespace);
        System.out.println("key ===> " + Tuz.use("key"));

        // 释放资源
        Tuz.destroy();
    }

    // 准备一堆数据
    private static Map<String, String> manyEntries() {
        Map<String, String> entries = new HashMap<>(8);
        for (int i = 0; i < 4; i++) {
            entries.put("key" + i, "value" + i);
        }
        return entries;
    }
}
```

#### 9. 更多用法开发中，你也可以轻松定制自己的插件
内置插件位于 `cn.com.fishin.tuz.plugin` 包下，常用的有 `DiPlugin` 依赖注入插件

您可以参考这个插件的实现来定制自己的插件！

目前内置两种资源加载器，你可以自定义自己的加载器，只需要简单实现 `cn.com.fishin.tuz.core.Loadable` 接口即可

更多使用演示案例，请参见 test 模块下的 cn.com.fishin.tuz.demo

## 方法说明 -- method book
以下列举的仅仅是部分可供您使用的方法，还有一部分没有列举出来，可能需要您慢慢探索了:)
The methods below are some of usable methods, the others need your discovery:)
+ #### core 包（核心包）
    + Tuz (Class)：核心系统
        + 方法列表 ↓
        + load：加载资源，初始化 Tuz
        + use：使用资源，可以获取到加载过的资源
        + useGracefully：优雅地使用资源，当找不到资源时返回自定义的默认值
    + TuzConfig (Class)：配置类
        + 方法列表 ↓
        + isSingleton：获得类实例生成方式，默认是单例
        + setSingleton：设置类实例生成方式，可选单例或多例
    + Loadable (Interface)：加载器接口
        + 方法列表 ↓
        + namespace：获得命名空间
        + load：加载资源
    + Tuzable (Interface)：这是一个信仰，没有任何方法:)
    
+ #### helper 包（工具包）
    + ClassHelper (Class)：类操作工具包
        + 方法列表 ↓
        + newInstance：生成类对象实例
    + IOHelper (Class)：IO 操作工具包
        + 方法列表 ↓
        + newReader：获得一个指向某个资源的读取器
        + newReaderToFileSystem：获得一个指向文件系统的某个资源的读取器
        + newReaderToClasspath：获得一个指向类路径的某个资源的读取器
        + getResourceFromFileSystem：获得一个指向文件系统的某个资源的路径
        + getResourceFromClasspath：获得一个指向类路径的某个资源的路径
    + LogHelper (Class)：日志操作工具包
        + 方法列表 ↓
        + 就常用的 debug/info/warn/error 等日志记录方法呗
    + NameSpaceHelper (Class)：命名空间工具包
        + 方法列表 ↓
        + generateNameSpace：生成命名空间名字，默认从 1 开始生成
    
+ #### plugin 包（插件包）
    + DiPlugin (Class)：依赖注入插件
        + 方法列表 ↓
        + useInstance：使用一个类对象实例

## 友情链接 -- as a friend
+ [Kiter 集成工具类 @ 来自王老板的奇思妙想](https://github.com/ChickenWinner/kiter)
+ [knapsack @ 来自黄少爷的财大气粗](https://github.com/Mackyhuang/knapsack)
+ [vue - cms @ 来自软大师的吊儿郎当](https://gitee.com/mdaovo/vue-cms)

## 更新日志 -- update log
#### *2019-4-26:*
    1. 重新设计 LockTemplate 模板类，使用更加简洁
    2. Tuz 类在写入资源的操作中抛出的异常变为 Throwable，注意注意！！！
    3. 加入 Redis 资源加载器，可以非常简单透明地操作 redis 资源

#### *2019-4-25:*
    1. 微调拦截器的执行顺序，同一级别的拦截器都会执行
    2. 为 JDK9 以上版本做兼容，替换 Class.newInstance 方法为构造的 newInstance 方法
    3. 更改为读写锁来保证线程安全，由于多个操作需要保证原子性，所以使用 HashMap 替代 ConcurrentHashMap
    4. 为 redis 资源加载器做准备 ^_^

#### *2019-4-24:*
    1. 新增 Tuz.unload 方法，当资源加载过多，耗费大量内存时就可以卸载掉不需要的资源了
    2. 新增 Tuz.reload 方法，当资源被修改了需要更新时，就可以使用它更新资源
    3. 新增 Tuz.unUse 方法，可以独立移除一个资源，更加细粒度的操作资源
    4. 新增 Tuz.appendResource 方法，可以往一个命名空间添加资源值

#### *2019-4-22:*
    1. 新增 JSON 资源加载器，现在 JSON 文件也可以很简单的加载并使用了
    2. 新增 DiPlugin 的三个方法，可以临时指定单例模式或者多例模式
    3. 新增 ProxyPlugin 的三个方法，可以临时指定单例模式或者多例模式
    4. 以上两点改动可以做到不修改配置，灵活生成类的实例
    5. 现在有两种 jar 可供使用，一种是不带任何依赖的，一种是带了完整依赖的

#### *2019-4-17:*
    1. 代理工厂新增一个代理拦截器的方法
    2. 当这个类可以被继承时，使用 CGlib 来实现动态代理
    3. 如果不可以被继承，就使用 JDK 来动态代理，这时要求该类必须实现接口，而且使用接口来接收

#### *2019-4-14:*
    1. 加入了动态代理工厂，你可以自己定制代理类
    2. 使用代理工厂解决业务主次解耦的问题
    3. 加入代理插件更方便用户实现代理模式以及拦截器模式
    4. 不推荐直接使用代理工厂，推荐使用代理插件
    5. 代理产生的类可以是单例模式也可以是多例模式的

#### *2019-4-7:*
    1. FTP 功能完毕，修复了上一版本中乱码的问题

#### *2019-4-2:*
    1. 新增多个 IO 处理方法，可以直接获取输入流
    2. 新增 FTPHelper 工具，可以上传文件到 FTP 服务器

#### *2019-3-30:*
    1. 修复了上一个版本中资源文件非英文字符集乱码的问题
    2. 新增加了带有指定字符集的资源加载器，可以在加载资源时指定字符集
    3. 废弃了 cn.com.fishin.tuz.loader.properties.InputStreamPropertiesLoader 加载器
    4. 更改 NameSpaceHelper 的后缀生成策略
    5. 新增 DiPlugin 一个方法，可以更简单获取实例

#### *2019-3-29:*
    1. 加入了依赖注入插件，参考 cn.com.fishin.tuz.plugin.DiPlugin
    2. 实现了文件系统资源加载器，可以从文件系统中加载资源文件了
    3. 修改 cn.com.fishin.tuz.core.Loadable 接口，使得它具有命名空间的功能
    4. 增加了 API 文档和多个代码演示例子，还调整了包结构
