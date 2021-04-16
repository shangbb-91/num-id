package com.shangbb.strengthen.exception;

/**
 * @Author benben.shang
 * @Date 2021/2/5 10:15
 */
public abstract class BaseException extends RuntimeException {
    protected String msg;

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    abstract public Integer getStatus();

    public Integer getCode() {
        return getStatus();
    }

    public String getMsg() {
        return this.getClass().getSimpleName() + ":" + this.msg;
    }

    public String realMsg() {
        return this.msg;
    }

}
