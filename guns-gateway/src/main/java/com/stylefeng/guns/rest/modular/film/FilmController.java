package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lucasma
 */
@Controller
@RequestMapping("/film/")
public class FilmController {


    /**
     * API 网关，dubbo 里体现的是
     *  1. 功能聚合 【API聚合】
     *  优点是：减少 http 请求，安全性，降低开发难度
     *  只暴露一个接口给前端。
     */




    /**
     * 获取首页信息
     *
     * @return
     */
    @PostMapping(value = "getIndex")
    public ResponseVO getIndex() {

        // 获取banner


        // 获取热映

        // 获取即将上映

        // 票房排行

        // 受欢迎的榜单

        // top 100
        return null;
    }
}
