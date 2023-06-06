# INSPECFLOW

督察数据模型图如下：


![](uml/model.svg)


督察业务流程图如下：
![](uml/flow.svg)

问题状态图如下：

![](uml/stateissue.svg)

## flowable-UI

- flowable项目代码下载 `https://github.com/flowable/flowable-engine/releases/tag/flowable-6.8.0` , 选择 **flowable-6.8.0.zip** 下载
- 解压以后，在 `/wars` 下找到两个war包，`flowable-rest.war` 和 `flowable-ui.war`
    - 在windows环境下，可以安装 tomcat，然后将这两个war包，放置在 `/webapps` 目录下，使用`/bin/startup.bat` 运行tomcat即可。
    - 在mac环境下，可以在vscode中安装插件，`Community Server Connector`，然后安装tomcat server 9.0.41，部署 `flowable-ui.war`，
    - 在网页端输入`localhost:8080/flowable-ui`，第一次登陆会需要输入账号密码，其中管理员 账号：`admin`，密码：`test`
    - 注意：flowable-6.8.0 的war包中springboot内置tomcat版本是**9.x**，所以使用的tomcat版本也一定要是**9.x**，不然就会一直报错 `404`

flowable-ui界面如下：
![](uml/flowable-ui%E7%95%8C%E9%9D%A2.png)

登陆后界面如下：
![](uml/flowable-ui%E4%B8%BB%E9%A1%B5%E9%9D%A2.png)

- 任务应用程序：这个模块主要进行流程相关的操作，比如发起、审核等，发布后的应用程序会显示在这里
- 建模器应用程序：这个模块是核心，主要用来创建流程、发布流程等等
- 管理员应用程序：这个模块就是管理发布的应用程序，可以上传/删除应用程序等等
- 身份管理应用程序：这个模块就是用户管理模块，包括用户的创建、权限分配等等

具体使用可以见开发者文档：`https://tkjohn.github.io/flowable-userguide/#flowableUIApps` 或者 `https://github.com/qiudaoke/flowable-userguide/blob/master/V6.5/docs/userguide/src/zh_CN/bpmn/ch13-UI.adoc` 
