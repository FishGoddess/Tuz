package cn.com.fishin.tuz.demo;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.loader.AbstractJSONLoader;
import cn.com.fishin.tuz.loader.ClasspathJSONLoader;
import cn.com.fishin.tuz.loader.FileSystemJSONLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Tuz JSON 资源加载器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 20:24:26</p>
 */
public class TuzSimpleDemo3 {

    public static void main(String[] args) throws IOException {

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
