package com.shangbb.strengthen.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResponse<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public transient static final String SUCCESS_STR = "success";


    public RestResponse<T> successResp(T data) {
        this.data = data;
        this.msg = SUCCESS_STR;
        this.code = 200;
        return this;
    }

    public RestResponse<T> successResp() {
        return successResp(null);
    }

    public RestResponse<T> errorResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

}
