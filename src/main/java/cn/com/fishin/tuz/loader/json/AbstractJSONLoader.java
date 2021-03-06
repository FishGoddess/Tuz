package cn.com.fishin.tuz.loader.json;

import cn.com.fishin.tuz.core.Loader;
import cn.com.fishin.tuz.helper.LogHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>JSON 配置文件抽象加载器</p>
 * <p>JSON resources loader</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/22 15:29:40</p>
 */
public abstract class AbstractJSONLoader implements Loader {

    // 存储一个 JSON 对象的容器
    // A container stores this json object
    private Map<String, String> jsonMap = new HashMap<>();

    @Override
    public Map<String, String> load() throws Throwable {
        // 日志输出
        LogHelper.info("Load resource ===> " + jsonMap);
        return jsonMap;
    }

    /**
     * <p>使用一个符合 JSON 规范的字符串来初始化这个加载器</p>
     * <p>Use a standard JSON string to initialize this loader</p>
     *
     * @param jsonString <p>JSON 字符串</p>
     *                   <p>The json string</p>
     */
    protected AbstractJSONLoader(String jsonString) {
        // 解析 JSON 字符串，填充进结果容器
        parseJSONObject(JSON.parseObject(jsonString));
    }

    // 解析一个 JSONObject 对象
    // Parse one JSON object
    private void parseJSONObject(JSONObject jsonObject) {
        parseJSONObjectInternal(null, jsonObject);
    }

    // 内部使用的 JSONObject 对象解析方法
    // Parse one JSON object for me, not for you :)
    private void parseJSONObjectInternal(Object key, Object value) {

        // 首先填充结果
        filledJsonMap(key, value);

        // 如果是 JSONObject 对象，以 JSONObject 对象解析方式解析
        if (JSONObject.class.equals(value.getClass())) {
            JSONObject jsonObject = (JSONObject) value;
            //filledJsonMap(key, object);
            /*if (jsonObject.isEmpty()) {
                // 如果这个对象为空，直接填充
                filledJsonMap(key, object);
                return;
            }*/

            // 否则将每个对象成员都进行解析
            for (Map.Entry entry : jsonObject.entrySet()) {
                Object k = entry.getKey();
                if (key != null) {
                    // 获取命名之后的键值
                    k = key + "." + k;
                }
                parseJSONObjectInternal(k, entry.getValue());
            }
            return;
        }

        // 如果是 JSONArray 对象，以 JSONArray 对象解析方式解析
        if (JSONArray.class.equals(value.getClass())) {
            JSONArray jsonArray = (JSONArray) value;
            for (int i = 0; i < jsonArray.size(); i++) {
                Object k = key;
                if (k != null) {
                    k = k + "[" + i + "]";
                }
                parseJSONObjectInternal(k, jsonArray.get(i));
            }
        }
    }

    // 填充 JSON 结果容器
    // Filled with key and value
    private void filledJsonMap(Object key, Object value) {
        jsonMap.put(String.valueOf(key), String.valueOf(value));
    }
}
