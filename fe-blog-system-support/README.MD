# fe-blog-system-support

​	 是这个项目的其它功能的子池模块是fe-blog-system-cloud其它功能支持的项目，由于这些模块的功能所需要的JDK版本和依赖版与父依赖不一致，就被分离了出来

​	emailService:

​		这个模块是用来发送邮箱验证码

​	fe-blog-system-chat

​		这个模块主要用于对话，Spring Boot版本为3.3.1，使用Netty 通信，对话内容会存进MongDb

​		之后会将fe-blog-system-cloud-ornament整合进这个某块
