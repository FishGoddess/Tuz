package cn.com.fishin.tuz.helper;


import cn.com.fishin.tuz.entity.FTPUploadFile;
import cn.com.fishin.tuz.entity.LoginInfo;
import cn.com.fishin.tuz.entity.ServerAddress;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * <p>FTP 帮助器</p>
 * <p>FTP Helper</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 13:04:59</p>
 */
public class FTPHelper {

    /**
     * <p>上传一个文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性
     * </p>
     * <p>Upload a file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * This argument "file" has many attributes about the file
     * to be uploaded and info of FTP server
     * </p>
     *
     * @param file <p>要被上传的文件，这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性</p>
     *             <p>The file to be uploaded,
     *             This argument "file" has many attributes about the file
     *             to be uploaded and info of FTP server</p>
     *
     * @param controlEncoding <p>网络通信使用的编码</p>
     *                        <p>The encoding of net transport</p>
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     *
     * @return <p>true 上传成功，false 上传不成功</p><p>true if successful, false if not</p>
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p><p>If occur some problems when uploading</p>
     */
    public static boolean upload(FTPUploadFile file, String controlEncoding) throws IOException {
        return new FTPTemplate(file.getServerAddress(), file.getLoginInfo()) {
            @Override
            protected boolean upload(FTPClient client) throws IOException {

                // 设置上传文件类型
                client.setFileType(file.getFileType());

                // 设置默认通信编码
                client.setControlEncoding(controlEncoding);

                // 如果切换失败，说明可能没有这个目录，创建目录
                if (!client.changeWorkingDirectory(file.getRemoteDirection())) {
                    client.makeDirectory(file.getRemoteDirection());
                    client.changeWorkingDirectory(file.getRemoteDirection());
                }

                // 上传
                return client.storeFile(file.getRemoteFileName(), file.getInputStream());
            }
        }.upload();
    }

    /**
     * <p>上传一个文本文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性
     * </p>
     * <p>Upload a text file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * This argument "file" has many attributes about the file
     * to be uploaded and info of FTP server
     * </p>
     *
     * @param file <p>要被上传的文本文件，这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性</p>
     *             <p>The text file to be uploaded,
     *             This argument "file" has many attributes about the file
     *             to be uploaded and info of FTP server</p>
     *
     * @param controlEncoding <p>网络通信使用的编码</p>
     *                        <p>The encoding of net transport</p>
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     *
     * @return <p>true 上传成功，false 上传不成功</p>
     *         <p>true if successful, false if not</p>
     *
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p>
     *                     <p>If occur some problems when uploading</p>
     */
    public static boolean uploadAscii(FTPUploadFile file, String controlEncoding) throws IOException {
        file.setFileType(FTP.ASCII_FILE_TYPE);
        return upload(file, controlEncoding);
    }

    /**
     * <p>上传一个二进制文件到 FTP 服务器</p>
     * <p>
     * <strong>注意：</strong>
     * 这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性
     * </p>
     * <p>Upload a binary file to FTP server</p>
     * <p>
     * <strong>Notice: </strong>
     * This argument "file" has many attributes about the file
     * to be uploaded and info of FTP server
     * </p>
     *
     * @param file <p>要被上传的二进制文件，这个参数包含很多要被上传文件的属性以及 FTP 服务器的属性</p>
     *             <p>The text file to be uploaded,
     *             This argument "file" has many attributes about the file
     *             to be uploaded and info of FTP server</p>
     *
     * @param controlEncoding <p>网络通信使用的编码</p>
     *                         <p>The encoding of net transport</p>
     * @see org.apache.commons.net.ftp.FTPClient#setControlEncoding(String)
     *
     * @return <p>true 上传成功，false 上传不成功</p>
     *         <p>true if successful, false if not</p>
     *
     * @throws IOException <p>上传遇到问题就会抛出这个异常</p>
     *                     <p>If occur some problems when uploading</p>
     */
    public static boolean uploadBinary(FTPUploadFile file, String controlEncoding) throws IOException {
        file.setFileType(FTP.BINARY_FILE_TYPE);
        return upload(file, controlEncoding);
    }

    /**
     * <p>连接到 FTP 服务器</p>
     * <p>返回一个 FTPClient 对象</p>
     * <p>Connect to FTP server</p>
     * <p>Return a connection to FTP server</p>
     *
     * @param address <p>服务器地址</p><p>Server address</p>
     * @param user    <p>登录 FTP 服务器的用户</p><p>User who login the FTP server</p>
     * @return <p>返回一个 FTPClient 对象</p><p>Return a connection to FTP server</p>
     * @throws IOException <p>当连接出现异常的时候就会抛出这个异常</p><p>If connect failed</p>
     */
    public static FTPClient connectToFTP(ServerAddress address, LoginInfo user) throws IOException {
        FTPClient client = new FTPClient();

        // 连接 FTP 服务器，并登陆
        client.connect(address.getIp(), address.getPort());
        client.login(user.getUsername(), user.getPassword());
        return client;
    }

    /**
     * <p>关闭与 FTP 服务器的连接</p>
     * <p>Cloase the connection to FTP server</p>
     *
     * @param client <p>要被关闭的连接对象</p><p>The connection to be closed</p>
     */
    public static void disconnectFromFTP(FTPClient client) {

        try {
            // 退出登录，并断开连接
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            LogHelper.error("Failed to disconnect from FTP server!" + client, e);
        }
    }

    // FTP 模板类
    // FTP Template
    private static abstract class FTPTemplate {

        // FTP 客户端
        private FTPClient client = null;

        // 构造函数
        FTPTemplate(ServerAddress address, LoginInfo info) {
            try {
                client = connectToFTP(address, info);
            } catch (IOException e) {
                String errorMessage = "Failed to connect to FTP server at [" +
                        address.getIp() + ":" + address.getPort() +
                        "], user is " + info.getUsername() + ", password is " + info.getPassword();

                LogHelper.error(errorMessage, e);
                throw new RuntimeException(errorMessage, e);
            }
        }

        // 上传方法交给子类实现，注意返回值要遵循 true 上传成功，false 上传失败的约定
        protected abstract boolean upload(FTPClient client) throws IOException;

        // 上传方法，true 上传成功，false 上传失败
        boolean upload() throws IOException {
            try {
                // 执行上传操作
                return upload(client);
            } finally {
                // 关闭服务器连接
                disconnectFromFTP(client);
            }
        }
    }
}
