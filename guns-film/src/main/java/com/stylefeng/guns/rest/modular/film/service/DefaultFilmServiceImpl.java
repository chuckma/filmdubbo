package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasma
 */
@Component
@Service(interfaceClass = FilmServiceAPI.class)
public class DefaultFilmServiceImpl implements FilmServiceAPI {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;


    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> res = new ArrayList<>();
        List<MoocBannerT> moocBannerTS = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBannerTS) {
            BannerVO bannerVO  =new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid() + "");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            res.add(bannerVO);
        }
        return res;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        // 是否是首页需要的内容
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 热映影片的条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        if (isLimit) {
            // 限制条数
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

            // 组装 filmInfo
             filmInfos = getFilmInfos(moocFilmTS);
             filmVO.setFilmNum(moocFilmTS.size());
             filmVO.setFilmInfo(filmInfos);
        } else {
            // 列表
        }
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        // 是否是首页需要的内容
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 即将上映的条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        if (isLimit) {
            // 限制条数
            Page<MoocFilmT> page = new Page<>(1,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

            // 组装 filmInfo
            filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmNum(moocFilmTS.size());
            filmVO.setFilmInfo(filmInfos);

        } else {
            // 列表
        }
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 票房排行
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {
        List<CatVO> catVOS = new ArrayList<>();
        // 查询实体对象 MoocCatDictT
        List<MoocCatDictT> moocCatDictTS = this.moocCatDictTMapper.selectList(null);
        // 将实体对象转换为业务对象 CatVO
        for (MoocCatDictT moocCatDictT : moocCatDictTS) {
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCatDictT.getUuid() + "");
            catVO.setCatName(moocCatDictT.getShowName());
            catVOS.add(catVO);
        }
        return catVOS;
    }

    @Override
    public List<SourceVO> getSource() {
        List<SourceVO> sourceVOS = new ArrayList<>();
        List<MoocSourceDictT> sourceDictTS = this.moocSourceDictTMapper.selectList(null);
        for (MoocSourceDictT sourceDictT : sourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(sourceDictT.getUuid() + "");
            sourceVO.setSourceName(sourceDictT.getShowName());
            sourceVOS.add(sourceVO);
        }
        return sourceVOS;
    }

    @Override
    public List<YearVO> getYear() {
        List<YearVO> yearVOS = new ArrayList<>();
        List<MoocYearDictT> yearDictTS = this.moocYearDictTMapper.selectList(null);
        for (MoocYearDictT yearDictT : yearDictTS) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(yearDictT.getUuid() + "");
            yearVO.setYearName(yearDictT.getShowName());

            yearVOS.add(yearVO);

        }
        return yearVOS;
    }


    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MoocFilmT moocFilmT : moocFilmTS) {

            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid() + "");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }


}
