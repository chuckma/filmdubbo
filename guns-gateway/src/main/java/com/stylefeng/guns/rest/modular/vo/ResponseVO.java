package com.stylefeng.guns.rest.modular.vo;

/**
 * Created by lucasma
 */
public class ResponseVO<T> {


    // 返回状态
    private int status;
    // 返回信息
    private String msg;
    // 返回数据
    private T data;


    private  ResponseVO(){}

    public static<T> ResponseVO success(T data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);

        return responseVO;
    }
    // 业务异常
    public static<T> ResponseVO serviceFile(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO appFile(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(50001);
        responseVO.setMsg(msg);

        return responseVO;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
