package com.shangbb.strengthen.exception.parameter;

/**
 * 参数为NULL
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamNullException extends ParamException {
    private final Integer status = super.getErrorCode() + 1;

    public ParamNullException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
