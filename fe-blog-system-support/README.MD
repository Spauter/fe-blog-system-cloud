# fe-blog-system-support
## 说明
​	 是这个项目的其它功能的子池模块是fe-blog-system-cloud其它功能支持的项目，由于这些模块的功能所需要的JDK版本和依赖版与父依赖不一致，就被分离了出来

​	emailService:

​		这个模块是用来发送邮箱验证码，使用的spring的自动装配

​	fe-blog-system-chat

​		这个模块主要用于对话，Spring Boot版本为3.3.1，使用Netty 通信，对话内容会存进MongDb

​		之后可能会将fe-blog-system-cloud-ornament整合进这个某块
## 配置
### JDK版本
使用的JDK版本为17
### MongoDb
使用的Docker镜像版本为4.4.29 ,在使用exec进入容器后，执行:

```sql
use fe_blog;

db.createUser({
  user: "oldfu",
  pwd: "123456",  
  roles: [
    { role: "readWrite", db: "fe_blog" },
  ],
  mechanisms: ["SCRAM-SHA-1", "SCRAM-SHA-256"],
  customData: {
    name: "oldfu",
    age: 18
  }
});

db.auth("oldfu","123456")
```
### Minio
```shell
mkdir "/home/asd/docker/minio"
docker run -p 9000:9000 -p 9001:9001 --name bsminio\
  -v /home/asd/docker/minio:/data \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  minio/minio:RELEASE.2022-09-07T22-25-02Z server /data --console-address ":9001"
```
### 其它
naocs 版本不变
由于这个模块会用到fe-blog-system-bean中类,先要在这个模块下执行"mvn install"