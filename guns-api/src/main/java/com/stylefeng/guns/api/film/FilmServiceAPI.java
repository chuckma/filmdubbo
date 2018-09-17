package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;

import java.util.List;

/**
 * Created by lucasma
 */
public interface FilmServiceAPI {

    // 获取bannber
    List<BannerVO> getBanners();

    // 获取热映
    FilmVO getHotFilms(boolean isLimit, int nums);

    // 获取即将上映
    FilmVO getSoonFilms(boolean isLimit, int nums);

    // 获取票房排行
    List<FilmInfo> getBoxRanking();

    // 获取人气排行
    List<FilmInfo> getExpectRanking();

    // 获取top100
    List<FilmVO> getTop();

}
