package com.shangbb.strengthen.exception.parameter;

/**
 * 参数无效
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamInvalidException extends ParamException {
    private final Integer status = super.getErrorCode() + 3;

    public ParamInvalidException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
