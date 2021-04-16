package com.shangbb.strengthen;

import com.shangbb.strengthen.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author benben.shang
 * @Date 2021/2/5 10:40
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class GlobalSuccessHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String returnTypeName = returnType.getParameterType().getName();
        if ("void".equals(returnTypeName)) {
            return new RestResponse<>().successResp(RestResponse.SUCCESS_STR);
        }
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }
        if (returnTypeName.contains(RestResponse.class.getName())) {
            return body;
        }
        return new RestResponse<>().successResp(body);
    }

}
