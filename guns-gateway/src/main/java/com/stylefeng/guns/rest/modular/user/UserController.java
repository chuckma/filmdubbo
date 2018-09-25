package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lucasma
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;


    @PostMapping("register")
    public ResponseVO register(UserModel userModel) {
        if (userModel.getUserName() == null || userModel.getUserName().length() == 0) {
            return ResponseVO.serviceFile("用户名不能为空");
        }

        if (userModel.getPassword() == null || userModel.getPassword().length() == 0) {
            return ResponseVO.serviceFile("密码不能为空");
        }

        boolean register = userAPI.register(userModel);
        if (register) {
            return ResponseVO.success("注册成功！");
        } else {
            return ResponseVO.serviceFile("注册失败！");
        }
    }




    @PostMapping("check")
    public ResponseVO check(String userName) {
        if (userName != null && userName.length() > 0) {
            boolean notExists = userAPI.checkUserName(userName);
            if (notExists) {
                return ResponseVO.success("用户名不存在！");
            } else {
                return ResponseVO.serviceFile("用户名已存在！");
            }
        } else {
            return ResponseVO.serviceFile("用户名不能为空！");
        }
    }



    @GetMapping("logout")
    public ResponseVO logout(String userName) {

        /**
         * 实际生产环境
         * 前端存储 jwt 7 天
         * 服务器会话存储用户活动信息 30 min
         * jwt userId 为key 查看活跃用户
         *
         *
         * 退出时：
         * 1. 删除 jwt
         * 2. 删除会话活跃用户缓存。
         */

        // 删除 JWT
        return ResponseVO.success("退出成功！");
    }


    @GetMapping("getUserInfo")
    public ResponseVO getUserInfo() {

        // 获取当前登录用户，查询数据库
        String userId = CurrentUser.getUserId();
        if (userId != null || userId.trim().length() > 0) {
            int uuid = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.getUserInfo(uuid);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.appFile("用户信息查询失败！");
            }
        } else {
            return ResponseVO.serviceFile("用户未登录！");
        }
    }


    @PostMapping("updateUserInfo")
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel) {

        // 获取当前登录用户，查询数据库
        String userId = CurrentUser.getUserId();
        if (userId != null || userId.trim().length() > 0) {
            int uuid = Integer.parseInt(userId);
            // 当前登录的 id 与修改结果的 id 是否一致
            if (uuid != userInfoModel.getUuid()) {
                return ResponseVO.serviceFile("请修改你自己的信息");
            }
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.appFile("用户信息修改失败！");
            }
        } else {
            return ResponseVO.serviceFile("用户未登录！");
        }
    }
}
