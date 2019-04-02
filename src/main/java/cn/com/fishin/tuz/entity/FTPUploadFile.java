package cn.com.fishin.tuz.entity;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>FTP 上传属性信息实体类</p>
 * <p>通常这些信息包含服务器地址、登录用户信息以及要被上传的文件信息</p>
 * <p>FTP upload info entity</p>
 * <p>Usually this info includes server address, login user info
 * and file that will be uploaded</p>
 * @see cn.com.fishin.tuz.entity.FTPUploadInfo
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 14:31:02</p>
 */
public class FTPUploadFile {

    // 服务器地址
    // Server address
    private ServerAddress serverAddress = null;

    // 登陆用户信息
    // Login user info
    private LoginInfo loginInfo = null;

    // FTP 服务器上的保存文件夹
    // Save direction of FTP server
    private String remoteDirection = null;

    // FTP 服务器上的保存文件名
    // Save filename of FTP server
    private String remoteFileName = null;

    // 本地文件输入流，也就是要被上传的文件的输入流
    // Local file inputStream, the uploading file
    private InputStream inputStream = null;

    // 文件类型
    // File type
    private int fileType = 0;

    // 文件字符编码，如果是文本字符，这个配置会起作用
    private Charset charset = StandardCharsets.UTF_8;

    public FTPUploadFile() {}

    public ServerAddress getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getRemoteDirection() {
        return remoteDirection;
    }

    public void setRemoteDirection(String remoteDirection) {
        this.remoteDirection = remoteDirection;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream stream) {
        this.inputStream = stream;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String toString() {
        return "FTPUploadFile{" +
                "serverAddress=" + serverAddress +
                ", loginInfo=" + loginInfo +
                ", remoteDirection='" + remoteDirection + '\'' +
                ", remoteFileName='" + remoteFileName + '\'' +
                ", stream=" + inputStream +
                ", fileType=" + fileType +
                ", charset=" + charset +
                '}';
    }
}
