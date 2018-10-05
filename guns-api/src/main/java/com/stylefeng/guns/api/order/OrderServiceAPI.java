package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;

import java.util.List;

/**
 * Created by lucasma
 * <p>
 * 订单 api
 */
public interface OrderServiceAPI {

    // 验证出售的票是否为真
    boolean isTrueSeats(String fieldId, String seats);

    // 判断该座位 是否已经被销售
    boolean isNotSoldSeats(String fieldId, String seats);

    // 创建订单
    OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName,Integer userId);

    // 使用当前登录人的查询其订单信息
    Page<OrderVO> listOrderByUserId(Integer userId, Page<OrderVO> page);

    // fieldId 获取搜索订单座位信息
    String getSoldSeatsByFieldId(Integer fieldId);

}
