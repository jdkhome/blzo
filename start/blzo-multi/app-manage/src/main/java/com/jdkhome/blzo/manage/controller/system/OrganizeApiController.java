package com.jdkhome.blzo.manage.controller.system;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.OrganizeStatusEnum;
import com.jdkhome.blzo.ex.authj.service.OrganizeBasicService;
import com.jdkhome.blzo.ex.authj.service.OrganizeService;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linkji
 * @date 2019-03-11 15:30
 * 组织管理接口控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/manage/system/organize")
public class OrganizeApiController {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    OrganizeBasicService organizeBasicService;

    @Autowired
    OrganizeService organizeService;

    /**
     * 添加组织
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Authj
    @Api("添加组织")
    public ApiResponse apiManageSystemOrganizeAdd(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "remark", required = false) String remark
    ) {
        // 只有0号组织管理员才能创建组织
        if (0 != authjManager.getOrganizeId()) {
            log.error("添加组织 -> 没有权限");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        organizeBasicService.addOrganize(name, remark);
        return ApiResponse.success();

    }


    /**
     * 删除组织
     *
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @Authj
    @Api("删除组织")
    public ApiResponse apiManageSystemOrganizeDel(@RequestParam(value = "organizeId", required = false) Integer organizeId
    ) {
        // 只有0号组织管理员才能创建组织
        if (0 != authjManager.getOrganizeId()) {
            log.error("删除组织 -> 没有权限");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        organizeService.delOrganize(organizeId);
        return ApiResponse.success();
    }

    /**
     * 编辑组织
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Authj
    @Api("编辑组织")
    public ApiResponse apiManageSystemOrganizeEdit(@RequestParam(value = "organizeId", required = true) Integer organizeId,
                                                   @RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "status", required = false) Integer status,
                                                   @RequestParam(value = "remark", required = false) String remark
    ) {
        // 只有0号组织管理员才能创建组织
        if (0 != authjManager.getOrganizeId()) {
            log.error("编辑组织 -> 没有权限");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        organizeBasicService.editOrganize(organizeId, name, remark, OrganizeStatusEnum.getByCode(status));
        return ApiResponse.success();
    }

}
