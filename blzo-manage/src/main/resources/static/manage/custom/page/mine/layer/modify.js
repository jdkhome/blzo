"use strict";

(function ($, request) {

    function Modify() {
        this.dom = {};
    }

    Modify.prototype = {
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

            $('#add-layer').click(function (event) {
                $('#my-layer-table').append(`
                                        <tr ondrop="drop(event,this)" ondragover="allowDrop(event)"
                                            draggable="true" ondragstart="drag(event, this)">
                                            <td>
                                                <div class="input-group">
                                                    <span class="input-group-addon">
                                                        <span class="onoffswitch">
                                                            <input type="radio" name="select-layer">
                                                        </span>
                                                    </span>
                                                    <input class="form-control layer-name" value="新菜单组" type="text">
                                                </div>
                                            </td>
                                            <td class="layer-menus">
                                                
                                            </td>
                                            <td>
                                                <button class="btn btn-danger remove-layer" onclick="removeLayer(this)">移除</button>
                                            </td>
                                        </tr>
                `);
            });

            $('#layer-save').click(function (event) {

                var layers = new Array();

                $('#my-layer-table').find('tr').each(function (i) {
                    var uris = new Array();
                    $(this).find('.layer-menus:first').find('button').each(function (j) {
                        uris.push($(this).attr('value'));
                    });
                    layers.push({'name': $(this).find('.layer-name:first').val(), 'uris': uris});
                })

                console.log(layers);

                request.apiManagerMineLayerModify($(document), JSON.stringify(layers), 'layer-save-callback');

            });

            $(document).on('layer-save-callback', function (msg, data) {
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
    window.controller.Modify = Modify;

})(jQuery, controller.Request);