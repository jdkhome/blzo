"use strict";

(function ($, request) {

    function Dashboard() {
        this.dom = {};
    }

    Dashboard.prototype = {
        init: function () {
            var _this = this;

            $('.group-edit').click(function (event) {
                $(this).parents('tr').find('.group-name-input:first').removeClass('hidden');
                $(this).parents('tr').find('.group-remark-input:first').removeClass('hidden');
                $(this).parents('tr').find('.group-save:first').removeClass('hidden');

                $(this).parents('tr').find('.group-name-span:first').addClass('hidden');
                $(this).parents('tr').find('.group-remark-span:first').addClass('hidden');
                $(this).parents('tr').find('.group-edit:first').addClass('hidden');

            });
            $('.group-save').click(function (event) {
                var obj = {
                    "groupId": $(this).data('id'),
                    "name": $(this).parents('tr').find('.group-name-input:first').val(),
                    "remark": $(this).parents('tr').find('.group-remark-input:first').val()
                };

                request.apiMineGroupEdit($(document), obj, 'groupEdit');
            });

            $(document).on('groupEdit', function (msg, data) {
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


            $('#group-add').click(function (event) {


                var obj = {
                    "name": $('#group-add-name').val(),
                    "remark": $('#group-add-remark').val()
                };

                console.log(obj);

                request.apiMineGroupAdd($(document), obj, 'groupAdd');
            });

            $(document).on('groupAdd', function (msg, data) {
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

            $('#group-delete').click(function (event) {
                var groupId = $(this).data('id');
                request.apiMineGroupDel($(document), {
                    groupId: groupId
                }, 'groupDelete');
            });
            $(document).on('groupDelete', function (msg, data) {
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
    window.controller.Dashboard = Dashboard;

})(jQuery, controller.Request);