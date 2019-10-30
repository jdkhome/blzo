(function ($, request) {

    function Search() {

        this.dom = {};
    }


    Search.prototype = {

        init: function () {
            var _this = this;
            // 搜索
            $("#list-search").click(function (event) {
                var parameter = _this.parameter();
                var url = "";
                for (var i in parameter) {
                    if (parameter[i]) {
                        url = _this.href(i, parameter[i], url)
                    }
                }
                if (location.href.indexOf("?") != -1) {
                    var href = location.href.split("?");
                    location.href = href[0] + url;
                } else {
                    var href = location.href;
                    location.href = href + url;
                }
            });

            $("#list-download").click(function (event) {
                var parameter = _this.parameter();
                var url = "";
                for (var i in parameter) {
                    if (parameter[i]) {
                        url = _this.href(i, parameter[i], url)
                    }
                }
                alert('download')
                if (location.href.indexOf("?") != -1) {
                    var href = location.href.split("?");
                    location.href = href[0] + "/download/" + url;
                } else {
                    var href = location.href;
                    location.href = href + "/download/" + url;
                }
            });

            // 清空
            $(".empty").click(function () {
                if ($("input[name='search']")) {
                    $("input[name='search']").map(function (index, item) {
                        $(item).val("");
                    });
                }
                if ($("select[name='search']")) {

                    $("select[name='search']").map(function (index, item) {
                        var str = $(item).attr("id");
                        $('#' + str + '_chosen .chosen-single span').html("全部");
                    });

                    /*单击清空 设置下拉列表选中第一个  kang。。*/
                    var SelectArr = $("select[name='search']");
                    for (var i = 0; i < SelectArr.length; i++) {
                        SelectArr[i].options[0].selected = true;
                    }

                    /*单击清空  checkbox 设置未选中*/
                    $("#topCheck").prop("checked", false);
                    $(".checkboxList").prop("checked", false);
                }
            });
            //回显
            var show = {};
            if (location.href.indexOf("?") != -1) {
                var href = location.href.split("?");
                var pathname = href[1].split("&");
                var name = [];
                var value = [];
                for (k = 0; k < pathname.length; k++) {
                    var path = pathname[k].split("=");
                    name.push(path[0]);
                    value.push(path[1]);
                }
                for (var i = 0; i < name.length; i++) {
                    for (var j = 0; j < value.length; j++) {
                        if (j == i) {
                            show[name[i]] = value[j];
                        }
                    }
                }
                if ($("input[name='search']")) {
                    $("input[name='search']").map(function (index, item) {
                        var str = $(item).attr("id");
                        for (var p in show) {
                            if (p == str) {
                                if (str.indexOf("cash") != -1 || str.indexOf("score") != -1 || str.indexOf("Cash") != -1) {
                                    var val = show[p] / 1000;
                                    $(item).val(val.toFixed(2));
                                } else {
                                    var val = decodeURI(show[p]);
                                    $(item).val(val);
                                }
                            }
                        }
                    });
                }
                if ($("select[name='search']")) {
                    $("select[name='search']").map(function (index, item) {
                        var num = 0;
                        var str = $(item).attr("id");
                        $(item).find("option").map(function (index, item) {
                            for (var p in show) {
                                if (p == str) {
                                    var val = decodeURI(show[p]);
                                    if ($(item).val() == val) {
                                        $(item).attr("selected", "selected");
                                    }
                                }
                            }
                        });
                    })
                }
            }
        },
        href: function (name, value, url) {
            if (url.indexOf("?") != -1) {
                url = url + "&" + name + "=" + value;
            } else {
                url = url + "?" + name + "=" + value;
            }
            return url;
        },
        parameter: function () {
            var search = {};
            if ($(".search").attr("data-id")) {
                var str = $(".search").attr("id");
                search[str] = $(".search").attr("data-id");
            }
            if ($("input[name='search']")) {
                $("input[name='search']").map(function (index, item) {
                    var str = $(item).attr("id");
                    if (str.indexOf("Time") != -1 || str.indexOf("time") != -1) {
                        search[str] = $(item).val();
                    } else if (str.indexOf("cash") != -1 || str.indexOf("score") != -1 || str.indexOf("Cash") != -1) {
                        search[str] = $(item).val() * 1000;
                    } else {
                        search[str] = $(item).val().replace(/ /, "%20");
                    }
                });
            }
            if ($("select[name='search']")) {
                $("select[name='search']").map(function (index, item) {
                    var num = 0;
                    var str = $(item).attr("id");
                    search[str] = $(item).val();
                })
            }
            return search;
        }

    };

    window.controller.Search = Search;

})(jQuery);