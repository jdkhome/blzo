package com.jdkhome.blzo.common.aop.log.controller;

import com.jdkhome.blzo.common.constants.SystemConstants;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.common.tools.IpTools;
import com.jdkhome.blzo.common.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * Created by jdk on 17/4/18.
 */
@Slf4j
@Aspect
@Component
public class ApiRecordAspect {


    //申明一个切点里面是execution表达式
    @Pointcut("execution(* com.jdkhome.blzo.*.controller..*.*(..))")
    private void controllerAspect() {
    }

    public static String getParamerStr(HttpServletRequest request) {
        StringBuilder paramerSb = new StringBuilder();
        Enumeration<String> paramerNames = request.getParameterNames();
        if (paramerNames.hasMoreElements()) {
            while (paramerNames.hasMoreElements()) {
                String name = paramerNames.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    paramerSb.append(name).append("=").append(values[0]);
                } else {
                    paramerSb.append(name).append("[]={");
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0) {
                            paramerSb.append(",");
                        }
                        paramerSb.append(values[i]);
                    }
                    paramerSb.append("}");
                }
                paramerSb.append("  ");
            }
        }

        return paramerSb.toString();
    }

    public static String getBodyStr(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    //请求method前打印内容
    @Before(value = "controllerAspect()&&@annotation(com.jdkhome.blzo.common.aop.log.controller.Api)")
    public void methodBefore(JoinPoint joinPoint) {

        // 获取Api 注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Api api = method.getAnnotation(Api.class);

        // 获取Request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 请求保存接口名称
        request.setAttribute(SystemConstants.controllerName, api.value());

        String ip = IpTools.getIp(request);

        if (api.log()) {

            StringBuilder headerSb = new StringBuilder();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames.hasMoreElements()) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);
                    headerSb.append(name).append("=").append(value);
                    headerSb.append("  ");
                }
            }

            log.info("Api(In) => {} [{}] {} param:{} body:{} ip:{} header:{} className:{} modelName:{}",
                    api.value(), request.getMethod(), request.getRequestURL(),
                    ApiRecordAspect.getParamerStr(request), ApiRecordAspect.getBodyStr(request), ip, headerSb,
                    joinPoint.getTarget().getClass().getSimpleName(),
                    ((MethodSignature) joinPoint.getSignature()).getMethod().getName()
            );
        }

        // 检查入参错误 抛参数不正确的异常
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof BindingResult) {
                if (((BindingResult) obj).hasErrors()) {
                    throw new ServiceException(CommonResponseError.PARAMETER_ERROR, ((BindingResult) obj).getAllErrors().get(0).getDefaultMessage());
                }
            }

            if (obj instanceof Errors) {
                if (((Errors) obj).hasErrors()) {
                    throw new ServiceException(CommonResponseError.PARAMETER_ERROR, ((Errors) obj).getAllErrors().get(0).getDefaultMessage());
                }
            }
        }

    }


    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()&&@annotation(com.jdkhome.blzo.common.aop.log.controller.Api)")
    public void methodAfterReturing(JoinPoint joinPoint, Object o) {
        // 获取Api 注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Api api = method.getAnnotation(Api.class);

        if (api.log()) {
            log.info("Api(Out) =>{} return:{}", api.value(), PerfectGson.getGson().toJson(o));
        }

    }
}
