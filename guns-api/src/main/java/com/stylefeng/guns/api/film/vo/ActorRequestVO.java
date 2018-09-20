package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.util.List;


/**
 * Created by lucasma
 */
@Data
public class ActorRequestVO {

    private ActorVO director;
    private List<ActorVO> actorVOS;
}
