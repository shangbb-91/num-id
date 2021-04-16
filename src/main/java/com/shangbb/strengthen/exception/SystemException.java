package com.shangbb.strengthen.exception;

/**
 * @Author benben.shang
 * @Date 2021/2/5 10:18
 */
public class SystemException extends BaseException {

    private final Integer status = 90000;

    public SystemException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
