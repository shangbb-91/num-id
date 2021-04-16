package com.shangbb.strengthen.exception.data;

/**
 * 数据类型非法
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataTypeIllegalException extends DataException {
    private final Integer status = super.getErrorCode() + 5;

    public DataTypeIllegalException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
