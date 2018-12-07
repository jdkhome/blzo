"use strict";

(function () {

    function Editor() {
    }

    var E = window.wangEditor;
    //这里的id为<div id="editor"中的id.
    Editor.editor = new E('#editor');
    // 配置服务器端地址,也就是controller的请求路径，不带项目路径，前面没有/
    Editor.editor.customConfig.uploadImgServer = '/api/editor/uploadInfo';
    //配置属性名称，绑定请求的图片数据
    //controller会用到，可以随便设置，但是一定要与controller一致
    Editor.editor.customConfig.uploadFileName = 'file';
    //自定义菜单栏
    Editor.editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'image',  // 插入图片
        'table'  // 表格
    ];
    //插入图片的回调
    Editor.editor.customConfig.linkImgCallback = function (url) {
        console.log(url); // url 即插入图片的地址
    };


    //创建编辑器
    Editor.editor.create();

    /*
            //默认的菜单项
            [
                'head',  // 标题
                'bold',  // 粗体
                'fontSize',  // 字号
                'fontName',  // 字体
                'italic',  // 斜体
                'underline',  // 下划线
                'strikeThrough',  // 删除线
                'foreColor',  // 文字颜色
                'backColor',  // 背景颜色
                'link',  // 插入链接
                'list',  // 列表
                'justify',  // 对齐方式
                'quote',  // 引用
                'emoticon',  // 表情
                'image',  // 插入图片
                'table',  // 表格
                'video',  // 插入视频
                'code',  // 插入代码
                'undo',  // 撤销
                'redo'  // 重复
            ]*!/

            /!*$("#editorSetBtn").click(function () {
                //这是设置编辑器内容
                editor.txt.html("dsafdfadsfdafdsfds");
            })
            $("#editorGetBtn1").click(function () {
                //获取编辑器的内容，带样式
                //一般使用这个获取数据，通过ajax发送给服务端　，然后服务端以Ｓtring接收，保存到数据库．
                alert(editor.txt.html());
            })
            $("#editorGetBtn2").click(function () {
                //获取编辑器的内容，不带样式，纯文本
                alert(editor.txt.text());
            })
    */

    window.controller.Editor = Editor;
})();