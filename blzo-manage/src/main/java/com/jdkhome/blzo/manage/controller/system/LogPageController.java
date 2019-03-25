package com.jdkhome.blzo.manage.controller.system;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.service.LogBasicService;
import com.jdkhome.blzo.ex.basic.pojo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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

    @Autowired
    AuthjManager authjManager;

    /**
     * 日志列表页
     */
    @Authj(value = "日志列表页", menu = true)
    @RequestMapping("/list")
    public String logList(Model model, PageRequest pageRequest,
                          @RequestParam(value = "organizeId", required = false) Integer organizeId,
                          @RequestParam(value = "nickName", required = false) String nickName,
                          @RequestParam(value = "authjUri", required = false) String authjUri,
                          @RequestParam(value = "authjName", required = false) String authjName,
                          @RequestParam(value = "timeBegan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date timeBegan,
                          @RequestParam(value = "timeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date timeEnd
    ) {

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        // 非0号组织则只能看自己的数据
        if (0 != authjManager.getOrganizeId()) {
            organizeId = authjManager.getOrganizeId();
        }

        PageInfo pageInfo = logBasicService.getLogWithPage(organizeId, nickName, authjUri, authjName,
                timeBegan, timeEnd, pageRequest.getPage(), pageRequest.getSize());

        model.addAttribute("pageInfo", pageInfo);

        return "manage/page/system/log/list";
    }

}
