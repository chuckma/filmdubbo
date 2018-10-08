package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.TokenBucket;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasma
 */
@Slf4j
@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    private static TokenBucket tokenBucket = new TokenBucket();

    @Reference(
            interfaceClass = OrderServiceAPI.class,
            check = false,
            group = "order2018"
    )
    private OrderServiceAPI orderServiceAPI;




    @Reference(
            interfaceClass = OrderServiceAPI.class,
            check = false,
            group = "order2017"
    )
    private OrderServiceAPI orderServiceAPI2017;




//    public ResponseVO error(Integer fieldId,String soldSeats,String seatsName){
//        return ResponseVO.serviceFile("抱歉，下单的人太多了，请稍后重试");
//    }

    // 购票
    /*
        信号量隔离
        线程池隔离
        线程切换
     */
    /*@HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
            })*/

    /**
     * 售票接口
     *
     * @param fieldId   场次
     * @param soldSeats 座位
     * @param seatsName
     * @return
     */
    @PostMapping(value = "butTickets")
    public ResponseVO butTickets(Integer fieldId, String soldSeats, String seatsName) {
        try {
            if (tokenBucket.getToken()) {
                // 验证出售的票是否为真
                boolean trueSeats = orderServiceAPI.isTrueSeats(fieldId + "", soldSeats);

                // 判断该座位 是否已经被销售
                boolean isNotSold = orderServiceAPI.isNotSoldSeats(fieldId + "", soldSeats);

                // 验证是否需要创建订单信息
                if (trueSeats && isNotSold) {
                    // 创建订单 ？ 获取登录人
                    String userId = CurrentUser.getUserId();
                    if (userId == null && userId.trim().length() == 0) {
                        return ResponseVO.serviceFile("用户未登录");
                    }
                    OrderVO orderVO = orderServiceAPI.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
                    if (orderVO == null) {
                        log.error("购票失败！");
                        return ResponseVO.serviceFile("购票异常！");
                    } else {
                        return ResponseVO.success(orderVO);
                    }
                } else {
                    return ResponseVO.serviceFile("订单中的座位异常！");
                }
            } else {
                return ResponseVO.serviceFile("购票人数太多，请稍后再试！");
            }

        } catch (Exception e) {
            log.error("购票异常",e);
            return ResponseVO.serviceFile("购票异常");
        }

    }


    @PostMapping(value = "getOrderInfo")
    public ResponseVO getOrderInfo(@RequestParam(value = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize) {

        // 获取当前登录人的信息
        String userId = CurrentUser.getUserId();

        // 使用当前登录人的查询其订单信息
        Page<OrderVO> page = new Page<>(nowPage, pageSize);
        if (userId != null && userId.length() > 0) {
            Page<OrderVO> result = orderServiceAPI.listOrderByUserId(Integer.parseInt(userId), page);

            Page<OrderVO> result2017 = orderServiceAPI2017.listOrderByUserId(Integer.parseInt(userId), page);

            log.error(result2017.getRecords()+" , "+result.getRecords());
            // 合并结果集
            int totalPages = (int) (result.getPages()+result2017.getPages());
            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());
            orderVOList.addAll(result2017.getRecords());


            return ResponseVO.success(nowPage, totalPages, "", orderVOList);

        } else {
            return ResponseVO.serviceFile("用户未登录");
        }
    }

}
