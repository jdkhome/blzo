"use strict";

(function ($, request) {

    function GoogleAuth() {
        this.dom = {};
    }

    GoogleAuth.prototype = {
        init: function () {
            var _this = this;

            $("#wizard").steps({
                headerTag: "h2",
                bodyTag: "section",
                transitionEffect: "slideLeft",
                onFinished: function () {

                    var obj = {
                        "secret": $('#secret').val(),
                        "code": $('#code').val()
                    };

                    request.apiManagerMineSettingGoogleAuth($(document), obj, 'callback');
                }
            });

            $(document).on('callback', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg,
                        onClose: function () {
                            location.href = '/';
                        }
                    }).show();
                }
            });

        }
    };

    window.controller.GoogleAuth = GoogleAuth;

})(jQuery, controller.Request);