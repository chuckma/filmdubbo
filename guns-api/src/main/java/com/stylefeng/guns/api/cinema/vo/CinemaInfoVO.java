package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 * 播放场次
 */
@Data
public class CinemaInfoVO implements Serializable {

    private String  cinemaId;
    private String  cinemaName;
    private String imgUrl;
    private String cinemaAddress;
    private String cinemaPhone;
}
