package com.stylefeng.guns.rest.common;

import com.stylefeng.guns.api.user.UserInfoModel;

/**
 * Created by lucasma
 */
public class CurrentUser {


    // 考虑到存储全部用户信息会太大，这里只存储用户ID
    private static final ThreadLocal<String> threadLocat = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        threadLocat.set(userId);
    }

    public static String getUserId() {
        return threadLocat.get();
    }
    /*
    private static final ThreadLocal<UserInfoModel> threadLocat = new ThreadLocal<>();

   // 用户信息放在 threadlocal
    public static void saveUserInfo(UserInfoModel userInfoModel) {
        threadLocat.set(userInfoModel);
    }

    public static UserInfoModel getCurrentUser() {
        return threadLocat.get();
    }*/


}
