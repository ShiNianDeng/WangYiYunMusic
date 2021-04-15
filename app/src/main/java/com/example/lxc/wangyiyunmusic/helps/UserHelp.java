package com.example.lxc.wangyiyunmusic.helps;

/**
 * 1.用户登录状态
 *      1.当用户登录时，使用过SharedPreferences来保存登录用户的标记（phone）
 *      2.使用全局单例类UserHelp保存登录用户信息
 *          1.用户登录之后需要保存用户信息
 *          2.打开应用程序时，检测SharedPreferencecs中是否存在用户标记
 *              如果存在用户标记，则为UserHelp赋值（保存用户信息）
 *              如果不存在，则进入登录页面
 * 2.用户注销状态
 *      1.删除掉SharedPredferences中的用户标记（Phone)，退出到登录页面
 */
public class UserHelp {
    private static UserHelp instance;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private UserHelp() {

    }

    public static UserHelp geteInstance() {
        if (instance == null) {
            synchronized (UserHelp.class) {
                if (instance == null) {
                    instance = new UserHelp();
                }
            }
        }
        return instance;
    }

}
