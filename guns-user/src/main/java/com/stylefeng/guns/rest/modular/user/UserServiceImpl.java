package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lucasma
 */

@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {


    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String userName, String password) {
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        // 转化为数据实体

        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUserName());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUserPhone(userModel.getPhone());

        // 密码加密 md5混淆加密 + 盐值  salt
        String encryptPwd = MD5Util.encrypt(userModel.getPassword());

        moocUserT.setUserPwd(encryptPwd);

        Integer insert = moocUserTMapper.insert(moocUserT);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUserName(String userName) {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        return null;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        return null;
    }
}
