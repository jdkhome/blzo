"use strict";

(function ($, request) {

    function Demo() {
        this.dom = {};
    }

    Demo.prototype = {
        init: function () {
            var _this = this;

            $('#demo-api').click(function (event) {
                request.apiDemoApi($(document), {}, 'demo-api-callback');
            });

            $(document).on('demo-api-callback', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                }
            });

        }
    };

    window.controller.Demo = Demo;

})(jQuery, controller.Request);