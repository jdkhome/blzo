"use strict";

(function ($, request) {

    function GroupAuth() {
        this.dom = {};
    }

    GroupAuth.prototype = {
        init: function () {
            var _this = this;

            // === 响应事件 === //
            // 同步响应=> 成功后自动刷新页面
            $(document).on('api-with-sync', function (msg, data) {
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

            // 非同步响应=> 如果失败则刷新页面
            $(document).on('api-without-sync', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: "失去同步:" + data.msg,
                        onClose: function () {
                            location.reload();
                        }
                    }).show();
                }
            });

            $('#save').click(function (event) {
                request.apiManagerMineGroupAuthSet($(document), JSON.stringify({
                    groupId: $("#groupId").val(),
                    uris: $("#auth_multi_select").val()
                }), 'api-with-sync');

            });


            $('#auth_multi_select').multiSelect({
                selectableHeader: "<input type='text' class='form-control search-input' autocomplete='off' placeholder='在所有权限中查找...'>",
                selectionHeader: "<input type='text' class='form-control search-input' autocomplete='off' placeholder='在已有权限中查找...'>",
                afterInit: function (ms) {
                    var that = this,
                        $selectableSearch = that.$selectableUl.prev(),
                        $selectionSearch = that.$selectionUl.prev(),
                        selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)',
                        selectionSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selection.ms-selected';

                    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                        .on('keydown', function (e) {
                            if (e.which === 40) {
                                that.$selectableUl.focus();
                                return false;
                            }
                        });

                    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                        .on('keydown', function (e) {
                            if (e.which == 40) {
                                that.$selectionUl.focus();
                                return false;
                            }
                        });
                },
                afterSelect: function () {
                    this.qs1.cache();
                    this.qs2.cache();
                },
                afterDeselect: function () {
                    this.qs1.cache();
                    this.qs2.cache();
                }
            });

        }
    };

    window.controller.GroupAuth = GroupAuth;

})(jQuery, controller.Request);