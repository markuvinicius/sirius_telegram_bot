# SIRIUS Telegram Bot

Sirius is a multifunctional telegram bot that was made for helping you doing stuf.
Feel free to extend sirius functionality by implementing more commands

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install:

* Java OpenJDK 1.8: https://openjdk.java.net/install/
* Maven 3.3.6: https://maven.apache.org/download.cgi
* Newman 5.0.0: https://www.npmjs.com/package/newman
* Docker Comunity: https://docs.docker.com/

## Installing

A step by step series of examples that tell you how to get a development env running

### Build

As a conteinerized Java application, the build process constructs the jar artifact at `target` folder

```
cd sirius_telegram_bot
mvn clean package
```

### Build a new local Docker Image

To build a local docker image, do the following

```
docker image build -t telegram_sirius_bot .
```

### Running Locally on Docker Daemon

To run the application locally on docker daemon, do the following:

```
docker container run -d \
  -e TELEGRAM_TOKEN=<YOUR_TELEGRAM_TOKEN> \
  -e WORDS_API_KEY=<YOUR_WORDS_API_KEY>  \
  -e BOT_USER_NAME=<YOUT_BOT_USER_NAME> \
  telegram_sirius_bot
```

After running, you can start sending messages for the bot inside telegram


## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [SpringBoot](https://spring.io/projects/spring-boot) - The Java Web Framework User
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit 5](https://junit.org/junit5) - The Java Unit Test Framework
* [Swagger](https://swagger.io) - OpenApi Tool

## Contributing

Feel free to submit a pul request

## Versioning

We use [SemVer](http://semver.org/) for versioning. 

## Authors

* **Marku Vinícius** - *Initial work* - [Marku Vinicius](https://gitlab.com/markuvinicius)


## License

This project is licensed under the MIT License 

## Acknowledgments
