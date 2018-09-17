package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 */
@Data
public class BannerVO implements Serializable {

    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;
}
