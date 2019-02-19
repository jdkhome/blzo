## 老版管理后台 迁移到 新版管理后台指南

> 通过预设的模板页面来降低管理后台的开发成本，具体的好处就不一一列举了。

新版管理后台，相对旧版来说，布局更合理，主页面区域更大，菜单可隐藏，可扩展的功能更多，并且拥有更多的预设页面
现在除了常规的列表页和详情页模板，在构建页面时，你有了更多的选择。

### 迁移说明:

只有resources中的静态资源和html模板需要替换，服务端代码不用动
新版后台的自动菜单、分页器等复用模板均已实现，直接引用即可，用法和之前一致

- 原来我们自定义的JS 95% 兼容 (大部分不用修改，但是仍需测试，避免页面结构改变导致一些JS失效)
- 原来我们自定义的PAGE 80% 兼容 (页面主体几乎不用修改，只有少量按钮不兼容)

## 开始迁移:

### 备份原有后台项目并创建新的管理后台项目

> 该步骤 建议不要在IDE中进行，会很卡的

```
# cd project-addr/
# cp -r xxxx-manage xxxx-manege-old
# echo "include 'xxxx-manage-old'" >> settings.gradle
```

接下来我们将在 xxxx-manage 模块中迁移管理后台模板
xxxx-manage-old 是原来的管理后台模块

### 准备基础文件

删除xxxx-manage模块中 resource下面的 static 和 templates 目录
然后将blzo中的static 和 templates 复制到xxxx-manage模块的对应位置

将xxxx-manage-old模块中 resources/templates/manage/page 目录下 原来的自定义PAGE 复制到 xxxx-manage模块的对应目录
将xxxx-manage-old模块中 resources/static/manage/js/page 目录下 原来的自定义JS 复制到 xxxx-manage/resources/static/manage/custom/page

### 检查页面

现在可以启动项目了，理论上，除了 BLZO自带的也买之外，你的自定义页面会全炸，主要原因是，公共模板的路径更改了。

保留页面主体和JS引用部分，其他参考BLZO现有示例，进行替换即可

> 注意，原来的搜索按钮，用的是 class="search" 由于和页面模板有冲突，所以换成 id="list-search" 

新旧页面模板、开发模式、技术栈基本一致，但是不排除你在旧版开发时，引入了一些第三方或者实现了一些复杂的功能导致无法兼容，**强烈建议对所有页面测试**。