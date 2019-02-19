"use strict";

(function ($, request) {

    function GroupAdmin() {
        this.dom = {};
    }

    GroupAdmin.prototype = {
        init: function () {
            var _this = this;

            $('.add').click(function (event) {
                request.apiMineGroupAdminAdd($(document), {
                    groupId: $("#groupId").val(),
                    adminId: $(this).data('id')
                }, 'groupAdminAdd');

            });
            $('.remove').click(function (event) {

                request.apiMineGroupAdminRemove($(document), {
                    groupId: $("#groupId").val(),
                    adminId: $(this).data('id')
                }, 'groupAdminRemove');
            });


            $(document).on('groupAdminAdd', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message : data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message : data.msg,
                        onClose :function(){location.reload();}
                    }).show();
                }
            });
            $(document).on('groupAdminRemove', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message : data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message : data.msg,
                        onClose :function(){location.reload();}
                    }).show();
                }
            });
        }
    };

    window.controller.GroupAdmin = GroupAdmin;

})(jQuery, controller.Request);