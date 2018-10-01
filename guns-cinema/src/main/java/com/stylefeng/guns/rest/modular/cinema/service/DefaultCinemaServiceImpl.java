package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MoocCinemaT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasma
 */
@Component
@Service(interfaceClass = CinemaServiceAPI.class)
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;

    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;

    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;

    @Autowired
    private MoocFieldTMapper moocFieldTMapper;

    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;

    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;

    //  1、根据CinemaQueryVO，查询影院列表
    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        List<CinemaVO> cinemaVOS = new ArrayList<>();
         //  判断是否有查询条件 brandId , dictId,hallType  是否 == 99 查询 all
        Page<MoocCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>(); // 业务条件对象
        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }
        if (cinemaQueryVO.getHallType() != 99) {
            entityWrapper.like("hall_ids", "%#+"+cinemaQueryVO.getHallType()+"+#%");
        }
        if (cinemaQueryVO.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }
        // 数据实体转为 业务实体
        List<MoocCinemaT> moocCinemaTS = moocCinemaTMapper.selectPage(page, entityWrapper);
        for (MoocCinemaT moocCinemaT : moocCinemaTS) {
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setUuid(moocCinemaT.getUuid()+"");
            cinemaVO.setMinimumPrice(moocCinemaT.getMinimumPrice() + "");
            cinemaVO.setCinemaName(moocCinemaT.getCinemaName());
            cinemaVO.setAddress(moocCinemaT.getCinemaAddress());

            cinemaVOS.add(cinemaVO);
        }
        // 根据条件判断影片总数
        long count = moocCinemaTMapper.selectCount(entityWrapper);

        // 返回业务对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemaVOS);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(count);

        return result;
    }

    //  2、根据条件获取品牌列表 除 99 之外，其他都是 isActive
    @Override
    public List<BrandVO> listBrands(int brandId) {
        return null;
    }

    //  3、获取行政区域列表
    @Override
    public List<AreaVO> listAreas(int areaId) {
        return null;
    }

    //  4、获取影厅类型列表
    @Override
    public List<HallTypeVO> listHallType(int hallType) {
        return null;
    }

    //  5、根据影院编号，获取影院信息
    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {
        return null;
    }

    //  6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    @Override
    public FilmInfoVO getFilmInfoByCinemaId(int cinemaId) {
        return null;
    }

    //  7、根据放映场次ID获取放映信息
    @Override
    public FilmFieldVO getFilmFieldInfo(int fieldId) {
        return null;
    }

    //  8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return null;
    }
}
