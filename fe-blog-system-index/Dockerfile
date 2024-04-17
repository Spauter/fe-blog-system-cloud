FROM openjdk:11

# 添加参数
ARG JAR_FILE
# 指定作者名和E-mail
MAINTAINER BloducSpauter "bloducspauter@gamil.com"
# 添加 Spring Boot 包, JAR_FILE 参数就是从 Docker Maven 插件中指定的构建参数


#指定挂载点
VOLUME /tmp
#将本地文件或目录复制到由dockerfile构建的镜像中
ADD target/fe-blog-system-index-1.0-exec.jar fe-blog-index.jar
# 执行启动命令,   -Djava.security.egd=file:/dev/./urandom优化随数生成策略的
#   java -jar -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom  app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/fe-blog-index.jar"]