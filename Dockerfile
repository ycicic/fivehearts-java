FROM java:8
ENV TAG_VERSION=0.0.1
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone
ADD /target/fivehearts.jar /home/fivehearts/fivehearts.jar
EXPOSE 11013
LABEL version = $TAG_VERSION
ENTRYPOINT java -jar /home/fivehearts/fivehearts.jar
MAINTAINER https://github.com/fivehearts