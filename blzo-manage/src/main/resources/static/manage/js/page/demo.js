"use strict";

(function ($, request) {

    function Demo() {
        this.dom = {};
    }

    Demo.prototype = {
        init: function () {
            var _this = this;

            $('#demo-api-1').click(function (event) {
                request.apiDemoApi1($(document), {}, 'demo-api-1-callback');
            });

            $(document).on('demo-api-1-callback', function (msg, data) {
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