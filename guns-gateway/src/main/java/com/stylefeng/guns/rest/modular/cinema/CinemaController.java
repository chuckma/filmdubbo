package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lucasma
 */
@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    private static final String IMG_PRE = "http://img.meetingshop.cn";
    @Reference(interfaceClass = CinemaServiceAPI.class,connections = 10,cache = "lru",check = false)
    private CinemaServiceAPI cinemaServiceAPI;


    /**
     * 1、	查询影院列表-根据条件查询所有影院
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping(value = "getCinemas")
    public ResponseVO  getCinemas(CinemaQueryVO cinemaQueryVO) {

        try {
            // 按照条件进行筛选
            Page<CinemaVO> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVO);
            // 判断是否有满足条件的影院
            if (cinemas.getRecords() == null || cinemas.getRecords().size() == 0) {
                return ResponseVO.success("没有符合条件的影院");
            } else {
                return ResponseVO.success(cinemas.getCurrent(),(int)cinemas.getPages(),"",cinemas.getRecords());
            }
        } catch (Exception e) {
            log.error("获取影院列表异常",e);
            return ResponseVO.serviceFile("查询影院列表失败！");
        }
    }

    /**
     * 1 热点数据 接口 dubbo 缓存 cache="lru "
     * 2
     * 获取影院列表查询条件
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping(value = "getCondition")
    public ResponseVO  getCondition(CinemaQueryVO cinemaQueryVO) {

        try {
            // 获取 3 个集合，封装成 1 个对象返回
            List<BrandVO> brandVOS = cinemaServiceAPI.listBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areaVOS = cinemaServiceAPI.listAreas(cinemaQueryVO.getDistrictId());
            List<HallTypeVO> hallTypeVOS = cinemaServiceAPI.listHallType(cinemaQueryVO.getHallType());

            CinemaConditionResponseVO cinemaConditionResponseVO = new CinemaConditionResponseVO();
            cinemaConditionResponseVO.setAreaList(areaVOS);
            cinemaConditionResponseVO.setBrandList(brandVOS);
            cinemaConditionResponseVO.setHallTypeList(hallTypeVOS);
            return ResponseVO.success(cinemaConditionResponseVO);
        } catch (Exception e) {
            log.error("获取条件列表失败", e);
            return ResponseVO.serviceFile("获取影院查询条件失败");
        }
    }


    /**
     * 播放场次
     * @param cinemaId
     * @return
     */
    @GetMapping(value = "getFields")
    public ResponseVO  getFields(Integer cinemaId) {

        try {

            CinemaInfoVO cinemaInfo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            List<FilmInfoVO> filmInfoByCinemaId = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);
            CinemaFieldsResponseVO cinemaFieldResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldResponseVO.setCinemaInfoVO(cinemaInfo);
            cinemaFieldResponseVO.setFilmInfoVOList(filmInfoByCinemaId);

            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVO);
        } catch (Exception e) {

            log.error("获取播放场次失败", e);
            return ResponseVO.serviceFile("获取播放场次失败");
        }

    }

    /**
     * 场次详细信息
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @PostMapping(value = "getFieldInfo")
    public ResponseVO  getFieldInfo(Integer cinemaId,Integer fieldId) {

        try {

            CinemaInfoVO cinemaInfoById = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfoVO filmInfoByCinemaId = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            HallInfoVO filmFieldInfo = cinemaServiceAPI.getFilmFieldInfo(fieldId);



            // 假数据 ，后续对接订单接口
            filmFieldInfo.setSoldSeats("1,2,3");

            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoById);
            cinemaFieldResponseVO.setFilmInfo(filmInfoByCinemaId);
            cinemaFieldResponseVO.setHallInfoVO(filmFieldInfo);

            return ResponseVO.success(IMG_PRE,cinemaFieldResponseVO);
        } catch (Exception e) {
            log.error("获取选座信息失败！",e);
            return ResponseVO.serviceFile("获取选座信息失败!");
        }
    }

}
