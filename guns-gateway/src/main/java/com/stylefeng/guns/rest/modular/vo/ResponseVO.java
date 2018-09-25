package com.stylefeng.guns.rest.modular.vo;

import lombok.Data;

/**
 * Created by lucasma
 */

@Data
public class ResponseVO<T> {


    // 返回状态
    private int status;
    // 返回信息
    private String msg;
    // 返回数据
    private T data;

    // 图片前缀
    private String imgPre;

    private int nowPage;

    // 分页
    private int totalPage;
    private  ResponseVO(){}


    public static<T> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static<T> ResponseVO success(int nowPage,int totalPage,String imgPre,T data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    public static<T> ResponseVO success(String imgPre,T data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        return responseVO;
    }


    public static<T> ResponseVO success(T data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);

        return responseVO;
    }
    // 业务异常
    public static<T> ResponseVO serviceFile(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO appFile(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(50001);
        responseVO.setMsg(msg);

        return responseVO;
    }

}
