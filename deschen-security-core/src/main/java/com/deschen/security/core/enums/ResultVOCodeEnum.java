package com.deschen.security.core.enums;

/**
 * @Author deschen
 * @Create 2019/8/12
 * @Description 自定义状态码
 * @Since 1.0.0
 */
public enum ResultVOCodeEnum implements IEnum{
    /*Http规定的状态码
    * 1xx：相关信息
      2xx：操作成功
      3xx：重定向
      4xx：客户端错误
      5xx：服务器错误
      自定义请求：除200，500外其他最好在6XX之后的定义，以免跟里面的状态码混淆
    * */

    SUCCESS(200, "处理成功"),
    // 自定义异常
    PARAM_ERROR(600, "参数错误"),
    RETURN_NULL(601, "返回值为空"),
    OPERATION_ERROR(602, "操作失败"),
    // 用于未能确定的异常
    SERVER_ERROR(500, "服务器内部错误"),

    ;

    private Integer code;
    private String msg;

    ResultVOCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
