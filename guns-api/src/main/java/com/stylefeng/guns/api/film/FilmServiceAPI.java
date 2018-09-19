package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * Created by lucasma
 */
public interface FilmServiceAPI {

    // 获取bannber
    List<BannerVO> getBanners();

    // 获取热映
    FilmVO getHotFilms(boolean isLimit, int nums,int nowPage,int sortId,int sourceId,int yeadId,int catId);

    // 获取即将上映
    FilmVO getSoonFilms(boolean isLimit, int nums,int nowPage,int sortId,int sourceId,int yeadId,int catId);


    // 获取经典影片
    FilmVO getClassicFilms(int nums,int nowPage,int sortId,int sourceId,int yeadId,int catId);

    // 获取票房排行
    List<FilmInfo> getBoxRanking();

    // 获取人气排行
    List<FilmInfo> getExpectRanking();

    // 获取top100
    List<FilmInfo> getTop();




    // 影片条件接口
    // 分类条件

    List<CatVO> getCats();


    // 片源

    List<SourceVO> getSource();

    // 年代

    List<YearVO> getYear();



    // 根据 影片ID 或者 影片名称获取影片详细信息
    // 获取影片其他信息 演员，图片地址。。。

    FilmDetailVO getFilmDetail(int searchType, String searchParam);

}
