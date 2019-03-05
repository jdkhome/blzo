package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.constants.ErrorMsg;
import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


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

        groupBasicService.delGroup(groupId);
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

    @Data
    class GroupAuthSaveParams {

        @NotNull(message = ErrorMsg.NULL)
        Integer groupId;

        List<String> uris;
    }

    @Authj
    @Api("保存组权限")
    @RequestMapping(value = "/auth/save", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAuthSave(@Valid @RequestBody GroupAuthSaveParams params, Errors errors) {

        Group group = groupBasicService.getGroupById(params.groupId);
        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("保存组权限 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        // groupBasicService.addGroupAuth(groupId, uri);


        return ApiResponse.success();

    }

    /**
     * 添加组权限
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("添加组权限")
    @RequestMapping(value = "/auth/add", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAuthAdd(@RequestParam(value = "groupId", required = true) Integer groupId,
                                                  @RequestParam(value = "uri", required = true) String uri
    ) {

        Group group = groupBasicService.getGroupById(groupId);
        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("添加组权限 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupBasicService.addGroupAuth(groupId, uri);


        return ApiResponse.success();

    }


    /**
     * 移除组权限
     *
     * @param groupId
     * @return
     */
    @Authj
    @Api("移除组权限")
    @RequestMapping(value = "/auth/remove", method = RequestMethod.POST)
    public ApiResponse apiManagerMineGroupAuthRemove(@RequestParam(value = "groupId", required = true) Integer groupId,
                                                     @RequestParam(value = "uri", required = true) String uri
    ) {

        Group group = groupBasicService.getGroupById(groupId);
        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("移除组权限 -> 当前用户不是改组的创建者");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        groupBasicService.delGroupAuth(groupId, uri);


        return ApiResponse.success();

    }


}
