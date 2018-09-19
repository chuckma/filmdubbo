package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 */

@Data
public class FilmDetailVO implements Serializable {


    private String filmName;
    private String filmEnName;
    private String score;
    private String imgAddress;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;

}
