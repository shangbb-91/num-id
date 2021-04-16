package com.shangbb.strengthen.exception.data;

/**
 * 数据未找到
 *
 * @Author benben.shang
 * @Date 2021/2/6 15:51
 */
public class DataNotFoundException extends DataException {
    private final Integer status = super.getErrorCode();

    public DataNotFoundException(String msg) {
        super.msg = msg;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }

}
