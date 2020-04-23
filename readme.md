# Novschola
![novschola logo](https://i.imgur.com/6chErgC.png)

> Note: Novschola is currently in development and most of its functions are not ready yet

Novschola is a social platform designed for schools

  - Exchange thoughts 
  - Help each other
  - Stay in touch
  - Study together 
  - Make school work better

# New Features!

  - Login to your own account
  - Create your own post
  - Add comments to posts


### Tech

Novschola uses a number of open source projects to work properly:
* [Java] - Great object oriented language for backend
* [Spring] - Java framework for IOC and web development
* [Hibernate] - Awesome object relational mapping
* [mysql] - Awesome database management system

And of course Novschola itself is open source with a [public repository]
 on GitHub.

### Installation

Novschola requires:
 - JDK > 11 (prefered openJDK)
 - mysql database with imported novschola schema
 - smtp server

Install the dependencies and start the server.

```sh
$ mvn install
$ java -Dspring.profiles.active=prod -DJWT_SECRET="${jwtSecret}" -DSMTP-PASSWORD="${smtpPassword}" -DSMTP-USERNAME="${smtpUsername}" -DSMTP-HOST="${smtpHost}" -DSMTP-PORT="${smtpPort}" -DMYSQL-HOST="${mysqlHost}" -DMYSQL-PORT="${mysqlPort}" -DMYSQL-NAME="${mysqlName}" -DMYSQL-PASSWORD="${mysqlPassword}" -DMYSQL-USERNAME="${mysqlUsername}" -DNOVSCHOLA-URL="${baseUrl}" -Djava.security.egd=file:/dev/./urandom -jar target/novschola-0.0.1-SNAPSHOT.jar
```
Dont forget to set:
- ```${jwtSecret}``` - jwt secret string used to sign jwt tokkens it has to be >= 256 bits
-  ```${smtpPassword}``` - Password for smtp
-  ```${smtpUsername}``` - Smtp login
-  ```${smtpHost}``` - smtp host address
-  ```${smtpPort}``` - smtp port default set to ```587```
-  ```${mysqlHost}``` - mysql database address
-  ```${mysqlPort}``` - mysql database port default set to ```3306```
-  ```${mysqlName}``` - mysql novschola database name default set to ```novschola```
-  ```${mysqlPassword}``` - password for mysql database
-  ```${mysqlUsername}``` - username for mysql database
-  ```${baseUrl}``` - url of your novschola instance default set to ```http:127.0.0.1:8080```

Use novschola.sql file to set up your database.

### Documentation
Open api documentation available: https://app.swaggerhub.com/apis/kacperszot/Novschola/
### Development

Want to contribute? Great!

Just import project to your favorite IDE like any other maven project and don't forget to install Lombak support for your ide.
Use ```dev``` profile for development

> Note: in dev profile, novschola do **not** send the activation mail, user is already active after registration

### Docker
Novschola is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
$ cd novschola
$ docker build -f Dockerfile -t novschola .
```
This will create the novschola image and pull in the necessary dependencies.

Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 8000 of the host to port 8080 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 -e jwtSecret=${jwtSecret} -e smtpPassword=${smtpPassword} -e smtpUsername=${smtpUsername} -e smtpHost=${smtpHost} -e smtpPort=${smtpPort} -e mysqlHost=${mysqlHost} -e mysqlPort=${mysqlPort} -e mysqlName=${mysqlName} -e mysqlPassword=${mysqlPassword} -e mysqlUsername=${mysqlUsername} -e baseUrl=${baseUrl} novschola
```

where:
- ```${jwtSecret}``` - jwt secret string used to sign jwt tokkens it has to be >= 256 bits
-  ```${smtpPassword}``` - Password for smtp
-  ```${smtpUsername}``` - Smtp login
-  ```${smtpHost}``` - smtp host address
-  ```${smtpPort}``` - smtp port default set to ```587```
-  ```${mysqlHost}``` - mysql database address
-  ```${mysqlPort}``` - mysql database port default set to ```3306```
-  ```${mysqlName}``` - mysql novschola database name default set to ```novschola```
-  ```${mysqlPassword}``` - password for mysql database
-  ```${mysqlUsername}``` - username for mysql database
-  ```${baseUrl}``` - url of your novschola instance default set to ```http:127.0.0.1:8080```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:8000/v1/posts
```
### Docker hub
You can use docker hub image ```kacperszo/novschola```, there is also preconfigured mysql image ```kacperszo/novschola-db```
### Docker compose
To use Docker compose create ```.env``` with content:
```
JWT_SECRET=${jwtSecret}
SMTP_USERNAME=${smtpUsername}
SMTP_PASSWORD=${smtpPassword}
SMTP_HOST=${smtpHost}
SMTP_PORT=${smtpPort}
DB_PASSWORD=${mysqlPassword}
```
dont forget to set
  - ```${jwtSecret}``` - jwt secret string used to sign jwt tokkens it has to be >= 256 bits
  -  ```${smtpPassword}``` - Password for smtp
  -  ```${smtpUsername}``` - Smtp login
  -  ```${smtpHost}``` - smtp host address
  -  ```${smtpPort}``` - smtp port default set to ```587```
  -  ```${mysqlPassword}``` - password for mysql database

to start docker-compose type
```bash
$ docker-compose up
```
Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:8000/v1/posts
```
