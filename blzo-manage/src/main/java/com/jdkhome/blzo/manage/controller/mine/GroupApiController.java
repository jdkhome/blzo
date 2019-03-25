package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.pojo.dto.AuthDTO;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.ex.authj.service.GroupService;
import com.jdkhome.blzo.ex.authj.validator.OrganizeValidator;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by jdk on 17/12/5.
 */
@Slf4j
@RestController
@RequestMapping("/api/manage/mine/group")
public class GroupApiController {


    @Autowired
    GroupBasicService groupBasicService;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    OrganizeValidator organizeValidator;

    @Autowired
    GroupService groupService;

    /**
     * 添加组
     *
     * @return
     */
    @Authj
    @Api("添加组")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAdd(@RequestParam(value = "name", required = true) String name,
                                              @RequestParam(value = "remark", required = false) String remark
    ) {
        groupBasicService.addGroup(name, remark);
        return ApiResponse.success();

    }

    /**
     * 删除组
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("删除组")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupDel(@RequestParam(value = "groupId", required = true) Integer groupId
    ) {

        Group group = groupBasicService.getGroupById(groupId);

        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("删除组 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupService.delGroup(groupId);
        return ApiResponse.success();

    }

    /**
     * 编辑组
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("编辑组")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupEdit(@RequestParam(value = "groupId", required = true) Integer groupId,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "remark", required = false) String remark
    ) {

        Group group = groupBasicService.getGroupById(groupId);

        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("编辑组 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupBasicService.editGroups(groupId, name, remark);


        return ApiResponse.success();

    }

    /**
     * 添加组成员
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("添加组成员")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAdminAdd(@RequestParam(value = "groupId", required = true) Integer groupId,
                                                   @RequestParam(value = "adminId", required = true) Integer adminId
    ) {

        Group group = groupBasicService.getGroupById(groupId);

        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("添加组成员 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        /**
         * 0号组织 可以跨组织赋权给其他组织管理员 但是不允许权限在非0号组织中传递
         */
        if (!organizeValidator.validAdmin(adminId)) {
            log.error("编辑管理员 -> 组织鉴权不通过 禁止为其他组织的管理员赋权");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupBasicService.addGroupAdmin(groupId, adminId);


        return ApiResponse.success();

    }

    /**
     * 移除组成员
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("移除组成员")
    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAdminRemove(@RequestParam(value = "groupId", required = true) Integer groupId,
                                                      @RequestParam(value = "adminId", required = true) Integer adminId
    ) {
        Group group = groupBasicService.getGroupById(groupId);
        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("移除组成员 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupBasicService.delGroupAdmin(groupId, adminId);


        return ApiResponse.success();

    }

    /**
     * 设置组权限
     *
     * @param auths
     * @return
     */
    @Authj
    @Api("设置组权限")
    @RequestMapping(value = "/auth/set", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAuthSet(@Valid @RequestBody AuthDTO auths, Errors errors) {

        Group group = groupBasicService.getGroupById(auths.getGroupId());
        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("设置组权限 -> 当前用户不是该组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupService.setAuth(auths.getGroupId(), auths.getUris());

        return ApiResponse.success();
    }


}
