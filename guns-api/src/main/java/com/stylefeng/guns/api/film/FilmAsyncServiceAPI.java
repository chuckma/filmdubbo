package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * Created by lucasma
 */
public interface FilmAsyncServiceAPI {

    // 同步调用接口
    // 根据 影片ID 或者 影片名称获取影片详细信息
    // 获取影片其他信息 演员，图片地址。。。

//    FilmDetailVO getFilmDetail(int searchType, String searchParam);


    // 影片描述信息
    FilmDescVO getFilmDesc(String filmId);

    // 图片地址
    ImgVO getImgs(String filmId);


    // 获取导演
    ActorVO getDirector(String filmId);

    // 演员信息
    List<ActorVO> getActors(String filmId);

}
