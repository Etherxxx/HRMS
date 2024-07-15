package com.example.spring.common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Result {
    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    //code:返回结果状态码
    private String code;
    //msg:存储报错信息
    private String msg;
    //data:数据
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("success");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }
}
