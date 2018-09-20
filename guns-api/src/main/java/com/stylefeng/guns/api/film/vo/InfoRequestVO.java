package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ImgVO;
import lombok.Data;

/**
 * Created by lucasma
 */
@Data
public class InfoRequestVO {

    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgVO;
    private String filmId;

}
