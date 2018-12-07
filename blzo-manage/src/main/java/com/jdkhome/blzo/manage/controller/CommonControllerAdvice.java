package com.jdkhome.blzo.manage.controller;

import com.jdkhome.blzo.manage.common.aop.authj.AuthjBean;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjCache;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjManager;
import com.jdkhome.blzo.manage.common.aop.authj.UserAuthjConfBean;
import com.jdkhome.blzo.manage.common.constant.ManageConstant;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 给model添加初始值
     *
     * @param model 页面model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        model.addAttribute("projectName", ManageConstant.MANAGE_NAME);

        AuthjBean authjBean = authjCache.getAuthj(request.getServletPath());

        String pageName = ManageConstant.NO_PAGE_NAME;
        if (authjBean != null) {
            pageName = authjBean.getName();
        }
        model.addAttribute("pageName", pageName);

        String nickName = ManageConstant.NO_LOGIN;
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
