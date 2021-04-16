package com.shangbb.strengthen.exception.data;

/**
 * 数据已过期
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataExpiredException extends DataException {
    private final Integer status = super.getErrorCode() + 3;

    public DataExpiredException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}
