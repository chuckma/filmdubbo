package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 */

@Data
public class OrderQueryVO implements Serializable {

    private String cinemaId;
    private String filmPrice;
    
}
