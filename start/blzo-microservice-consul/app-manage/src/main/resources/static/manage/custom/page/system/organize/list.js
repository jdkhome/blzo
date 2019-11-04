"use strict";

(function ($, request) {

    function OrganizeList() {
        this.dom = {};
    }

    OrganizeList.prototype = {
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


            /**
             * 添加组织
             */
            $('#organize-add').click(function (event) {
                var obj = {
                    "name": $('#add-name').val(),
                    "remark": $('#add-remark').val()
                };
                request.apiManageSystemOrganizeAdd($(document), obj, 'api-with-sync');
            });


            var edit_organize_id;
            var edit_organize_name;
            var edit_organize_status;
            var edit_organize_remark;

            /**
             * 编辑组织按钮被点击，保存被点击组织的信息
             */
            $('.edit-organize-btn').click(function (event) {

                edit_organize_id = $($(this).parents('tr').first().children('td')[0]).text();
                edit_organize_name = $($(this).parents('tr').first().children('td')[1]).text();
                edit_organize_status = $($(this).parents('tr').first().children('td')[2]).attr('status');
                edit_organize_remark = $($(this).parents('tr').first().children('td')[3]).text();

                $('#edit-id').val(edit_organize_id);
                $('#edit-name').val(edit_organize_name);
                $('#edit-remark').val(edit_organize_remark);
                $('#edit-status').val(edit_organize_status);
            });

            /**
             * 编辑组织
             */
            $('#organize-save').click(function (event) {
                var obj = {
                    "organizeId": $('#edit-id').val(),
                    "name": $('#edit-name').val(),
                    "status": $('#edit-status').val(),
                    "remark": $('#edit-remark').val()
                };
                request.apiManageSystemOrganizeEdit($(document), obj, 'api-with-sync');
            });

            /**
             * 删除组织
             */
            $('.del-organize-btn').click(function (event) {
                if (confirm("确定要删除?") == false) {
                    return;
                }
                var obj = {
                    "organizeId": $($(this).parents('tr').first().children('td')[0]).text()
                };
                request.apiManageSystemOrganizeDel($(document), obj, 'api-with-sync');
            });




        }
    };

    window.controller.OrganizeList = OrganizeList;

})(jQuery, controller.Request);