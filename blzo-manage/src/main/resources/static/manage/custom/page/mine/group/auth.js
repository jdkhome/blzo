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

            $('#save').click(function (event) {
                request.apiMineGroupAuthSave($(document), JSON.stringify({
                    groupId: $("#groupId").val(),
                    uris: $("#auth_multi_select").val()
                }), 'groupAuthAdd');

            });


            $(document).on('groupAuthAdd', function (msg, data) {
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
            $(document).on('groupAuthRemove', function (msg, data) {
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

            // 表格
            // var oTable = $('#os-table').dataTable({
            //     "aLengthMenu": [
            //         [5, 15, 20, -1],
            //         [5, 15, 20, "All"] // change per page values here
            //     ],
            //     // set the initial value
            //     "iDisplayLength": 5,
            //     "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
            //     "sPaginationType": "bootstrap",
            //     "oLanguage": {
            //         "sLengthMenu": "_MENU_ records per page",
            //         "oPaginate": {
            //             "sPrevious": "上一页",
            //             "sNext": "下一页"
            //         }
            //     },
            //     "aoColumnDefs": [{
            //         'bSortable': false,
            //         'aTargets': [0]
            //     }
            //     ]
            // });

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