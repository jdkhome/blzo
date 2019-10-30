"use strict";

(function ($, request) {

    function Login() {
        this.dom = {};
    }

    Login.prototype = {
        init: function () {
            var _this = this;


            $("#login").click(function (event) {

                request.apiLogin($(document), {
                    username: $('input[name="username"]').val(),
                    password: $('input[name="password"]').val()
                }, 'apiLogin');

                event.preventDefault();
            });


            /*键盘按下*/
            $(document).keyup(function (event) {

                /*回车按钮事件 登录 kang.*/
                if (event.keyCode == 13) {
                    request.apiLogin($(document), {
                        username: $('input[name="username"]').val(),
                        password: $('input[name="password"]').val()
                    }, 'apiLogin');

                    event.preventDefault();
                }
            });
            $(document).on('apiLogin', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg,
                        onClose: function () {
                            window.location.href = "/manage/index";
                        }
                    }).show();
                }
            });

        }
    };

    window.controller.Login = Login;

})(jQuery, controller.Request);