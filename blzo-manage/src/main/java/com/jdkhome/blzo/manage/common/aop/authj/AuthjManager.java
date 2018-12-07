package com.jdkhome.blzo.manage.common.aop.authj;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jdk on 18/1/6.
 */
@Slf4j
@Component
public class AuthjManager {


    @Autowired
    AuthjCache authjCache;

    @Autowired
    AuthjService authjService;

    /**
     * SESSION 中的key
     */
    public static final String AUTHJ_KEY = "authj_key";


    /**
     * 获取request
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }


    /**
     * 获取登录的用户id，没登录则返回0
     *
     * @return
     */
    public Integer getUserId() {
        UserAuthjConfBean userAuthjConfBean = this.getUserAuthjConfBean();
        if (userAuthjConfBean == null) {
            return 0;
        } else {
            return userAuthjConfBean.getId();
        }
    }

    /**
     * 获取登录的用户name，没登录则返回null
     *
     * @return
     */
    public String getUserName() {
        UserAuthjConfBean userAuthjConfBean = this.getUserAuthjConfBean();
        if (userAuthjConfBean == null) {
            return null;
        } else {
            return userAuthjConfBean.getName();
        }
    }

    /**
     * 获取登录用户的权限实体
     *
     * @return
     */
    public UserAuthjConfBean getUserAuthjConfBean() {
        return (UserAuthjConfBean) this.getRequest().getSession().getAttribute(AUTHJ_KEY);
    }


    /**
     * 鉴权
     *
     * @return
     */
    public boolean authentication(String uri) {

        // 权限实体
        AuthjBean authjBean = authjCache.getAuthj(uri);
        if (authjBean == null) {
            log.error("[Authj - 鉴权] uri:{} -> authjCache未命中,请检查");
            return true;
        }
        log.debug("[Authj - 鉴权] uri:{} -> authjBean:{}", uri, JSONObject.toJSONString(authjBean));


        if (!authjBean.getAuth()) {
            log.info("[Authj - 鉴权] uri:{} -> 无需鉴权", uri);
            return true;
        }

        // 用户权限集合
        UserAuthjConfBean userAuthjConfBean = this.getUserAuthjConfBean();

        if (userAuthjConfBean == null) {
            log.info("[Authj - 鉴权] 用户未登录");
            return false;
        }

        // 不为公共权限的接口(页面) 才需要鉴权
        if (!authjBean.common && !userAuthjConfBean.getAuthUriSet().contains(uri)) {
            log.info("[Authj - 鉴权] 用户:{}({})没有权限:{}", userAuthjConfBean.getName(), userAuthjConfBean.getId(), uri);
            return false;
        }

        return true;
    }

    /**
     * 授权
     *
     * @param userId
     * @return
     */
    public UserAuthjConfBean grant(Integer userId) {
        //入参检验。。
        if (userId == null) {
            return null;
        }

        UserAuthjConfBean userAuthjConfBean = authjService.getUserAuthjConf(userId);
        this.getRequest().getSession().setAttribute(AUTHJ_KEY, userAuthjConfBean);
        return userAuthjConfBean;
    }

    /**
     * 解除授权
     */
    public void delGrant() {
        this.getRequest().getSession().removeAttribute(AUTHJ_KEY);
    }

}
