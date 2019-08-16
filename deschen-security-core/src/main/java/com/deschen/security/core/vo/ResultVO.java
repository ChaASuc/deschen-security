package com.deschen.security.core.vo;

import com.deschen.security.core.enums.ResultVOCodeEnum;

import java.io.Serializable;

/**
 * @Author deschen
 * @Create 2019/8/12
 * @Description 与前端数据交互统一模板
 * @Since 1.0.0
 */
public class ResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 状态码
    private Integer code;

    // 消息
    private String msg;

    // 数据
    private Object data;


    public ResultVO setCode(Integer code) {
        this.code = code;
        return this;
    }


    public ResultVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    public ResultVO setData(Object data) {
        this.data = data;
        return this;
    }


    public static  ResultVO success(Object obj) {
        return new ResultVO()
                //为null统一给前端{}
                .setData(obj)
                .setCode(ResultVOCodeEnum.SUCCESS.getCode())
                .setMsg(ResultVOCodeEnum.SUCCESS.getMsg());
    }

    public static  ResultVO success() {
        return ResultVO.success(null);
    }

    public static ResultVO failure(ResultVOCodeEnum ResultVOCodeEnum) {
        return new ResultVO()
                .setCode(ResultVOCodeEnum.getCode())
                .setMsg(ResultVOCodeEnum.getMsg());
    }

    public static ResultVO failure(Integer code, String msg) {
        return new ResultVO().setCode(code).setMsg(msg);
    }

    public static ResultVO failure(Integer code, String msg, Object data) {
        return new ResultVO().setCode(code).setMsg(msg).setData(data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
