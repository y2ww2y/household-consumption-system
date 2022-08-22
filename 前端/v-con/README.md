### Vue部分：

本项目用vue3，vue-cli5开发。

![1](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220822231604185.png)

**dist:** <u>npm run build</u> 对vue项目进行打包后的文件，用于项目部署。

**node_modules:** 项目运行的依赖，我的有几百MB,就不传了，项目下下来先 <u>npm install</u> 命令会自动生成本包

**public：** index.html 文件和favicon.ico图标文件，index.html中id="app"我设置了高度，不然页面中只占一部分

**src:** 主要放代码的地方 里面的包名自己起的，大体还算规范。

​	assert: 放项目中用到的一些图片

​	components: 自己写的vue组件，可能有重复的代码，可以自己简化

​	page：几个路由跳转的vue文件

​	router：配置路由的文件

​	App: 入口vue文件

​	main: 入口文件

**.gitignore:** 设置上传git忽略的文件

**babel.config.js:** babel配置文件，自动生成，不知道干啥的

**jsconfig.json:**  自动生成，可以提供路径的重名。

**package-lock.json:**  锁定依赖包版本号以及依赖的依赖版本号，防止<u>npm install</u> 导入依赖时和原来的不一样。自动生成

**package.json:** 可读元数据（如项目名称和说明）以及功能元数据（如程序包版本号和程序所需的依赖项列表）自动生成。

**vue.config.js:** 可选的配置文件，我配置了关掉lint,测试运行时的代理服务器，publicPath,部署时可能会用得上的，一个关于相对路径的参数，不过前后端分离我部署在了nginx上，可能最后没用上。

因为没有做表单验证，消息记录的添加除了消费详情(desc)都要填，不然添加失败，修改只要随便一个就可以。

