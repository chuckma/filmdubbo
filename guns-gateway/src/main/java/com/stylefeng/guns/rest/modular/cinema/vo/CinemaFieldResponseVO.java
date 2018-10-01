package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import lombok.Data;

import java.util.List;

/**
 * Created by lucasma
 */
@Data
public class CinemaFieldResponseVO {


    private CinemaInfoVO cinemaInfoVO;
    private List<FilmInfoVO> filmInfoVOList;
}
