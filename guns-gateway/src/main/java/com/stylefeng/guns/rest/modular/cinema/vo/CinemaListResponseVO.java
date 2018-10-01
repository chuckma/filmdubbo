package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaVO;
import lombok.Data;

import java.util.List;

/**
 * Created by lucasma
 */
@Data
public class CinemaListResponseVO {

    private List<CinemaVO> cinemaVOS;

}
