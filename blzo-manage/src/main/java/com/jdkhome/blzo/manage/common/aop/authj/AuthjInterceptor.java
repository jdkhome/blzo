package com.jdkhome.blzo.manage.common.aop.authj;

import com.alibaba.fastjson.JSONObject;
import com.jdkhome.blzo.common.aop.log.controller.ApiRecordAspect;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.pojo.ApiResponse;
import com.jdkhome.blzo.common.tools.IpTools;
import com.jdkhome.blzo.manage.service.manage.LogBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by jdk.
 */
@Component
public class AuthjInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    LogBasicService logBasicService;

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Authj authj = null;
        String uri = null;
        if (handler instanceof HandlerMethod) {

            HandlerMethod h = (HandlerMethod) handler;
            authj = h.getMethodAnnotation(Authj.class);
            // 没有注解
            if (authj == null) {
                return true;
            }

            uri = request.getServletPath();
            if (!authjManager.authentication(uri)) {
                // 没有权限

                if (uri.startsWith("/api")) {
                    // api 返回没有权限
                    ApiResponse resp = ApiResponse.error(CommonResponseError.NO_PERMISSION);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json");
                    PrintWriter pw = response.getWriter();
                    pw.write(JSONObject.toJSONString(resp));
                    pw.flush();
                } else {
                    // page 跳转到错误页面
                    response.setCharacterEncoding("utf-8");
                    response.sendRedirect("/manage/login");
                }
                return false;
            }
        }

        if (authj != null && authj.auth()) {
            logBasicService.addLog(authjManager.getUserId(), authjManager.getUserName(), uri, authj.value(), ApiRecordAspect.getParamerStr(request), IpTools.getIp(request));
        }
        return true;
    }

}
