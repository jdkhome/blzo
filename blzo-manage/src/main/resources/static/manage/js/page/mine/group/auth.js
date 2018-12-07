"use strict";

(function ($, request) {

    function GroupAuth() {
        this.dom = {};
    }

    GroupAuth.prototype = {
        init: function () {
            var _this = this;

            $('.add').click(function (event) {
                request.apiMineGroupAuthAdd($(document), {
                    groupId: $("#groupId").val(),
                    uri: $(this).data('uri')
                }, 'groupAuthAdd');

            });
            $('.remove').click(function (event) {

                request.apiMineGroupAuthRemove($(document), {
                    groupId: $("#groupId").val(),
                    uri: $(this).data('uri')
                }, 'groupAuthRemove');
            });


            $(document).on('groupAuthAdd', function (msg, data) {
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
            $(document).on('groupAuthRemove', function (msg, data) {
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

    window.controller.GroupAuth = GroupAuth;

})(jQuery, controller.Request);