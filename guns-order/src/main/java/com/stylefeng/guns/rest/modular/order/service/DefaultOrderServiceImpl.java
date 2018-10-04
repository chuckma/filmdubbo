package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lucasma
 */
@Component
@Service(interfaceClass = OrderServiceAPI.class)
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    @Autowired
    private MoocOrderTMapper moocOrderTMapper;

    // 验证出售的票是否为真
    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        String seatsPath = moocOrderTMapper.getSeatsByFieldId(fieldId);
        return false;
    }

    // 判断该座位 是否已经被销售
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    // 创建订单
    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    // 订单列表
    @Override
    public List<OrderVO> listOrderByUserId(Integer userId) {
        return null;
    }

    // 场次座位信息
    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
