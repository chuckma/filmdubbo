package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.OrderQueryVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.BigDecimalUtil;
import com.stylefeng.guns.core.util.UUIDUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrder2018TMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrder2018TMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrder2018T;
import com.stylefeng.guns.rest.common.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasma
 */
@Slf4j
@Component
@Service(interfaceClass = OrderServiceAPI.class,group = "order2018")
public class OrderServiceImpl2018 implements OrderServiceAPI {

    @Autowired
    private MoocOrder2018TMapper moocOrder2018TMapper;

    @Autowired
    private FtpUtil ftpUtil;

    @Reference(interfaceClass = CinemaServiceAPI.class,check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    // 验证出售的票是否为真
    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        String seatsPath = moocOrder2018TMapper.getSeatsByFieldId(fieldId);
        // 读取座位图，判断 seats 是否为真
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatsPath);

        // 将 fileStrByAddress 转为 json对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();



        // 每一次匹配上 isTrue ++
        String[] seatsArr = seats.split(",");
        String[] idArrs = ids.split(",");

        for (String idArr : idArrs) {
            log.info(idArr);
        }


        int isTrue = 0;
        for (String idArr : idArrs) {
            for (String s : seatsArr) {
                if (s.equalsIgnoreCase(idArr)) {
                    isTrue++;
                }
            }
        }
        // 如果匹配上的座位数和已售座位数量y一致，则说明都是正确的
        if (seatsArr.length == isTrue) {
            return true;
        } else {
            return false;
        }
    }

    // 判断该座位 是否已经被销售
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {

        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id", fieldId);
        List<MoocOrder2018T> list = moocOrder2018TMapper.selectList(entityWrapper);

        String[] seatsArr = seats.split(",");

        // 但凡一个匹配上就返回 false
        for (MoocOrder2018T moocOrderT : list) {
            String[] ids = moocOrderT.getSeatsIds().split(",");
            for (String id : ids) {
                for (String s : seatsArr) {
                    if (s.equalsIgnoreCase(id)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // 创建订单
    @Override
    public OrderVO saveOrderInfo(
            Integer fieldId, String soldSeats, String seatsName, Integer userId) {

        String uuid = UUIDUtil.genUUID();
        FilmInfoVO filmInfoVO = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        Integer filmId = Integer.parseInt(filmInfoVO.getFilmId());

        OrderQueryVO orderQueryVO = cinemaServiceAPI.getOrderInfo(fieldId);
        Integer cinemaId = Integer.parseInt(orderQueryVO.getCinemaId());
        double filmPrive = Double.parseDouble(orderQueryVO.getFilmPrice());

        // 求订单金额

        int solds = soldSeats.split(",").length;
        double totalPrice = BigDecimalUtil.mul(solds, filmPrive).doubleValue();


        MoocOrder2018T moocOrderT = new MoocOrder2018T();
        moocOrderT.setUuid(uuid);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setOrderUser(userId);
        moocOrderT.setOrderPrice(totalPrice);
        moocOrderT.setFilmPrice(filmPrive);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setCinemaId(cinemaId);


        Integer insert = moocOrder2018TMapper.insert(moocOrderT);
        if (insert > 0) {
            // 返回查询结果
            OrderVO orderVO = moocOrder2018TMapper.getOrderInfoById(uuid);
            if (orderVO == null || orderVO.getOrderId() == null) {
                log.error("订单信息查询失败，订单编号为{}", uuid);
                return null;
            } else {
                return orderVO;
            }
        } else {
            // 插入错误
            log.error("订单插入失败");
            return null;
        }
    }


    // 订单列表
    @Override
    public Page<OrderVO> listOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> result = new Page<>();
        if (userId == null) {
            log.error("订单查询失败，用户编号未传入");
            return null;
        } else {
            List<OrderVO> ordersByUserId = moocOrder2018TMapper.getOrdersByUserId(userId, page);
            if (ordersByUserId == null & ordersByUserId.size() == 0) {
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
                return result;
            } else {
                // 获取订单总数
                EntityWrapper<MoocOrder2018T> entityWrapper = new EntityWrapper();
                entityWrapper.eq("order_user", userId);
                Integer counts = moocOrder2018TMapper.selectCount(entityWrapper);
                result.setTotal(counts);
                result.setRecords(ordersByUserId);
                return result;
            }
        }
    }

    // 场次座位信息
    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {

        if(fieldId == null){
            log.error("查询已售座位错误，未传入任何场次编号");
            return "";
        }else{
            String soldSeatsByFieldId = moocOrder2018TMapper.getSoldSeatsByFieldId(fieldId);
            return soldSeatsByFieldId;
        }
    }
}
