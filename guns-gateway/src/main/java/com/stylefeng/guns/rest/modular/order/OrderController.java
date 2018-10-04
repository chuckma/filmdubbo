package com.stylefeng.guns.rest.modular.order;

import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lucasma
 */

@RestController
@RequestMapping(value = "/order/")
public class OrderController {


    /**
     * 售票接口
     * @param fieldId 场次
     * @param soldSeats 座位
     * @param seatsName
     * @return
     */
    @PostMapping(value = "butTickets")
    public ResponseVO butTickets(Integer fieldId, String soldSeats, String seatsName) {

        // 验证出售的票是否为真

        // 判断该座位 是否已经被销售

        // 创建订单 ？ 获取登录人
        return null;
    }


    @PostMapping(value = "getOrderInfo")
    public ResponseVO getOrderInfo(@RequestParam(value = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize) {

        // 获取当前登录人的信息

        // 使用当前登录人的查询其订单信息

        return null;
    }

}
