package com.shangbb.strengthen.exception.parameter;

/**
 * 参数非法
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamIllegalException extends ParamException {
    private final Integer status = super.getErrorCode() + 2;

    public ParamIllegalException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
