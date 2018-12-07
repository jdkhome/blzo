package com.jdkhome.blzo.manage.common.aop.authj;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by jdk on 17/12/22.
 * Authj初始化
 */
@Component
public class AuthjInit implements InitializingBean {

    @Autowired
    AuthjCache authjCache;

    private void getAllSupervisionUrl() {

        // 获取所有监管中的方法注解

        ClassUtil.getClasses("com.jdkhome.blzo.manage.controller").stream()
                .forEach(aClass -> Arrays.stream(aClass.getMethods())
                        .filter(method -> method.getAnnotation(Authj.class) != null)
                        .forEach(method -> authjCache.addAuthj(method)));


        return;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        getAllSupervisionUrl();
    }
}
