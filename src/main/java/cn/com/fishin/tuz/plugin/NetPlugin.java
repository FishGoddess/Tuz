package cn.com.fishin.tuz.plugin;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.entity.FTPUploadFile;
import cn.com.fishin.tuz.entity.LoginInfo;
import cn.com.fishin.tuz.factory.FTPUploadFileFactory;
import cn.com.fishin.tuz.helper.FTPHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * <p>网络资源操作插件</p>
 * <p>比如，FTP 服务器资源上传和下载</p>
 * <p>Plugin of net resources</p>
 * <p>Such as, FTP resources</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 16:16:15</p>
 */
public class NetPlugin {

    /**
     * <p>上传一个文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性，
     * controlEncoding 是网络通信使用的编码，是内部赋值的，外界不需要知道，它的默认值是在 TuzConfig 中
     * </p>
     *
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * This argument "file" has many attributes about the file
     * to be uploaded and info of FTP server.
     * controlEncoding is used to net transport, default value is in TuzConfig
     * </p>
     *
     * @param file <p>要被上传的文件，这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性</p>
     *             <p>The file to be uploaded,
     *             This argument "file" has many attributes about the file
     *             to be uploaded and info of FTP server</p>
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     * @see cn.com.fishin.tuz.core.TuzConfig#getFtpControlEncoding()
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     */
    public static boolean uploadToServer(FTPUploadFile file) throws IOException {
        return FTPHelper.upload(file, Tuz.getConfig().getFtpControlEncoding());
    }

    /**
     * <p>上传一个二进制文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * controlEncoding 是网络通信使用的编码，是内部赋值的，外界不需要知道，它的默认值是在 TuzConfig 中
     * </p>
     *
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * controlEncoding is used to net transport, default value is in TuzConfig
     * </p>
     *
     * @param loginInfo       <p>登录 FTP 服务器的用户信息，比如用户名和密码</p>
     *                        <p>User who login FTP server</p>
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     */
    public static boolean uploadBinaryToServer(LoginInfo loginInfo,
                                               String remoteDirection, String remoteFileName,
                                               InputStream inputStream) throws IOException {

        // 完善这个上传文件的信息
        FTPUploadFile file = FTPUploadFileFactory.makeBinaryFile(remoteDirection, remoteFileName, inputStream);
        file.setLoginInfo(loginInfo);

        // 上传到服务器
        return uploadToServer(file);
    }

    /**
     * <p>上传一个二进制文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * controlEncoding 是网络通信使用的编码，是内部赋值的，外界不需要知道，它的默认值是在 TuzConfig 中
     * </p>
     *
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * controlEncoding is used to net transport, default value is in TuzConfig
     * </p>
     *
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     */
    public static boolean uploadBinaryToServer(String remoteDirection, String remoteFileName,
                                               InputStream inputStream) throws IOException {
        return uploadBinaryToServer(new LoginInfo(Tuz.getConfig().getFtpUser(), Tuz.getConfig().getFtpPassword()),
                remoteDirection, remoteFileName, inputStream);
    }

    /**
     * <p>上传一个文本文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 由于文本文件可能有编码问题，所以这里多一个文本字符集的参数，
     * controlEncoding 是网络通信使用的编码，是内部赋值的，外界不需要知道，它的默认值是在 TuzConfig 中
     * </p>
     *
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * You should appointed the encoding of text file to avoid encoding problems.
     * controlEncoding is used to net transport, default value is in TuzConfig
     * </p>
     *
     * @param loginInfo       <p>登录 FTP 服务器的用户信息，比如用户名和密码</p>
     *                        <p>User who login FTP server</p>
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @param charset         <p>文本文件的字符集编码</p>
     *                        <p>The encoding of text file</p>
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     */
    public static boolean uploadTextToServer(LoginInfo loginInfo,
                                             String remoteDirection, String remoteFileName,
                                             InputStream inputStream, Charset charset) throws IOException {

        // 完善这个上传文件的信息
        FTPUploadFile file = FTPUploadFileFactory.makeAsciiFile(remoteDirection, remoteFileName, inputStream);
        file.setLoginInfo(loginInfo);
        file.setCharset(charset);

        // 上传到服务器
        return uploadToServer(file);
    }

    /**
     * <p>上传一个文本文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 由于文本文件可能有编码问题，这里使用默认配置中的编码来解码，具体请看下面的 @see
     * controlEncoding 是网络通信使用的编码，是内部赋值的，外界不需要知道，它的默认值是在 TuzConfig 中
     * </p>
     *
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * This method use default encoding to decode this text file,
     * controlEncoding is used to net transport, default value is in TuzConfig
     * </p>
     * @see FTPUploadFile#getCharset()
     *
     * @param loginInfo       <p>登录 FTP 服务器的用户信息，比如用户名和密码</p>
     *                        <p>User who login FTP server</p>
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     */
    public static boolean uploadTextToServer(LoginInfo loginInfo,
                                             String remoteDirection, String remoteFileName,
                                             InputStream inputStream) throws IOException {

        // 完善这个上传文件的信息
        FTPUploadFile file = FTPUploadFileFactory.makeAsciiFile(remoteDirection, remoteFileName, inputStream);
        file.setLoginInfo(loginInfo);

        // 上传到服务器
        return uploadToServer(file);
    }
}
