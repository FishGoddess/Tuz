package cn.com.fishin.tuz.entity;

/**
 * <p>用来表示登录信息的实体类</p>
 * <p>登陆一般有用户名和密码</p>
 * <p>Login info entity</p>
 * <p>Login info usually has username and password</p>
 *
 * @author Fish
 * <p>Email: fishinlove@163.com</p>
 * <p>created by 2019/04/02 14:12:06</p>
 */
public class LoginInfo {

    // 用户名
    // username
    private String username = null;

    // 密码
    // password
    private String password = null;

    public LoginInfo() {}

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
