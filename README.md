# HrmSysG2

前后端分离开发示例
前端使用Vue.js + Element + vue-router + axios 开发
* 使用Visual Studio Code作为IDE
* 使用vue-cli脚手架搭建 webpack 打包工程

SSM、Spring+SpringMVC+MyBatis开发示例
* 使用IntelliJ IDEA作为开发工具
* Maven作为工程管理
* MySQL作为数据库

# 编译部署
##前端编译
前端采用vue-cli脚手架创建了工程，webpack打包，使用很简便，
如果要单独测试前端，可以命令行进入工程目录，执行`npm run dev`
如果要编译后部署使用，命令行进入工程目录，执行`npm run build`，编译完成后在工程目录下生成一个 **dist** 目录，内含 index.html 文件和static目录，static目录中有生成的 JavaScript文件；

## 开发环境部署
可以将前端编译结果放入后端src\main\webapp\ 目录下，然后在IDEA环境下直接启动Jetty插件，本地 localhost:8088 访问即可（端口号自己配置）
## 生产环境部署
部署好Jetty，将前后端生成结果放置到Jetty的webapps目录下，运行Jetty