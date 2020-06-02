package com.paas.service.loginservice;

import com.paas.service.common.PaasResponnse;
import com.paas.service.loginservice.dto.LoginReqDTO;
import com.paas.service.loginservice.dto.LoginResDTO;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/

public interface LoginService {

    PaasResponnse<LoginResDTO> auth(LoginReqDTO loginDTO);


}
