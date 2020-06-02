package com.paas.service.loginservice.dto;

import lombok.*;

import java.util.Date;

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
public class TokenInfo {


    /**
     * jwtToken字符串
     */
    private String jwt;
    /***
     * 创建时间
     */
    private Date createAt;
    /**
     * 多少秒过期
     */
    private Long expireAt;


}
