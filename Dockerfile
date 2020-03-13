FROM openjdk:11

ENV jwtSecret="CHANGE ME!"
ENV smtpPassword=""
ENV smtpUsername="root"
ENV smtpHost="127.0.0.1"
ENV smtpPort="587"
ENV mysqlPassword=""
ENV mysqlUsername="root"
ENV mysqlHost="db"
ENV mysqlPort="3306"
ENV mysqlName="novschola"

EXPOSE 8080

VOLUME /tmp

ENV baseUrl="http://127.0.0.1:8080"

ADD target/*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-DJWT_SECRET=${jwtSecret}","-DSMTP-PASSWORD=${smtpPassword}","-DSMTP-USERNAME=${smtpUsername}","-DSMTP-HOST=${mysqlHost}","-DSMTP-PORT=${smtpPort}","-DMYSQL-HOST=${mysqlHost}","-DMYSQL-PORT=${mysqlPort}","-DMYSQL-NAME=${mysqlName}","-DMYSQL-PASSWORD=${mysqlPassword}","-DMYSQL-USERNAME=${mysqlUsername}","-DNOVSCHOLA-URL=${baseUrl}","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]