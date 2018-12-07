"use strict";

(function ($, request) {

    function AdminAdd() {
        this.dom = {};
    }

    AdminAdd.prototype = {
        init: function () {
            var _this = this;

            $('#save').click(function (event) {


                var obj = {
                    "username": $('#username').val(),
                    "password": $('#password').val(),
                    "nickName": $('#nickName').val(),
                    "phone": $('#phone').val(),
                    "remark": $('#remark').val()
                };

                console.log(obj);

                request.apiSystemAdminAdd($(document), obj, 'adminAdd');
            });

            $(document).on('adminAdd', function (msg, data) {
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

    window.controller.AdminAdd = AdminAdd;

})(jQuery, controller.Request);