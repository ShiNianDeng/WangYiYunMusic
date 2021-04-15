package com.example.lxc.wangyiyunmusic.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.example.lxc.wangyiyunmusic.activitys.LoginActivity;
import com.example.lxc.wangyiyunmusic.helps.RealmHelp;
import com.example.lxc.wangyiyunmusic.helps.UserHelp;
import com.example.lxc.wangyiyunmusic.model.UserModel;

import java.util.List;

/**
 * 用户工具类
 */
public class UserUtils {

    /**
     * 用户登录合法性验证
     *
     * @param context
     * @param phone
     * @param password
     * @return
     */
    public static boolean valiDataLogin(Context context, String phone, String password) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 用户登录合法性验证没问题后，再验证该手机号是否已经被注册了，然后使用此方法将手机号和密码与数据库中的数据进行匹配来验证用户信息
     * true:用户账号密码正确   false:手机号或者密码错误
     * 将成功匹配的用户标记（phone）保存在SharedPreferences中
     */
    public static boolean login(Context context, String phone, String password) {
        if (!queryPhoneExist(phone)) {
            Toast.makeText(context, "当前手机号未注册", Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelp help = new RealmHelp();
        if (!help.userLogin(phone, password)) {
            Toast.makeText(context, "手机号或密码不正确", Toast.LENGTH_SHORT).show();
            help.close();
            return false;
        }


//        将成功匹配的用户标记（phone）保存在SharedPreferences中
        if (!SPUtils.saveUserInfo(context, phone)) {
            //如果保存失败
            Toast.makeText(context, "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();
            return false;
        }

//        将成功匹配的用户登录信息保存在UserHelp中
        UserHelp.geteInstance().setPhone(phone);

//        保存音乐数据源
        help.getMusicSource(context);
        help.close();
        return true;
    }

    /**
     * 用户注册合法性验证
     *
     * @param context
     * @param phone
     * @param password
     * @param passwordConfirm
     * @return
     */
    public static Boolean valiDataRegister(Context context, String phone, String password, String passwordConfirm) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }


//       如果手机号已经被注册了，则return;   true:已经被注册过了    false:还未被注册
        if (queryPhoneExist(phone)) {
            Toast.makeText(context, "该手机号已被注册！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        registerUser(phone, password);
        return true;
    }


    /**
     * 调用RealmHelp类的注册用户方法，注册该用户
     *
     * @param phone
     * @param password
     * @return
     */
    public static void registerUser(String phone, String password) {
        RealmHelp help = new RealmHelp();
        UserModel userModel = new UserModel();
        userModel.setPhone(phone);

        //使用md5为密码加密
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));
        help.registerUser(userModel);
        help.close();
    }

    /**
     * 查询手机号是否已经被注册过了   true:已经被注册过了    false:还未被注册
     *
     * @param phone
     * @return
     */
    public static boolean queryPhoneExist(String phone) {
        RealmHelp help = new RealmHelp();
        List<UserModel> list = help.getAllUsere();
        for (UserModel userModel : list) {
            if (userModel.getPhone().equals(phone)) {
                help.close();
                return true;
            }
        }
        return false;
    }

    /**
     * 用户退出登录
     *
     * @param context
     */
    public static void loginOut(Context context) {
        //用户退出前，先将用户标记从SharedPreferences中删除
        if (!SPUtils.isLLogoutUser(context)) {
            Toast.makeText(context, "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();
            return;
        }
        RealmHelp help = new RealmHelp();
        help.removeMusicSource();
        help.close();
        Intent intent = new Intent(context, LoginActivity.class);
        //设置Intent的Flags标识符:清除当前栈和创建一个新的栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 验证是否存在已登录用户
     *
     * @param context
     * @return
     */
    public static boolean valiDataUserLogin(Context context) {
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1、数据验证
     * 1、原密码是否输入
     * 2、新密码是否输入并且新密码与确定密码是否相同
     * 3、原密码输入是否正确
     * 1、Realm 获取到当前登录的用户模型
     * 2、根据用户模型中保存的密码匹配用户原密码
     * 2、利用 Realm 模型自动更新的特性完成密码的修改
     */
    public static boolean changePassword(Context context, String oldPassword, String password, String passwordConfirm) {
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认新密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp help = new RealmHelp();
        UserModel currentUser = help.getCurrentUser();
        if (!currentUser.getPassword().equals(EncryptUtils.encryptMD5ToString(oldPassword))) {
            Toast.makeText(context, "原密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        help.userChangePassword(EncryptUtils.encryptMD5ToString(password));
        help.close();
        return true;
    }

}
