package com.jdkhome.blzo.manage.controller;

import com.jdkhome.blzo.ex.authj.constants.AuthjConstant;
import com.jdkhome.blzo.ex.authj.core.AuthjBean;
import com.jdkhome.blzo.ex.authj.core.AuthjCache;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.core.UserAuthjConfBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jdk on 17/8/17.
 * 全局公共ControllerAdvice
 */
@ControllerAdvice("com.jdkhome.blzo.manage.controller")
public class CommonControllerAdvice {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AuthjCache authjCache;

    @Value("${blzo.project_name}")
    String projectName;

    /**
     * 给model添加初始值
     *
     * @param model 页面model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        model.addAttribute("projectName", projectName);

        AuthjBean authjBean = authjCache.getAuthj(request.getServletPath());

        String pageName = AuthjConstant.NO_PAGE_NAME;
        if (authjBean != null) {
            pageName = authjBean.getName();
        }
        model.addAttribute("pageName", pageName);

        String nickName = AuthjConstant.NO_LOGIN;
        UserAuthjConfBean userAuthjConfBean = authjManager.getUserAuthjConfBean();
        if (userAuthjConfBean != null) {
            model.addAttribute("layerList", userAuthjConfBean.getLayerList());
            model.addAttribute("moreMenu", userAuthjConfBean.getMoreMenu());
            nickName = userAuthjConfBean.getName();
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }
        model.addAttribute("nickName", nickName);

    }
}
