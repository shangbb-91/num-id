package com.shangbb.strengthen.exception;

/**
 * @Author benben.shang
 * @Date 2021/2/5 10:18
 */
public class ApiException extends BaseException {

    private final Integer status = 70000;

    public ApiException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
