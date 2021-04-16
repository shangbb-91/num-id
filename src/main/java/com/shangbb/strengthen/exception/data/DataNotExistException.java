package com.shangbb.strengthen.exception.data;

/**
 * 数据不存在
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataNotExistException extends DataException {
    private final Integer status = super.getErrorCode() + 1;

    public DataNotExistException(String msg) {
        super.msg = msg;
    }


    @Override
    public Integer getStatus() {
        return this.status;
    }
}
