package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

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
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userName);

        MoocUserT result = moocUserTMapper.selectOne(moocUserT);

        if (result != null && result.getUuid() > 0) {
            String md5Pwd = MD5Util.encrypt(password);
            if (md5Pwd.equals(result.getUserPwd())) {
                return result.getUuid();
            }
        }
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
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",userName);
        Integer count = moocUserTMapper.selectCount(entityWrapper);
        if (count != null && count > 0) {
            return false;
        }else{
            return true;
        }
    }

    private UserInfoModel entityToModel(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(moocUserT.getUuid());
        userInfoModel.setUserName(moocUserT.getUserName());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setNickName(moocUserT.getNickName());
        userInfoModel.setLifeState(moocUserT.getLifeState().toString());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setAddress(moocUserT.getAddress());

        return  userInfoModel;
    }

    private Date time2Date(long time) {
        Date date = new Date(time);
        return date;
    }
    @Override
    public UserInfoModel getUserInfo(int uuid) {

        // 根据主键查询用户信息
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        // MoocUserT 转换为 UserInfoModel
        UserInfoModel userInfoModel = entityToModel(moocUserT);
        // 返回 UserInfoModel
        return userInfoModel;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        // 将 userInfoModel 转换为 MoocUserT；
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(null);//time2Date(System.currentTimeMillis())
        moocUserT.setNickName(userInfoModel.getNickName());
        moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(null);//time2Date(userInfoModel.getBeginTime())
        // 保存到数据库
        Integer integer = moocUserTMapper.updateById(moocUserT);
        if (integer > 0) {
            // 再通过id 将数据查出来，返回前端
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            return userInfo;
        } else {
            return userInfoModel;
        }

    }
}
