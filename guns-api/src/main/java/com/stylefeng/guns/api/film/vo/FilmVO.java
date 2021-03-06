package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lucasma
 */

@Data
public class FilmVO implements Serializable {

    private int filmNum;

    private int totalPage;

    private int nowPage;

    private List<FilmInfo> filmInfo;
}
