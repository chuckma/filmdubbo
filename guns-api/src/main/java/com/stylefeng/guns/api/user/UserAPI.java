package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;

public interface UserAPI {


    // 登录
    int login(String userName, String password);

    // 注册
    boolean register(UserModel userModel);

    // 注册验证是否已经存在用户
    boolean checkUserName(String userName);

    // 查询用户信息（部分信息）
    UserInfoModel getUserInfo(int uuid );

    // 修改
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);


}
