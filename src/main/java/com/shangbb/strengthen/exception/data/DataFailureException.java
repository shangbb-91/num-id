package com.shangbb.strengthen.exception.data;

/**
 * 数据已失效
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataFailureException extends DataException {
    private final Integer status = super.getErrorCode() + 4;

    public DataFailureException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
