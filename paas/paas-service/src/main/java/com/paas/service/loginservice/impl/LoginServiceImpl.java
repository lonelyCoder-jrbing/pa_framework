package com.paas.service.loginservice.impl;

import com.paas.service.common.PaasResponnse;
import com.paas.service.common.jwt.JwtUtil;
import com.paas.service.common.utils.BeanUtils;
import com.paas.service.loginservice.LoginService;
import com.paas.service.loginservice.dto.LoginReqDTO;
import com.paas.service.loginservice.dto.LoginResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public PaasResponnse<LoginResDTO> auth(LoginReqDTO loginDTO) {
        //1.进行用户名和密码的校验
        //2.从用户表中查询完整信息
        //3.生成token
        //4.将token信息存储在redis中去
        //5.将token设置在返回结果中返回前端

        String jwtToken = JwtUtil.createJwtToken(loginDTO);
        LoginResDTO loginResDTO = BeanUtils.copy(loginDTO, LoginResDTO.class);
        loginResDTO.setAuthToken(jwtToken);

        return PaasResponnse.success(loginResDTO);
    }
}
