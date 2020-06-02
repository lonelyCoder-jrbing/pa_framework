package com.paas.service.loginservice.dto;

import lombok.*;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginReqDTO {
    //用戶名
    private String userName;

    //用戶編碼
    private Integer userCode;


    //昵稱
    private String nickname;


    //密碼
    private String passWord;


}
