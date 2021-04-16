package com.shangbb.strengthen.exception.parameter;

/**
 * 参数类型错误
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamTypeErrorException extends ParamException {
    private final Integer status = super.getErrorCode() + 5;

    public ParamTypeErrorException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
