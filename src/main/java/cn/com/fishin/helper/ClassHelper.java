package cn.com.fishin.helper;

/**
 * <p>类帮助器</p>
 * <p>一些操作类的方法</p>
 * <p>Class helper</p>
 * <p>Some methods that used to operate class</p>
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/03/28 22:31:45
 */
public class ClassHelper {

    /**
     * <p>加载一个类，并返回一个实例化对象</p>
     * <p>Load a class, and return a instance of this class</p>
     *
     * @param className <p>要被加载的类名</p>
     *                  <p>Name of loaded class</p>
     *
     * @return <p>返回这个被加载类的实例对象</p>
     * <p>Return the instance of loaded class</p>
     *
     * @throws ClassNotFoundException <p>如果找不到这个类</p><p>If the class is not found</p>
     * @throws IllegalAccessException <p>如果这个类不允许访问</p><p>If the class is not allowed to access</p>
     * @throws InstantiationException <p>如果这个类没有默认构造函数</p><p>If the class do not has default constructor</p>
     */
    public static Object newInstance(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        // 加载类
        Class clazz = Class.forName(className);
        return clazz.newInstance();
    }

    /**
     * <p>加载一个类，并返回一个实例化对象</p>
     * <p>出现异常就会返回 null</p>
     * <p>Load a class, and return a instance of this class</p>
     * <p>If exception happened, return null object</p>
     *
     * @param className <p>要被加载的类名</p>
     *                  <p>Name of loaded class</p>
     *
     * @param classType <p>类的实际类型，用于类型转换</p>
     *                  <p>The type of class, used to cast</p>
     * @return <p>返回这个被加载类的实例对象</p>
     * <p>Return the instance of loaded class</p>
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className, Class<T> classType) {
        try {
            return (T) newInstance(className);
        } catch (ClassNotFoundException e) {
            LogHelper.error("The class is not found", e);
        } catch (IllegalAccessException e) {
            LogHelper.error("the class is not allowed to access", e);
        } catch (InstantiationException e) {
            LogHelper.error("the class do not has default constructor", e);
        }

        // 出现异常就会返回 null
        return null;
    }
}
