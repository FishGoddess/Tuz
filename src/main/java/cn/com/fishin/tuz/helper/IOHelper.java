package cn.com.fishin.tuz.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>路径帮助器</p>
 * <p>Path helper</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/03/30 00:24:19</p>
 */
public class IOHelper {

    /**
     * <p>根据指定路径获取一个资源输入流</p>
     * <p>Get a inputStream from given path</p>
     *
     * @param path    <p>指定的路径</p>
     *                <p>Given path</p>
     * @return <p>返回读取器</p><p>Return inputStream</p>
     * @throws IOException <p>获取资源输入流异常</p><p>Get inputStream failed</p>
     */
    public static InputStream newInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    /**
     * <p>根据指定路径获取一个资源输入流</p>
     * <p>Get a inputStream from given path</p>
     *
     * @param path <p>指定的路径</p>
     *             <p>Given path</p>
     * @return <p>返回读取器</p><p>Return inputStream</p>
     * @throws IOException <p>获取资源输入流异常</p><p>Get inputStream failed</p>
     */
    public static InputStream newInputStreamToFileSystem(String path) throws IOException {
        return newInputStream(getResourceFromFileSystem(path));
    }

    /**
     * <p>根据指定路径获取一个资源输入流</p>
     * <p>Get a inputStream from given path</p>
     *
     * @param path <p>指定的路径</p>
     *             <p>Given path</p>
     * @return <p>返回读取器</p><p>Return inputStream</p>
     * @throws IOException <p>获取资源输入流异常</p><p>Get inputStream failed</p>
     */
    public static InputStream newInputStreamToClasspath(String path) throws IOException {
        return newInputStream(getResourceFromClasspath(path));
    }

    /**
     * <p>根据指定路径获取一个资源读取器</p>
     * <p>Get a reader from given path</p>
     *
     * @param path <p>指定的路径</p>
     *             <p>Given path</p>
     *
     * @param charset <p>字符集</p>
     *                <p>Charset</p>
     *
     * @return <p>返回读取器</p><p>Return reader</p>
     * @throws IOException <p>获取读取器异常</p><p>Get reader failed</p>
     */
    public static Reader newReader(Path path, Charset charset) throws IOException {
        return Files.newBufferedReader(path, charset);
    }

    /**
     * <p>根据文件系统获取一个资源读取器</p>
     * <p>Get a reader from file system</p>
     *
     * @param path    <p>文件系统路径</p>
     *                <p>Given path</p>
     * @param charset <p>字符集</p>
     *                <p>Charset</p>
     * @return <p>返回读取器</p><p>Return reader</p>
     * @throws IOException <p>获取读取器异常</p><p>Get reader failed</p>
     */
    public static Reader newReaderToFileSystem(String path, Charset charset) throws IOException {
        return newReader(getResourceFromFileSystem(path), charset);
    }

    /**
     * <p>根据类路径获取一个资源读取器</p>
     * <p>Get a reader from classpath</p>
     *
     * @param path    <p>类路径资源</p>
     *                <p>Given path in classpath</p>
     * @param charset <p>字符集</p>
     *                <p>Charset</p>
     * @return <p>返回读取器</p><p>Return reader</p>
     * @throws IOException <p>获取读取器异常</p><p>Get reader failed</p>
     */
    public static Reader newReaderToClasspath(String path, Charset charset) throws IOException {
        return newReader(getResourceFromClasspath(path), charset);
    }

    /**
     * <p>从类路径中获得资源路径</p>
     * <p>Get path from classpath</p>
     *
     * @param resource <p>类路径中的资源</p>
     *                 <p>The resource of classpath</p>
     * @return <p>返回指向类路径中这个资源的路径对象</p>
     * <p>Return a path to this resource in classpath</p>
     */
    public static Path getResourceFromClasspath(String resource) {

        URL url = IOHelper.class.getClassLoader().getResource(resource);
        try {
            if (url == null) {
                throw new FileNotFoundException(resource + " is not found in classpath!");
            }

            return Paths.get(url.toURI());
        } catch (URISyntaxException | FileNotFoundException e) {
            // URISyntaxException 是非常少见的，所以直接通知资源找不到即可
            LogHelper.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * <p>从文件系统获得资源路径</p>
     * <p>Get path from file system</p>
     *
     * @param resource <p>文件系统中的资源</p>
     *                 <p>The resource of file system</p>
     *
     * @return <p>返回指向文件系统中这个资源的路径对象</p>
     * <p>Return a path to this resource in file system</p>
     */
    public static Path getResourceFromFileSystem(String resource) {
        return Paths.get(resource);
    }
}
