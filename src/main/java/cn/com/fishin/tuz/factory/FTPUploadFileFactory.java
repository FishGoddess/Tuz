package cn.com.fishin.tuz.factory;

import cn.com.fishin.tuz.core.Tuz;
import cn.com.fishin.tuz.core.TuzConfig;
import cn.com.fishin.tuz.entity.FTPUploadFile;
import cn.com.fishin.tuz.entity.LoginInfo;
import cn.com.fishin.tuz.entity.ServerAddress;
import org.apache.commons.net.ftp.FTP;

import java.io.InputStream;

/**
 * <p>FTPUploadFile 工厂类</p>
 * <p>由于这个上传文件的这个实体类存在很多默认值，所以制造一个默认工厂类</p>
 * <p>FTPUploadFile factory</p>
 * <p>You should instance a FTPUploadFile by this factory</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 21:12:32</p>
 * @see cn.com.fishin.tuz.entity.FTPUploadFile
 */
public class FTPUploadFileFactory {

    /**
     * <p>生成一个待上传二进制文件</p>
     * <p>Create a binary file to be uploaded</p>
     *
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>返回一个待上传二进制文件对象</p><p>Return a binary file to be uploaded</p>
     */
    public static FTPUploadFile makeBinaryFile(String remoteDirection, String remoteFileName,
                                               InputStream inputStream) {
        return make(FTP.BINARY_FILE_TYPE, remoteDirection, remoteFileName, inputStream);
    }

    /**
     * <p>生成一个待上传文本文件</p>
     * <p>Create a text file to be uploaded</p>
     *
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>返回一个待上传文本文件对象</p><p>Return a text file to be uploaded</p>
     */
    public static FTPUploadFile makeAsciiFile(String remoteDirection, String remoteFileName,
                                              InputStream inputStream) {
        return make(FTP.ASCII_FILE_TYPE, remoteDirection, remoteFileName, inputStream);
    }

    /**
     * <p>生成一个待上传文件</p>
     * <p>Create a file to be uploaded</p>
     *
     * @param fileType        <p>文件类型</p>
     *                        <p>File type</p>
     * @param remoteDirection <p>文件保存在 FTP 服务器的那个文件</p>
     *                        <p>The direction where the file will be saved</p>
     * @param remoteFileName  <p>文件保存的名字</p>
     *                        <p>The name of file in the FTP server</p>
     * @param inputStream     <p>文件输入流</p>
     *                        <p>File inputStream</p>
     * @return <p>返回一个待上传文件对象</p><p>Return a file to be uploaded</p>
     * @see org.apache.commons.net.ftp.FTPClient#setFileType(int)
     * @see org.apache.commons.net.ftp.FTP#ASCII_FILE_TYPE
     * @see org.apache.commons.net.ftp.FTP#BINARY_FILE_TYPE
     */
    public static FTPUploadFile make(int fileType,
                                     String remoteDirection, String remoteFileName,
                                     InputStream inputStream) {
        FTPUploadFile file = new FTPUploadFile();
        file.setFileType(fileType);
        file.setRemoteDirection(remoteDirection);
        file.setRemoteFileName(remoteFileName);
        file.setInputStream(inputStream);

        TuzConfig config = Tuz.getConfig();
        file.setServerAddress(new ServerAddress(config.getFtpHost(), config.getFtpPort()));
        file.setLoginInfo(new LoginInfo(config.getFtpUser(), config.getFtpPassword()));
        return file;
    }
}
