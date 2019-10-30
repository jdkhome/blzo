"use strict";

(function ($, request) {

    function AdminEdit() {
        this.dom = {};
    }

    AdminEdit.prototype = {
        init: function () {
            var _this = this;

            $('#save').click(function (event) {
                var obj = {
                    "adminId": $('#adminId').val(),
                    "password": $('#password').val(),
                };

                request.apiSystemAdminEdit($(document), obj, 'adminEdit');
            });

            $(document).on('adminEdit', function (msg, data) {
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

    window.controller.AdminEdit = AdminEdit;

})(jQuery, controller.Request);