"use strict";

(function ($, request) {

    function Dashboard() {
        this.dom = {};
    }

    Dashboard.prototype = {
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

            // === 编辑组 === //

            var edit_group_id;
            var edit_group_name;
            var edit_group_remark;

            /**
             * 编辑组按钮被点击，保存被点击组的信息
             */
            $('.edit-group-btn').click(function (event) {

                edit_group_id = $($(this).parents('tr').first().children('td')[0]).text();
                edit_group_name = $($(this).parents('tr').first().children('td')[1]).text();
                edit_group_remark = $($(this).parents('tr').first().children('td')[2]).text();

                $('#edit-id').val(edit_group_id);
                $('#edit-name').val(edit_group_name);
                $('#edit-remark').val(edit_group_remark);
            });

            $('#group-save').click(function (event) {
                var obj = {
                    "groupId": $('#edit-id').val(),
                    "name": $('#edit-name').val(),
                    "remark": $('#edit-remark').val()
                };
                request.apiMineGroupEdit($(document), obj, 'api-with-sync');
            });



            // === 填加组 === //

            $('#group-add').click(function (event) {

                var obj = {
                    "name": $('#add-name').val(),
                    "remark": $('#add-remark').val()
                };
                request.apiMineGroupAdd($(document), obj, 'api-with-sync');
            });

            // === 删除组 === //
            $('.del-group-btn').click(function (event) {
                if (confirm("确定要删除?") == false) {
                    return;
                }
                request.apiMineGroupDel($(document), {
                    groupId: $($(this).parents('tr').first().children('td')[0]).text()
                }, 'api-with-sync');
            });


        },
        select: function (id) {
            var num = 0;
            $("#" + id + " option").map(function (index, item) {
                if ($(item).html() == $('#' + id + '_chosen .chosen-single span').html()) {
                    num = index;
                }
            });
            var value = $("#" + id + " option").eq(num).val();
            return value;
        }
    };
    window.controller.Dashboard = Dashboard;

})(jQuery, controller.Request);