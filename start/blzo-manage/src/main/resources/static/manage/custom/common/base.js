/**
 * base.js
 * 基础库
 */

(function ($) {

    // 通用方法
    function Base() {

    }

    Base.doGet = function (url, context, data, event, callbacks) {
        // var session = $.cookie('bs_session') || '';
        console.log(`[GET] url:${url} data:${JSON.stringify(data)}`);
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            timeout: 5000,
            data: data,
            context: this,
            // headers: {
            //     'QUARTZ-SESSION': session
            // },
            success: function (data) {
                this.success(context, event, data);
            },
            beforeSend: function (XMLHttpRequest) {
                this.beforeSend(XMLHttpRequest, callbacks);
            },
            complete: function (XMLHttpRequest, textStatus) {
                this.complete(XMLHttpRequest, textStatus, callbacks);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                this.error(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };

    Base.doPost = function (url, context, data, event, callbacks) {
        // var session = $.cookie('bs_session') || '';
        console.log(`[POST] url:${url} data:${JSON.stringify(data)}`);
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            timeout: 20000,
            data: data,
            context: this,
            // headers: {
            //     'QUARTZ-SESSION': session
            // },
            success: function (data) {
                this.success(context, event, data);
            },
            beforeSend: function (XMLHttpRequest) {
                this.beforeSend(XMLHttpRequest, callbacks);
            },
            complete: function (XMLHttpRequest, textStatus) {
                this.complete(XMLHttpRequest, textStatus, callbacks);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                this.error(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };

    Base.doPostBody = function (url, context, data, event, callbacks) {
        // var session = $.cookie('bs_session') || '';
        console.log(`[POST] url:${url} body:${JSON.stringify(data)}`);
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            timeout: 20000,
            data: data,
            context: this,
            // headers: {
            //     'QUARTZ-SESSION': session
            // },
            success: function (data) {
                this.success(context, event, data);
            },
            beforeSend: function (XMLHttpRequest) {
                this.beforeSend(XMLHttpRequest, callbacks);
            },
            complete: function (XMLHttpRequest, textStatus) {
                this.complete(XMLHttpRequest, textStatus, callbacks);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                this.error(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };

    Base.formPost = function (url, context, data, event, callbacks) {
        // var session = $.cookie('bs_session') || '';
        console.log(`[POST] url:${url} data:${JSON.stringify(data)}`);
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            timeout: 20000,
            data: data,
            context: this,
            contentType: false, // 注意这里应设为false
            processData: false,
            cache: false,
            // headers: {
            //     'QUARTZ-SESSION': session
            // },
            success: function (data) {
                this.success(context, event, data);
            },
            beforeSend: function (XMLHttpRequest) {
                this.beforeSend(XMLHttpRequest, callbacks);
            },
            complete: function (XMLHttpRequest, textStatus) {
                this.complete(XMLHttpRequest, textStatus, callbacks);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                this.error(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };



    Base.doDelete = function (url, context, data, event, callbacks) {
        console.log(`[DELETE] url:${url} data:${JSON.stringify(data)}`);
        $.ajax({
            url: url,
            type: 'DELETE',
            dataType: 'json',
            timeout: 5000,
            data: data,
            context: this,
            success: function (data) {
                this.success(context, event, data);
            },
            beforeSend: function (XMLHttpRequest) {
                this.beforeSend(XMLHttpRequest, callbacks);
            },
            complete: function (XMLHttpRequest, textStatus) {
                this.complete(XMLHttpRequest, textStatus, callbacks);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                this.error(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };

    Base.success = function (context, event, data) {
        if (typeof(context) == 'function') {
            context(data);
        } else {
            context.trigger(event, data);
        }
    };

    Base.error = function (XMLHttpRequest, textStatus, errorThrown) {
        try {
            if (typeof console !== undefined) {
                console.log(textStatus);
            }
        } catch (e) {

        }
    };

    Base.beforeSend = function (XMLHttpRequest, callbacks) {
        if ($.isPlainObject(callbacks) && callbacks.beforeSend && typeof callbacks.beforeSend == 'function') {
            callbacks.beforeSend(XMLHttpRequest);
        }
    };

    Base.complete = function (XMLHttpRequest, textStatus, callbacks) {
        if ($.isPlainObject(callbacks) && callbacks.complete && typeof callbacks.complete == 'function') {
            callbacks.complete();
        }
    };

    // return Base;

    window.controller = {};
    window.controller.Base = Base;

})(jQuery);
