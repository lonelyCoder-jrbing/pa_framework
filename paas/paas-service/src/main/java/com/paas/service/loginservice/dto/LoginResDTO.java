package com.paas.service.loginservice.dto;

import lombok.Data;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc: 返回用户的完整信息，并且有生成的token信息
 **/
@Data
public class LoginResDTO {

    //用户token
    private String authToken;


    //用戶名
    private String userName;

    //用戶編碼
    private Integer userCode;


    //昵稱
    private String nickname;


    //密碼
    private String passWord;

    //..............qita信息
}
