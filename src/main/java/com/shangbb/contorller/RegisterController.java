package com.shangbb.contorller;

import com.shangbb.contorller.request.RegisterRq;
import com.shangbb.entity.Register;
import com.shangbb.service.RegisterService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author benben.shang
 * @Date 2021/4/9 15:44
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @PostMapping()
    public synchronized void register(@RequestBody RegisterRq registerRq) {
        if (Strings.isBlank(registerRq.getCode())) {
            throw new RuntimeException("code can't be null!");
        }
        if (Objects.isNull(registerRq.getMinLength()) || registerRq.getMinLength() < 8) {
            throw new RuntimeException("minLength can't be null and should bigger than 7!");
        }
        Register register = registerRq.build();
        registerService.register(register);
    }

}
