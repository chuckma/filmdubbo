package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lucasma
 * <p>
 * 影院类型
 */
@Data
public class HallInfoVO implements Serializable {

    private String hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    private String soldSeats; // 已售座位必须关联订单信息才能查询

}
