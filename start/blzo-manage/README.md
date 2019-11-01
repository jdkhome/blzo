# BLZO 管理后台脚手架

考虑到不少项目都是 先做业务 后补管理后台，所以也提供一个独立的管理后台脚手架

blzo-manage 中已经预制了功能强大的权限管理模块(blzo-ex-authj) 使用这个脚手架，你只需要专注自己的业务代码即可

根据你的需要，在blzo-manage中使用多数据源或者远程调用其他服务来管理其他业务

## 快速开始

1. 创建 [管理后台基础数据库](./doc/manage.md)
2. 修改数据库链接配置，启动项目自由探索！

## 简要介绍authj权限管理

- 开发人员只需一个注解即可完成权限配置，无需关心"用户组"、"菜单"等内容
- 运营人员无需开发人员帮助即可自由创建新管理员，并为新管理员定制授权(可随时收回)，管理员可以自由定制菜单

## 更多文档

- [BLZO管理后台开发指南](https://www.jdkhome.com/blzo/manage-dev.html)
- [BLZO管理后台权限管理使用文档](https://www.jdkhome.com/blzo/manage-auth-use.html)