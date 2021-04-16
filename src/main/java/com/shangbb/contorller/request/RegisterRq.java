package com.shangbb.contorller.request;

import com.shangbb.entity.Register;
import lombok.Data;

/**
 * @Author benben.shang
 * @Date 2021/4/9 15:48
 */
@Data
public class RegisterRq {
    private String code;
    private Integer minLength;
    private Integer maxLength;
    private Integer cacheLength;

    public Register build() {
        Register register = new Register();
        register.setCode(this.code);
        register.setMinLength(this.minLength);
        register.setMaxLength(this.maxLength);
        register.setCacheLength(this.cacheLength);
        return register;
    }
}
