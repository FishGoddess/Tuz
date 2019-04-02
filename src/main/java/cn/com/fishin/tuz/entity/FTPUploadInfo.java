package cn.com.fishin.tuz.entity;

/**
 * <p>暂时不推荐使用</p>
 * <p>FTP 上传属性信息实体类</p>
 * <p>通常这些信息包含服务器地址、登录用户信息以及要被上传的文件信息</p>
 * <p>FTP upload info entity</p>
 * <p>Usually this info includes server address, login user info
 * and file that will be uploaded</p>
 * @see cn.com.fishin.tuz.entity.FTPUploadFile
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 14:31:02</p>
 */
@Deprecated
public class FTPUploadInfo {

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

    // 本地文件名，也就是要被上传的文件
    // Local file, the uploading file
    private String localFileName = null;

    public FTPUploadInfo() {}

    public ServerAddress getServerAddress() {
        return serverAddress;
    }

    public FTPUploadInfo setServerAddress(ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public FTPUploadInfo setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
        return this;
    }

    public String getRemoteDirection() {
        return remoteDirection;
    }

    public FTPUploadInfo setRemoteDirection(String remoteDirection) {
        this.remoteDirection = remoteDirection;
        return this;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public FTPUploadInfo setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
        return this;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public FTPUploadInfo setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
        return this;
    }

    @Override
    public String toString() {
        return "FTPUploadInfo{" +
                "serverAddress=" + serverAddress +
                ", loginInfo=" + loginInfo +
                ", remoteDirection='" + remoteDirection + '\'' +
                ", remoteFileName='" + remoteFileName + '\'' +
                ", localFileName='" + localFileName + '\'' +
                '}';
    }
}
