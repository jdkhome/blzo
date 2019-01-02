package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.core.AuthjService;
import com.jdkhome.blzo.ex.authj.pojo.dto.LayerDTO;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * author link.ji
 * createTime 下午3:17 2018/12/5
 * 个人菜单设置
 */
@Slf4j
@RestController
@RequestMapping("/api/manage/mine/layer")
public class LayerApiController {

    @Autowired
    AuthjService authjService;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 初始化菜单
     *
     * @return
     */
    @Authj(common = true)
    @Api("初始化菜单")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ApiResponse apiManagerMineLayerInit(
    ) {
        authjService.initLayer(authjManager.getUserId());
        return ApiResponse.success();
    }

    /**
     * 自定义菜单
     *
     * @return
     */
    @Authj(common = true)
    @Api("自定义菜单")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ApiResponse apiManagerMineLayerModify(@Valid @RequestBody List<LayerDTO> layers, Errors errors) {

        adminBasicService.editAdmin(authjManager.getUserId(), null, null, null, null, null,null, null, layers);

        return ApiResponse.success();
    }

}
