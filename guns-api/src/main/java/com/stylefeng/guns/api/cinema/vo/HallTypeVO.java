package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 *
 * 影院类型
 */
@Data
public class HallTypeVO implements Serializable {

    private String  halltypeId;
    private String halltypeName;
    private boolean isActive;

}
