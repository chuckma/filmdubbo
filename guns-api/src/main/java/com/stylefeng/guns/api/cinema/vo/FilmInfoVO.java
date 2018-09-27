package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lucasma
 * 播放场次
 */
@Data
public class FilmInfoVO implements Serializable {

    private String filmId;
    private String filmName;
    private String filmType;
    private String filmCats;
    private String filmActors;
    private String imgAddress;
    private List<FilmFieldVO> filmFields;
}
