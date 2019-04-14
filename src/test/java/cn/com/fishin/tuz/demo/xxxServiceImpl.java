package cn.com.fishin.tuz.demo;

/**
 * 测试的某业务接口实现类
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/29 00:08:24</p>
 */
public class xxxServiceImpl implements xxxService {

    @Override
    public void say(String word) {
        System.out.println(word);
    }

    @Override
    public void hung(int man, int woman) {
        System.out.println(man + " hung " + woman);
    }
}
