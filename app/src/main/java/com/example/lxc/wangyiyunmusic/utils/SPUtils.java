package com.example.lxc.wangyiyunmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.lxc.wangyiyunmusic.constants.SPConstants;
import com.example.lxc.wangyiyunmusic.helps.UserHelp;

public class SPUtils {

    /**
     * 当用户登录时，使用过SharedPreferences来保存登录用户的标记（phone）
     * 如果将用户标记成功保存在SharedPreferences中，则返回true;否则返回false
     */
    public static boolean saveUserInfo(Context context, String phone) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_USER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor = editor.putString(SPConstants.SP_USER_KEY, phone);
        result = editor.commit();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     * 如果存在已登录用户，则将用户信息保存在UserHelp中
     */
    public static boolean isLoginUser(Context context) {
        boolean result = false;

        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_USER_NAME, Context.MODE_PRIVATE);
        String phone = sp.getString(SPConstants.SP_USER_KEY, "");
        if (!TextUtils.isEmpty(phone)) {
            result = true;
            UserHelp.geteInstance().setPhone(phone);
        }
        return result;
    }

    /**
     * 删除用户标记
     */
    public static boolean isLLogoutUser(Context context) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences(SPConstants.SP_USER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor = editor.remove(SPConstants.SP_USER_KEY);
        result = editor.commit();
        return result;
    }
}
