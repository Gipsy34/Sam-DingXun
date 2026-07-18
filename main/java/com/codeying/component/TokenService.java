package com.codeying.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codeying.component.utils.CacheUtil;
import com.codeying.entity.LoginUser;
import com.codeying.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service
public class TokenService{
    Logger log = LoggerFactory.getLogger(getClass());

    private static final String USER_ID_KEY = "userId";
    private static final String USER_NAME_KEY = "userName";

    private Algorithm ALGORITHM = Algorithm.HMAC512("codeying-mh");

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    HttpServletRequest request;

    /**
     * 创建token
     * @param pmsUser
     * @return
     */
    public String createToken(LoginUser pmsUser) {
        String key = CommonUtils.newId();
        String token = JWT.create()
                .withClaim(USER_ID_KEY, pmsUser.getId())
                .withClaim(USER_NAME_KEY, pmsUser.getUsername())
                .withSubject(key)
                .sign(ALGORITHM);
        cacheUtil.set(token, pmsUser, 600);
        return token;
    }

    //刷新
    public void refreshToken(String token) {
        try{
            cacheUtil.expire(token, 60*30);
        }catch (Exception e) {
            log.error("刷新Token失败:无效token！", e);
        }
    }

    public void updateLoginUser(LoginUser pmsUser) {
        String token = request.getHeader("token");
        cacheUtil.set(token, pmsUser);
    }

    //获取
    public LoginUser getLoginInfo() {
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            return null;
        }
        refreshToken(token);
        return cacheUtil.getV(token);
    }

    //删除
    public void removeLoginInfo() {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            try{
                cacheUtil.del(token);
            }catch (Exception e) {
                log.error("删除用户信息失败:无效token！", e);
            }
        }
    }

}
