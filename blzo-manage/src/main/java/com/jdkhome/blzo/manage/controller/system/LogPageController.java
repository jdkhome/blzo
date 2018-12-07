package com.jdkhome.blzo.manage.controller.system;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.pojo.PageRequest;
import com.jdkhome.blzo.manage.common.aop.authj.Authj;
import com.jdkhome.blzo.manage.service.manage.LogBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * author linkji.
 * 后台管理 日志查看器
 */
@Slf4j
@Controller
@RequestMapping("/manage/system/log")
public class LogPageController {

    @Autowired
    LogBasicService logBasicService;

    /**
     * 日志列表页
     */
    @Authj(value = "日志列表页", menu = true)
    @RequestMapping("/list")
    public String logList(Model model, PageRequest pageRequest,
                          @RequestParam(value = "nickName", required = false) String nickName,
                          @RequestParam(value = "authjUri", required = false) String authjUri,
                          @RequestParam(value = "authjName", required = false) String authjName
    ) {

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        PageInfo pageInfo = logBasicService.getLogWithPage(nickName, authjUri, authjName, pageRequest.getPage(), pageRequest.getSize());

        model.addAttribute("pageInfo", pageInfo);

        return "manage/page/system/log/list";
    }

}
