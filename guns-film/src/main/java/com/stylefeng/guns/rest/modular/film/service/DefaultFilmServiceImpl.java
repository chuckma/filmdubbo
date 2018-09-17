package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmInfoT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
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
        } else {
            // 列表
        }
        return null;
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
        } else {
            // 列表
        }
        return null;
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
        return null;
    }

    @Override
    public List<FilmVO> getTop() {
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return null;
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
