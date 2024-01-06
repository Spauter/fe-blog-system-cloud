# 个人博客

此博客系统是改自FE-个人博客,用于三阶段项目课程设计；原作者地址：

[FE-博客系统](https://gitee.com/gu_jun_mo/fe-blog-system)


## 前端技术栈

HTML、CSS、JavaScript、sass、Less、Layui、Apache Echarts

## 后端技术栈

Java11+Maven 3.9.5+Spring Boot 2.7.16+Spring Cloud 3.1.6+MyBatis Plus+Jakarta  Mail+Sentinel+Gateway+git/github+MySQL8+STDOUT_LOGGING(Mybatis日志工具)+Nacos+Redis+Lombok+fastjson+fastDFS

## 测试工具

Apache Jmeter(~~没有流量就自己创造流量~~),Cpolar(内网穿透方便其他同学测试，访问速度比较慢。~~毕竟是免费的东西，没钱整校园网和云服务器~~。)

## 环境配置

本次更新，需要使用java11运行环境，除此之外还需要Redis和Nacos而外的配置。

由于SpringBoot内置一个Tomcat服务器，故不需要而外配置Tomcat服务器

## 使用说明

除配置数据库外，还需要而外以下操作:

1. Redis(下载链接：[Redis Windos版下载](https://github.com/tporadowski/redis/releases "Redis Windos版下载")),下载完毕后即可打开
2. Nacos(使用文档[Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html "Nacos 使用文档"))，下载完毕后根据官方文档，创建一个命名空间，id号为192099489，名字FE_BLOG,并在配置管理找到相应的命名空间，然后把配置文件（.zip）导入。
3. Sentinel([Sentinel使用文档](https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D))下载后找到sentinel-dashboard.jar,并启动它
3. 把upload文件放在D盘根目录（如果是Windows环境下）
4. 使用相关工具导入数据库脚本文件
5. 单独启动另外一个项目：EmailService 需要使用idea另外打开这个项目,并配置application.yml(~~当时直接上传github被警告了~~)
6. 启动fe-blog-system

## 注意事项

由于数据库脚本中头像使用的是windosn路径下的文件名（真的只是文件名）但是微服务架构必须在Liunx环境下运行，后续可能需要更改头像地址,因此可能需要Docker模拟该环境（后续再补)









