package cn.com.fishin.tuz.entity;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>简易版的 LRU 缓存</p>
 * <p>使用 LinkedHashMap 加 Timer 实现</p>
 * <p>Simple LRU cache</p>
 * <p>Use LinkedHashMap and Timer</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/28 23:18:08</p>
 */
public class SimpleLruCache<K, V> extends LinkedHashMap<K, V> {

    // 缓存最大个数
    private static final int DEFAULT_MAX_SIZE = 128;
    private final int maxSize;

    // 任务执行周期，默认一个小时执行一次
    // Default one time per hour
    private static final long PERIOD = TimeUnit.HOURS.toMillis(1);

    // 定时器
    // 定期清除这个缓存的数据
    // Timer
    // Clean up data on time
    private Timer timer = new Timer("SimpleLruCache_Timer", true);

    {
        // 初始化定时器
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    clear(); // 每一次都清空缓存
                }
            }
        }, PERIOD, PERIOD);
    }

    /**
     * <p>使用默认最大值构造</p>
     * <p>Use DEFAULT_MAX_SIZE to initialize</p>
     */
    public SimpleLruCache() {
        this(DEFAULT_MAX_SIZE);
    }

    /**
     * <p>使用最大缓存个数和缓存排序方式构造</p>
     * <p>Use maxSize and accessOrder to initialize</p>
     *
     * @param maxSize     <p>缓存最大个数，到达这个值之后就会开始清除缓存数据</p>
     *                    <p>The max size of cache. The cache begins to clean up if size reach max size</p>
     */
    public SimpleLruCache(int maxSize) {
        this(maxSize, true);
    }

    /**
     * <p>使用最大缓存个数和缓存排序方式构造</p>
     * <p>Use maxSize and accessOrder to initialize</p>
     *
     * @param maxSize <p>缓存最大个数，到达这个值之后就会开始清除缓存数据</p>
     *                <p>The max size of cache. The cache begins to clean up if size reach max size</p>
     * @param accessOrder <p>true 表示以访问顺序排列数据，false 表示以插入数据表示数据</p>
     *                    <p>true for access-order, false for insertion-order</p>
     */
    public SimpleLruCache(int maxSize, boolean accessOrder) {
        super(DEFAULT_MAX_SIZE, 0.75f, accessOrder);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当缓存大小超过最大个数时移除最少使用的数据
        return size() > maxSize;
    }
}
