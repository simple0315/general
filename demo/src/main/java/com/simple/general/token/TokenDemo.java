package com.simple.general.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * token
 *
 * @author Mr.Wu
 * @date 2020/9/13 02:11
 */
public class TokenDemo {

    public static String getToken() {
        String token="";
        token= JWT.create().withAudience("1")
                .sign(Algorithm.HMAC256("admin"));
        return token;
    }

}
