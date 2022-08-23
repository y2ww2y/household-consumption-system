前端文件夹有readme文件供理解前端。

后续会更新更具体的内容

更于2022/8/23 12:30

****

### 下载后的部署：

**前端**：

1. 这里是vscode下，<u>*Ctrl j*</u> 打开终端，运行<u>*npm install*</u>  下载依赖包，生成node_modules

![image-20220823091107406](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823091107406.png)

2. 需要在这里试运行把vue.config.js里的target改成自己要访问的后端地址，方便区分带有/api的都是后端路径，否则是前端vue路由。要更改后端对应的mapping路径，到src/page各个路由vue文件的axios请求即可。

   后面用nginx作为代理服务器进行跨域访问，不需要这个了，仅作为vscode上测试使用

```vue
devServer: {//代理服务器
    proxy: {
      '/api': {//路径有这个的会跨域访问
        target: 'http://localhost:8082',//要访问的路径
        pathRewrite: { '^/api': '' },//访问前把/api换成/
        ws: true,
        // changeOrigin: true //假报地址
      },
    },
  },
```

3.<u>*npm run serve*</u> 在vscode运行，数据交互需要启动后端项目。

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823094903995.png" alt="image-20220823094903995" style="zoom: 33%;" />

4.<u>*npm run build*</u> 项目打包成dist文件夹。

![image-20220823100235799](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823100235799.png)



**后端**：

先建本地sql，建库字符集<u>utf8 -- UTF-8 Unicode</u>，排序规则<u>utf8_general_ci</u>，引入sql文件后有两个表，bill--消费记录，member--家庭成员。导入项目后只需要更改src/main/resources/application.yml中的配置，,检查一下pom mysql连接依赖版本，运行后同前端进行测试

```properties
server:
  port: 8082 #端口号
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource #数据库连接池
    driver-class-name: com.mysql.cj.jdbc.Driver #连接驱动
    url: jdbc:mysql://localhost:3306/consume?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```



<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823103208674.png" alt="image-20220823103208674" style="zoom: 33%;" />

**linux部署：**

1.关闭防火墙

```shell
systemctl stop firewalld #关闭防火墙
systemctl status firewalld #查看防火墙状态

```

![](D:\桌面\屏幕截图 2022-08-23 103756.jpg)

2.navicat远程连接linux中的mysql,建库建表，记得更新一下后端数据库配置。后端如果要建在windows比较简单，后面注意nginx地址配置就行。

3.确认配置后使用maven打jar(默认)包。用xftp放入虚拟机，其他方式也行。war包也可以打，但是要部署到tomcat，得处理内置tomacat与外置的冲突，比较麻烦。在虚拟机使用 <u>java -jar jar包路径 &</u> 后台启动，建议不加&,另开终端，可以看到错误。

4.将dist文件夹放到linux中，我放在了nginx文件夹下，后面文件配置路径对应就行

![image-20220823112932792](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823112932792.png)

5.<u>nginx/conf</u>  更改nginx.conf文件。

![](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823133216354.png)

```c
		#nginx 监视的端口号，也就是访问前端的端口号，随意设置
		listen       8080;  
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
		
		#访问8080就去找root对应资源
        location / {
            #dist文件夹的位置，这里放在nginx里，是相对路径
            root   dist;
            #自动获取dist内的index
            try_files $uri $uri/ /index.html;
            index  index.html index.htm;
        }
		#拦截访问路径前缀有/api的
        location /api/ {   
            #重写路径,/api转成/
	        rewrite ^/api/(.*)$ /$1 break;
            #代理服务器，后面跟后端的war包名，一定加/
        	proxy_pass http://localhost:8082/; 
        }
```

6.<u>/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf</u>  启动nginx，根据自己nginx位置改命令

7.浏览器查看结果，从外面访问虚拟机地址

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823115949453.png" alt="image-20220823115949453" style="zoom:50%;" />

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20220823134106224.png" alt="image-20220823134106224" style="zoom:50%;" />

**不足：** 无表单验证，没有超时处理，如果后端访问不到没有反馈。
更新于2022/8/23 13:55