package cn.com.fishin.tuz.core;

import cn.com.fishin.tuz.loader.json.ClasspathJSONLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * 测试 JSON 资源加载器
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 20:07:12</p>
 */
public class JSONLoaderTest {

    // Tuz 实例
    private Tuz tuz = Tuz.instance();

    @Before
    public void before() {
        try {
            tuz.load(new ClasspathJSONLoader("test.json", "test", StandardCharsets.UTF_8));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
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
        Assert.assertEquals(tuz.use("status"), "0");
        Assert.assertEquals(tuz.use("status", "test"), "0");
        Assert.assertNull(tuz.use("status", "test2"));
        Assert.assertEquals(tuz.use("message"), "success");
        Assert.assertEquals(tuz.use("ok"), "true");
        Assert.assertEquals(tuz.use("data.title.id"), "001");
        Assert.assertEquals(tuz.use("data.title.name"), "test");
        Assert.assertEquals(tuz.use("data.title.null"), "{}");
        Assert.assertEquals(tuz.use("data.content[0].id"), "001");
        Assert.assertEquals(tuz.use("data.content[0].value"), "hello 001");
        Assert.assertEquals(tuz.use("data.content[0].arr[0]"), "1");
        Assert.assertEquals(tuz.use("data.content[0].arr[1]"), "非你莫属尽快发你说的");
        Assert.assertEquals(tuz.use("data.content[0].arr[2]"), "true");
        Assert.assertEquals(tuz.use("data.content[1].id"), "002");
        Assert.assertEquals(tuz.use("data.content[1].value"), "hello 002");

        System.out.println(tuz.use("data.title"));
    }
}
