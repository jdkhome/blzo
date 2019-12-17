"use strict";

(function ($, request) {

    function AdminList() {
        this.dom = {};
    }

    AdminList.prototype = {
        init: function () {
            var _this = this;

            // === 响应事件 === //
            // 同步响应=> 成功后自动刷新页面
            $(document).on('api-with-sync', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg,
                        onClose: function () {
                            location.reload();
                        }
                    }).show();
                }
            });

            // 非同步响应=> 如果失败则刷新页面
            $(document).on('api-without-sync', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: "失去同步:" + data.msg,
                        onClose: function () {
                            location.reload();
                        }
                    }).show();
                }
            });


            var edit_admin_id;
            var edit_admin_username;
            var edit_admin_nickName;
            var edit_admin_phone;
            var edit_admin_email;
            var edit_admin_status;
            var edit_admin_remark;

            /**
             * 编辑组织按钮被点击，保存被点击组织的信息
             */
            $('.edit-admin-btn').click(function (event) {

                edit_admin_id = $($(this).parents('tr').first().children('td')[0]).text();
                edit_admin_nickName = $($(this).parents('tr').first().children('td')[1]).text();
                edit_admin_username = $($(this).parents('tr').first().children('td')[2]).text();
                edit_admin_phone = $($(this).parents('tr').first().children('td')[3]).text();
                edit_admin_email = $($(this).parents('tr').first().children('td')[4]).text();
                edit_admin_status = $($(this).parents('tr').first().children('td')[5]).attr('status');
                edit_admin_remark = $($(this).parents('tr').first().children('td')[6]).text();

                $('#edit-id').val(edit_admin_id);
                $('#edit-nickName').val(edit_admin_nickName);
                $('#edit-username').val(edit_admin_username);
                $('#edit-phone').val(edit_admin_phone);
                $('#edit-email').val(edit_admin_email);
                $('#edit-status').val(edit_admin_status);
                $('#edit-remark').val(edit_admin_remark);
            });


            $('#admin-save').click(function (event) {
                var obj = {
                    "adminId": $('#edit-id').val(),
                    "username": $('#edit-username').val(),
                    "nickName": $('#edit-nickName').val(),
                    "phone": $('#edit-phone').val(),
                    "email": $('#edit-email').val(),
                    "status": $('#edit-status').val(),
                    "remark": $('#edit-remark').val()
                };

                request.apiSystemAdminEdit($(document), obj, 'api-with-sync');
            });


            $('#admin-add').click(function (event) {
                var obj = {
                    "username": $('#add-username').val(),
                    "password": $('#add-password').val(),
                    "nickName": $('#add-nickName').val(),
                    "phone": $('#add-phone').val(),
                    "email": $('#add-email').val(),
                    "remark": $('#add-remark').val(),
                    "organizeId": $('#add-organizeId').val()
                };
                request.apiSystemAdminAdd($(document), obj, 'api-with-sync');
            });


            /**
             * 删除
             */
            $('.del-admin-btn').click(function (event) {

                if (confirm("确定要删除?") == false) {
                    return;
                }

                request.apiSystemAdminDel($(document), {
                    adminId: $($(this).parents('tr').first().children('td')[0]).text()
                }, 'api-with-sync');
            });
        }
    };

    window.controller.AdminList = AdminList;

})(jQuery, controller.Request);