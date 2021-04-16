package com.shangbb.strengthen;

import com.shangbb.strengthen.exception.BaseException;
import com.shangbb.strengthen.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author benben.shang
 * @Date 2021/2/5 9:41
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public RestResponse<?> baseException(HttpServletResponse response, BaseException ex) {
        return new RestResponse<>().errorResp(ex.getCode(), ex.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RestResponse<?> globalException(HttpServletRequest request, Exception ex) {
        log.error(ex.getMessage(), ex);
        if (ex instanceof NullPointerException) {
            return new RestResponse<>().errorResp(90000, "NullPointerException");
        }
        return new RestResponse<>().errorResp(90000, ex.getMessage());
    }
}
