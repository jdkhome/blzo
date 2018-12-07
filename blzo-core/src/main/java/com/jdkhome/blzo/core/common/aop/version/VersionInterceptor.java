package com.jdkhome.blzo.core.common.aop.version;

import com.alibaba.fastjson.JSONObject;
import com.jdkhome.blzo.common.constants.SystemConstants;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * author link.ji
 * createTime 下午5:09 2018/6/19
 * <p>
 * 版本检查拦截器
 */
@Slf4j
@Component
public class VersionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    VersionTools versionTools;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果未加@Version注解，不需要校验version
        MinVersion versionAnn=method.getAnnotation(MinVersion.class);
        if (versionAnn == null) {
            //logger.info("登录校验->不需要校验token");
            return true;
        }

        //从header中得到token
        String version = request.getHeader(SystemConstants.VERSION);
        log.info("版本校验->请求version:{}", version);

        //验证token, token存在则将userId注入到request属性
        if (versionTools.checkVersion(version,versionAnn.value())) {
            request.setAttribute(SystemConstants.VERSION, version);
            return true;
        } else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(ApiResponse.error(CommonResponseError.VERSION_UPGRADE)));
            pw.flush();
            return false;
        }
    }
}
