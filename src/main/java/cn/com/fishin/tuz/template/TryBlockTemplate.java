package cn.com.fishin.tuz.template;

/**
 * <p>try...catch...block 代码块模板类</p>
 * <p>try...catch...block code template class</p>
 *
 * <pre>
 *     // 选择性地覆盖方法，以便在不同的代码块中运行
 *     public String lines() {
 *         return new TryBlockTemplate&lt;String&gt;() {
 *             //@Override
 *             protected String doInTry(Object... args) {
 *                 return null;
 *             }
 *
 *             //@Override
 *             protected String doInCatch(Throwable t, Object... args) {
 *                 return null;
 *             }
 *
 *             //@Override
 *             protected void doInFinally(Object... args) {
 *
 *             }
 *         }.tryWith();
 *     }
 * </pre>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/25 15:09:39</p>
 */
public class TryBlockTemplate<Result> {

    /**
     * <p>这是一个模板方法</p>
     * <p>包含了一个 try 代码块内容，由于这个代码块很常见，
     * 所以创建一个模板类减少重复代码，并且将精力集中在业务上</p>
     * <p>子类可以选择性地复写其中的方法</p>
     * <p>This is a template about try block</p>
     * <p>Try block is so common, so I wanna to make it gracefully</p>
     * <p>Subclass should override some methods of it</p>
     *
     * <pre>
     *     try {
     *         return doInTry(args);
     *     } catch (Throwable t) {
     *         return doInCatch(t, args);
     *     } finally {
     *         doInFinally(args);
     *     }
     * </pre>
     *
     * @param args <p>可变参数，如果你需要使用到额外的参数，就通过这个参数传进来</p>
     *             <p>Extendable parameters, if you wanna to use some parameters
     *             , then use it!
     *             </p>
     * @return <p>返回执行结果</p><p>Return result</p>
     */
    public Result tryWith(Object... args) {
        try {
            return doInTry(args);
        } catch (Throwable t) {
            return doInCatch(t, args);
        } finally {
            doInFinally(args);
        }
    }

    /**
     * <p>在 try 块中执行</p>
     * <p>Do in try block</p>
     *
     * @param args <p>可变参数，如果你需要使用到额外的参数，就通过这个参数传进来</p>
     *             <p>Extendable parameters, if you wanna to use some parameters
     *             , then use it!
     *             </p>
     * @throws Throwable <p>执行抛出异常</p><p>The exception you throw</p>
     * @return <p>返回执行结果</p><p>Return result</p>
     */
    protected Result doInTry(Object... args) throws Throwable {
        return null;
    }

    /**
     * <p>在 catch 块中执行</p>
     * <p>Do in catch block</p>
     *
     * @param t <p>抛出的异常</p><p>The threw exception</p>
     * @param args <p>可变参数，如果你需要使用到额外的参数，就通过这个参数传进来</p>
     *             <p>Extendable parameters, if you wanna to use some parameters
     *             , then use it!
     *             </p>
     * @return <p>返回执行结果</p><p>Return result</p>
     */
    protected Result doInCatch(Throwable t, Object... args) {
        return null;
    }

    /**
     * <p>在 finally 块中执行</p>
     * <p>Do in finally block</p>
     *
     * @param args <p>可变参数，如果你需要使用到额外的参数，就通过这个参数传进来</p>
     *             <p>Extendable parameters, if you wanna to use some parameters
     *             , then use it!
     *             </p>
     */
    protected void doInFinally(Object... args) {}
}
