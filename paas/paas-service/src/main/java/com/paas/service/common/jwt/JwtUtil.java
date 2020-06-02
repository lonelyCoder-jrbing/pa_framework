package com.paas.service.common.jwt;

import com.paas.service.loginservice.dto.LoginReqDTO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


@Slf4j
public class JwtUtil {
    /**
     * JWT加密密钥
     */
    private static final String SECURITY_KEY = "asfasdfasdcl-oisdfzx=zxc";

    //过期时间
    private static final Long TTL = 7 * 24 * 60 * 6 * 1000L;

    /**
     * 生成JwtToken
     *
     * @return
     */
    public static String createJwtToken(LoginReqDTO user) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getUserCode()+ "")       //设置tokenID
                .setSubject(user.getUserName())   //设置主题   jwt面向的用户
                .setIssuedAt(new Date())     //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TTL))//过期时间
                .claim("id", user.getUserCode()) //自定义属性
                .claim("username", user.getUserName())
                .claim("nickname", user.getNickname())
                .claim("password", user.getPassWord())
                //以上都是 载荷
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY);//签名，进行密钥加密
        // 这个是签证

        return jwtBuilder.compact();
    }


    /**
     * @param token
     * @return
     */
    public static LoginReqDTO parseJwtToken(String token) {
        LoginReqDTO user = null;

        try {
            Claims body = (Claims) Jwts.parser().setSigningKey(SECURITY_KEY).parse(token).getBody();
            Integer id = (Integer) body.get("id");
            String username = (String) body.get("username");
            String nickname = (String) body.get("nickname");
            String password = (String) body.get("password");
            user = LoginReqDTO.builder()
                    .userCode(id)
                    .userName(username)
                    .nickname(nickname)
                    .passWord(password)
                    .build();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        LoginReqDTO user = LoginReqDTO
                .builder()
                .nickname("suidan")
                .userCode(719203729)
                .passWord("jrbing001")
                .userName("jurongbing")
                .build();
        String jwtToken = createJwtToken(user);
        log.info("jwtToken:   {}",jwtToken);
        LoginReqDTO loginDTO = parseJwtToken(jwtToken);
        log.info("parseJwtToken:{}   ",loginDTO);


    }

}

