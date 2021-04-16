package com.shangbb.strengthen.exception.data;

/**
 * 数据不完整
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataUnCompletedException extends DataException {
    private final Integer status = super.getErrorCode() + 6;

    public DataUnCompletedException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
