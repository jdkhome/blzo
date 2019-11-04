package com.jdkhome.blzo.ex.consul.configration;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 为多语言header提供RPC传递能力
 */
@Configuration
@Component
public class RPCRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        ServletRequestAttributes sra = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes());

        if (sra != null) {
            template.header(BasicSystemConstants.i18n, sra.getRequest().getHeader(BasicSystemConstants.i18n));
        }

    }
}
