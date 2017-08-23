package com.stylefeng.guns.rest.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.persistence.dao.UserMapper;
import com.stylefeng.guns.rest.persistence.model.User;
import com.stylefeng.guns.rest.service.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean validate(Map<String, Object> params) {
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("userName", params.get("userName")));
        if(users != null && users.size() > 0){
            return true;
        }else{
            return false;
        }
    }
}
