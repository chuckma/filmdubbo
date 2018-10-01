package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MoocAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MoocHallDictT;
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
        boolean flag = false;
        List<BrandVO> brandVOS = new ArrayList<>(); // 业务对象
        // 判断传入的 brandId 是否存在
        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);
        // branId 是否等于 99
        if (brandId == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null) {
            flag=true;
        }

        // 查询所有列表
        List<MoocBrandDictT> moocBrandDictTS = moocBrandDictTMapper.selectList(null);

        // flag == true  则将 99 至 为 active
        for (MoocBrandDictT brandDictT : moocBrandDictTS) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandName(brandDictT.getShowName());
            brandVO.setBrandId(brandDictT.getUuid() + "");

            // flag = true 则是 99
            if (flag) {
                if (brandDictT.getUuid() == 99) {
                    brandVO.setActive(true);
                }
            } else {
                if (brandDictT.getUuid() == brandId) {
                    brandVO.setActive(true);
                }
            }
            brandVOS.add(brandVO);
        }
        return brandVOS;
    }

    //  3、获取行政区域列表
    @Override
    public List<AreaVO> listAreas(int areaId) {
        boolean flag = false;
        List<AreaVO> areaVOS = new ArrayList<>(); // 业务对象
        // 判断传入的 brandId 是否存在
        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);
        // branId 是否等于 99
        if (areaId == 99 || moocAreaDictT == null || moocAreaDictT.getUuid() == null) {
            flag=true;
        }

        // 查询所有列表
        List<MoocAreaDictT> moocAreaDictTS = moocAreaDictTMapper.selectList(null);

        // flag == true  则将 99 至 为 active
        for (MoocAreaDictT areaDictT : moocAreaDictTS) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaName(areaDictT.getShowName());
            areaVO.setAreaId(areaDictT.getUuid() + "");

            // flag = true 则是 99
            if (flag) {
                if (areaDictT.getUuid() == 99) {
                    areaVO.setActive(true);
                }
            } else {
                if (areaDictT.getUuid() == areaId) {
                    areaVO.setActive(true);
                }
            }
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    //  4、获取影厅类型列表
    @Override
    public List<HallTypeVO> listHallType(int hallType) {
        boolean flag = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>(); // 业务对象
        // 判断传入的 brandId 是否存在
        MoocHallDictT moocBrandDictT = moocHallDictTMapper.selectById(hallType);
        // branId 是否等于 99
        if (hallType == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null) {
            flag=true;
        }

        // 查询所有列表
        List<MoocHallDictT> moocHallDictTS = moocHallDictTMapper.selectList(null);

        // flag == true  则将 99 至 为 active
        for (MoocHallDictT hallDictT : moocHallDictTS) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeName(hallDictT.getShowName());
            hallTypeVO.setHalltypeId(hallDictT.getUuid() + "");

            // flag = true 则是 99
            if (flag) {
                if (hallDictT.getUuid() == 99) {
                    hallTypeVO.setActive(true);
                }
            } else {
                if (hallDictT.getUuid() == hallType) {
                    hallTypeVO.setActive(true);
                }
            }
            hallTypeVOS.add(hallTypeVO);
        }
        return hallTypeVOS;
    }

    //  5、根据影院编号，获取影院信息
    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {


        // 数据实体
        MoocCinemaT moocCinemaT = moocCinemaTMapper.selectById(cinemaId);

        // 业务实体
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setImgUrl(moocCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(moocCinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(moocCinemaT.getCinemaName());
        cinemaInfoVO.setCinemaId(moocCinemaT.getUuid() + "");
        cinemaInfoVO.setCinemaAddress(moocCinemaT.getCinemaAddress());

        return cinemaInfoVO;
    }

    //  6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {

        List<FilmInfoVO> filmInfos = moocFieldTMapper.getFilmInfos(cinemaId);

        return filmInfos;
    }

    //  7、根据放映场次ID获取放映信息
    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId) {

        HallInfoVO hallInfo = moocFieldTMapper.getHallInfo(fieldId);

        return hallInfo;
    }

    //  8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        FilmInfoVO filmInfo = moocFieldTMapper.getFilmInfoById(fieldId);

        return filmInfo;
    }
}
