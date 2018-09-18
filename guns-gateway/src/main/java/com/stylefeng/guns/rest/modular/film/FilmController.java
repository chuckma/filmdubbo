package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasma
 */
@RestController
@RequestMapping("/film/")
public class FilmController {


    /**
     * API 网关，dubbo 里体现的是
     * 1. 功能聚合 【API聚合】
     * 优点是：减少 http 请求，安全性，降低开发难度
     * 只暴露一个接口给前端。
     */

    private static final String IMG_PRE ="img.meetshop.cn";

    @Reference(interfaceClass = FilmServiceAPI.class)
    private FilmServiceAPI filmServiceAPI;

    /**
     * 获取首页信息
     *
     * @return
     */
    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getIndex() {

        FilmIndexVO filmIndexVO = new FilmIndexVO();
        // 获取banner
        filmIndexVO.setBanners(filmServiceAPI.getBanners());

        // 获取热映
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8));
        // 获取即将上映
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8));
        // 票房排行
        filmIndexVO.setBoxRanking(filmServiceAPI.getBoxRanking());
        // 受欢迎的榜单
        filmIndexVO.setExpectRanking(filmServiceAPI.getExpectRanking());
        // top 100
        filmIndexVO.setTop100(filmServiceAPI.getTop());

        return ResponseVO.success(IMG_PRE,filmIndexVO);
    }


    @GetMapping(value = "listConditions")
    public ResponseVO listConditions(@RequestParam(name = "catId",defaultValue = "99",required = false)String catId,
                                     @RequestParam(name = "sourceId",defaultValue = "99",required = false)String sourceId,
                                     @RequestParam(name = "yearId",defaultValue = "99",required = false)String yearId){

        boolean flag = false;
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        // 类型集合
        List<CatVO> catVOS = filmServiceAPI.getCats();
        List<CatVO> catRes = new ArrayList<>();

        CatVO cat = new CatVO();

        for (CatVO catVO : catVOS) {
            // 判断集合是否存在 catId，如果存在，则将对应的实体变成active
            // 如果不存在，则默认将全部变成 active

            // 6

            // 1,2,3,99,5
            // 优化 数据层查询 by id 有序集合  ->
            if (catVO.getCatId().equals("99")) {
                cat=catVO;
                continue;
            }
            if (catVO.getCatId().equals(catId)) {
                flag = true;
                catVO.setActive(true);
            } else {
                catVO.setActive(false);
            }

            catRes.add(catVO);

        }
        if (!flag) {
            cat.setActive(true);
            catRes.add(cat);
        } else {
            cat.setActive(true);
            catRes.add(cat);
        }
        // 片原集合
        flag=false;
        List<SourceVO> sourceVOS = filmServiceAPI.getSource();
        List<SourceVO> sourceRes = new ArrayList<>();

        SourceVO source = new SourceVO();

        for (SourceVO sourceVO : sourceVOS) {
            if (sourceVO.getSourceId().equals("99")) {
                source=sourceVO;
                continue;
            }
            if (sourceVO.getSourceId().equals(sourceId)) {
                flag = true;
                sourceVO.setActive(true);
            } else {
                sourceVO.setActive(false);
            }

            sourceRes.add(sourceVO);
        }

        if (!flag) {
            source.setActive(true);
            sourceRes.add(source);
        } else {
            source.setActive(false);
            sourceRes.add(source);
        }

        // 年代集合
        flag=false;
        List<YearVO> yearVOS = filmServiceAPI.getYear();
        List<YearVO> yearRes = new ArrayList<>();

        YearVO year = new YearVO();

        for (YearVO yearVO : yearVOS) {
            if (yearVO.getYearId().equals("99")) {
                year=yearVO;
                continue;
            }
            if (yearVO.getYearId().equals(yearId)) {
                flag = true;
                yearVO.setActive(true);
            } else {
                year.setActive(false);

            }

            yearRes.add(yearVO);

        }

        if (!flag) {
            year.setActive(true);
            yearRes.add(year);
        } else {
            year.setActive(false);
            yearRes.add(year);
        }


        filmConditionVO.setCatInfo(catRes);
        filmConditionVO.setSourceInfo(sourceRes);
        filmConditionVO.setYearInfo(yearRes);

        return ResponseVO.success(filmConditionVO);

    }
}