package com.jdkhome.blzo.core.common.aop.version;

import com.jdkhome.blzo.common.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by jdk on 17/9/6.
 * 自定义参数解析器，对添加了@CurrentUser注解的方法解析出UserBase对象
 */
@Slf4j
@Component
public class VersionMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是Integer并包含@CurrentUser注解才执行解析
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(Version.class);

    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        String version = (String) nativeWebRequest.getAttribute(SystemConstants.VERSION, RequestAttributes.SCOPE_REQUEST);

        log.info("version:{}", version);

        return version;
    }
}
