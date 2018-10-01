package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 * 播放场次
 */
@Data
public class FilmFieldVO implements Serializable {

    private String fieldId;
    private String beginTime;
    private String endTime;
    private String language;
    private String hallName;
    private String price;
}
