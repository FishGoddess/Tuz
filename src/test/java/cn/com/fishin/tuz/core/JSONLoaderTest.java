package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.loader.ClasspathJSONLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 测试 JSON 资源加载器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 20:07:12</p>
 */
public class JSONLoaderTest {

    @Before
    public void before() {
        try {
            Tuz.load(new ClasspathJSONLoader("test.json", "test", StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
                    "arr": [1, "bfdjhsb", true]
                },
                {
                    "id": "002",
                    "value": "hello 002"
                }
            ]
         }
     }
     */
    @Test
    public void testJSON() {
        Assert.assertEquals(Tuz.use("status"), "0");
        Assert.assertEquals(Tuz.use("status", "test"), "0");
        Assert.assertNull(Tuz.use("status", "test2"));
        Assert.assertEquals(Tuz.use("message"), "success");
        Assert.assertEquals(Tuz.use("ok"), "true");
        Assert.assertEquals(Tuz.use("data.title.id"), "001");
        Assert.assertEquals(Tuz.use("data.title.name"), "test");
        Assert.assertEquals(Tuz.use("data.title.null"), "{}");
        Assert.assertEquals(Tuz.use("data.content[0].id"), "001");
        Assert.assertEquals(Tuz.use("data.content[0].value"), "hello 001");
        Assert.assertEquals(Tuz.use("data.content[0].arr[0]"), "1");
        Assert.assertEquals(Tuz.use("data.content[0].arr[1]"), "非你莫属尽快发你说的");
        Assert.assertEquals(Tuz.use("data.content[0].arr[2]"), "true");
        Assert.assertEquals(Tuz.use("data.content[1].id"), "002");
        Assert.assertEquals(Tuz.use("data.content[1].value"), "hello 002");

        System.out.println(Tuz.use("data.title"));
    }
}
