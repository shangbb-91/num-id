package com.shangbb.strengthen.exception.data;

/**
 * 数据不合法
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataIllegalException extends DataException {
    private final Integer status = super.getErrorCode() + 2;

    public DataIllegalException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
