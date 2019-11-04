"use strict";

(function ($, request) {

    function Index() {
        this.dom = {};
    }

    Index.prototype = {
        init: function () {
            var _this = this;

            $('#layer-init').click(function (event) {
                var obj = {};

                request.apiManagerMineLayerInit($(document), obj, 'layer-init-callback');
            });

            $(document).on('layer-init-callback', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg,
                        onClose: function () {
                            location.href = "/manage/login";
                        }
                    }).show();
                }
            });

            $('#remove-google-auth').click(function (event) {
                var obj = {};

                request.apiManagerMineSettingGoogleAuthRemove($(document), obj, 'remove-google-auth-callback');
            });

            $(document).on('remove-google-auth-callback', function (msg, data) {
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

    window.controller.Index = Index;

})(jQuery, controller.Request);