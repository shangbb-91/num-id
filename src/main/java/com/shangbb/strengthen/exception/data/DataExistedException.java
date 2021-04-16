package com.shangbb.strengthen.exception.data;

/**
 * 数据已存在
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataExistedException extends DataException {
    private final Integer status = super.getErrorCode() + 6;

    public DataExistedException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
