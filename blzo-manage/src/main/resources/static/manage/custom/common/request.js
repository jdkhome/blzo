/*
 request.js
 网络请求列表
 */

"use strict";

(function (Base) {

    function Request() {
    }

    // DEMO API 1
    Request.apiDemoApi1 = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/demo/api_1', context, data, event, callbacks);
    };
    

    // 管理员登录API
    Request.apiLogin = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/login', context, data, event, callbacks);
    };

    //======管理员=======
    // 添加管理员
    Request.apiSystemAdminAdd = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/admin/add', context, data, event, callbacks);
    };

    // 编辑管理员
    Request.apiSystemAdminEdit = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/admin/edit', context, data, event, callbacks);
    };

    // 删除管理员
    Request.apiSystemAdminDel = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/admin/del', context, data, event, callbacks);
    };

    //==== 组织管理 ====
    // 添加组织
    Request.apiManageSystemOrganizeAdd = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/organize/add', context, data, event, callbacks);
    };

    // 删除组织
    Request.apiManageSystemOrganizeDel = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/organize/del', context, data, event, callbacks);
    };

    // 编辑组织
    Request.apiManageSystemOrganizeEdit = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/system/organize/edit', context, data, event, callbacks);
    };

    //======组=======
    // 添加组
    Request.apiMineGroupAdd = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/group/add', context, data, event, callbacks);
    };

    // 编辑组
    Request.apiMineGroupEdit = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/group/edit', context, data, event, callbacks);
    };

    // 删除组
    Request.apiMineGroupDel = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/group/del', context, data, event, callbacks);
    };

    // 添加成员组关联
    Request.apiMineGroupAdminAdd = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/group/admin/add', context, data, event, callbacks);
    };

    // 移除成员组关联
    Request.apiMineGroupAdminRemove = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/group/admin/remove', context, data, event, callbacks);
    };

    // 保存权限组关联
    Request.apiManagerMineGroupAuthSet = function (context, data, event, callbacks) {
        Base.doPostBody('/api/manage/mine/group/auth/set', context, data, event, callbacks);
    };

    //====管理员个人设置相关====

    // 初始化个人Layer
    Request.apiManagerMineLayerInit = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/layer/init', context, data, event, callbacks);
    };

    // 修改菜单设置
    Request.apiManagerMineLayerModify = function (context, data, event, callbacks) {
        Base.doPostBody('/api/manage/mine/layer/modify', context, data, event, callbacks);
    };

    // 修改个人信息
    Request.apiManagerMineSettingBasic = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/setting/basic', context, data, event, callbacks);
    };

    // 修改密码
    Request.apiManagerMineSettingPassword = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/setting/password', context, data, event, callbacks);
    };

    // 设置google身份验证器
    Request.apiManagerMineSettingGoogleAuth = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/setting/google_auth', context, data, event, callbacks);
    };

    // 移除google身份验证器
    Request.apiManagerMineSettingGoogleAuthRemove = function (context, data, event, callbacks) {
        Base.doPost('/api/manage/mine/setting/google_auth/remove', context, data, event, callbacks);
    };

    window.controller.Request = Request;

})(controller.Base);

