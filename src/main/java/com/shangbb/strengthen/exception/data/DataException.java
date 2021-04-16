package com.shangbb.strengthen.exception.data;

import com.shangbb.strengthen.exception.BaseException;
import lombok.Data;

/**
 * 数据异常
 *
 * @Author benben.shang
 * @Date 2021/2/5 10:18
 */
@Data
abstract class DataException extends BaseException {
    protected final Integer errorCode = 10000;

}
