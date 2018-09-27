package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lucasma
 */

@Controller
@RequestMapping("/cinema/")
public class CinemaController {


    @Reference(interfaceClass = CinemaServiceAPI.class,check = false)
    private CinemaServiceAPI cinemaServiceAPI;


    /**
     * 1、	查询影院列表-根据条件查询所有影院
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping(value = "getCinemas")
    public ResponseVO  getCinemas(CinemaQueryVO cinemaQueryVO) {

        // 按照条件进行筛选

        return null;
    }

    /**
     * 获取影院列表查询条件
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping(value = "getCondition")
    public ResponseVO  getCondition(CinemaQueryVO cinemaQueryVO) {


        return null;
    }


    /**
     * 播放场次
     * @param cinemaId
     * @return
     */
    @GetMapping(value = "getFields")
    public ResponseVO  getFields(Integer cinemaId) {


        return null;
    }

    /**
     * 场次详细信息
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @GetMapping(value = "getFieldInfo")
    public ResponseVO  getFieldInfo(Integer cinemaId,Integer fieldId) {


        return null;
    }

}
