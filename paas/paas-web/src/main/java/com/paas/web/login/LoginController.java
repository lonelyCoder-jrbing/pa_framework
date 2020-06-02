package com.paas.web.login;

import com.paas.service.common.PaasResponnse;
import com.paas.service.common.utils.BeanUtils;
import com.paas.service.loginservice.LoginService;
import com.paas.service.loginservice.dto.LoginReqDTO;
import com.paas.service.loginservice.dto.LoginResDTO;
import com.paas.web.login.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired(required = false)
    private LoginService loginService;


    @PostMapping("/auth")
    public PaasResponnse<LoginResDTO> auth(@RequestBody LoginVO loginVO) {
        LoginReqDTO copy = BeanUtils.copy(loginVO, LoginReqDTO.class);

        PaasResponnse<LoginResDTO> auth = loginService.auth(copy);
        return auth;
    }

}
