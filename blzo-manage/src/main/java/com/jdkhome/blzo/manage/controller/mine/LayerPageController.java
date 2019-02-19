package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.core.*;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAdmin;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.manage.pojo.vo.mine.layer.MenuGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * author link.ji
 * createTime 下午3:17 2018/12/5
 * 个人菜单设置
 */
@Slf4j
@Controller
@RequestMapping("/manage/mine/layer")
public class LayerPageController {

    @Autowired
    AuthjService authjService;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AuthjCache authjCache;

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    GroupBasicService groupBasicService;

    /**
     * 个人菜单配置页
     *
     * @return
     */
    @Authj(value = "我的菜单", menu = true, common = true)
    @RequestMapping("/modify")
    public String modify(Model model) {

        // 所有菜单
        Set<String> allUri = authjService.getAdminAuth(authjManager.getUserId(),authjManager.getUserId());
        List<AuthjBean> allMenu = new ArrayList<>();
        allUri.stream().filter(uri -> authjCache.hasAuthj(uri) && authjCache.getAuthj(uri).getMenu()).forEach(uri -> allMenu.add(authjCache.getAuthj(uri)));

        // 继承的菜单
        List<GroupAdmin> groupAdminList = groupBasicService.getGroupAdminByAdminId(authjManager.getUserId());
        List<MenuGroupVO> vos = new ArrayList<>(groupAdminList.size());
        MenuGroupVO commonVo = new MenuGroupVO();
        commonVo.setName("公共");
        List<AuthjBean> commonList = new ArrayList<>();
        authjCache.getAuthjBeans().stream().filter(authjBean -> authjBean.getCommon() && authjBean.getMenu())
                .forEach(commonList::add);
        commonVo.setMenus(commonList);

        vos.add(commonVo);

        groupAdminList.forEach(groupAdmin -> {
            MenuGroupVO vo = new MenuGroupVO();
            Group group = groupBasicService.getGroupById(groupAdmin.getGroupId());

            vo.setName(group.getName());
            var uris = authjService.getValidAuth(group.getId(),group.getCreateAdminId());
            List<AuthjBean> list = new ArrayList<>();
            uris.stream().filter(uri -> authjCache.hasAuthj(uri) && authjCache.getAuthj(uri).getMenu()).forEach(uri -> list.add(authjCache.getAuthj(uri)));

            if (!CollectionUtils.isEmpty(list)) {
                vo.setMenus(list);
                vos.add(vo);
            }
        });

        model.addAttribute("allMenu", allMenu);
        model.addAttribute("menuGroups", vos);


        return "manage/page/mine/layer/modify";
    }

}
