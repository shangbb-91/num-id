package com.shangbb.strengthen.exception.parameter;

import com.shangbb.strengthen.exception.BaseException;
import lombok.Data;

/**
 * 参数异常
 *
 * @Author benben.shang
 * @Date 2021/2/5 10:18
 */
@Data
abstract class ParamException extends BaseException {
    protected final Integer errorCode = 20000;

}
