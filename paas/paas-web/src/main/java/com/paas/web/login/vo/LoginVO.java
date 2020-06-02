package com.paas.web.login.vo;

import lombok.Data;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
@Data
public class LoginVO {

    //用戶名
    private String userName;

    //用戶編碼
    private Integer userCode;


    //昵稱
    private String nickname;


    //密碼
    private String passWord;



}
