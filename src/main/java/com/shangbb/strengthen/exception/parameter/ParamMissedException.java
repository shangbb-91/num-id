package com.shangbb.strengthen.exception.parameter;

/**
 * 参数缺失
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class ParamMissedException extends ParamException {
    private final Integer status = super.getErrorCode();

    public ParamMissedException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
