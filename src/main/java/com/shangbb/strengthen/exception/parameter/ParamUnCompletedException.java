package com.shangbb.strengthen.exception.parameter;

/**
 * 参数不全
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamUnCompletedException extends ParamException {
    private final Integer status = super.getErrorCode() + 4;

    public ParamUnCompletedException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
