# SpringAngularServer
Pre-configured **Spring** implementation with out of the box support for OAuth2, MongoDB, REST endpoints, ... with frontend written in **Angular**.

# Getting Started
To get started with Angular, [read this](https://angular.io/guide/setup-local). Make sure you have your favourite IDE (VSCode of course) and `Angular CLI` installed, using Node Package Manager (NPM): `npm install -g @angular/cli`.

# General Features
 - [Docker](https://www.docker.com/) 🐳 (docker-compose in place for "one-click" deployment on local and remote host)
 - Frontend using [Angular](https://angular.io/)
 - Backend using [Spring Boot](https://spring.io/) 🍃
 - [MongoDB](https://www.mongodb.com/) database
 - Optional: Pre-populating database with data via Docker & sh script

# Spring Features (Backend)
 - Written in **Kotlin**
 - **OAuth2** support
 - Implemented grant types: password, implicit, authorization_code, refresh_token
 - Configured form / web login
 - **AES-256** encryption
 - **BCrypt** password encoder
 - Preconfigured property (.yml) files & profiles
 - **REST API** configuration secured by OAuth2
 - **MongoDB** support (Spring Data MongoDB)
 - Repository for OAuth2Access- & RefreshToken
 - Repository for OAuth2 User
 - Repository for OAuth2 Client
 - Repository for AuthorizationCode
 - **Well tested**
 - Maven build

# Angular Features (Frontend)
 - Authentication with the backend (OAuth2 password & authorization_code)
 - UI using [Angular Material](https://material.angular.io/)
 - Existing components for Login, User creation, User listing / deletion
 - Run Angular app using `ng serve` during (local) development, or `nginx` in production (see Dockerfile)

# Run the stack locally
 - Start your local MongoDB instance
 - Run the Spring application from your IDE (e.g. IntelliJ)
 - Make sure you have Angular CLI installed, then run the Angular application via `ng serve` in terminal (optionally add `--configuration=production` to run with production environment parameters)

# Run via Docker (local or remote)
 - Package the Spring application using Maven (navigate to folder first): `./mvnw package` or `mvn package`
 - *Optional: (Create a docker image: `docker build -t philjay/springangular .` - optional because this is also done by 'build:'' in docker-compose in this case)*
 - Build with docker-compose: `docker-compose build --no-cache`
 - Run docker-compose locally: `docker-compose up -d --build --force-recreate` (remove -d for showing log info)
 - To run e.g. on remote host: add `-H "ssh://youruser@your.host"` parameter (SSH access required, docker & docker-compose must be installed on remote machine, make sure you have your user permissions configured correctly - user should be in docker group, SSH MaxSessions can also interfere - configure via sshd_conf - set e.g. to 30)
 - Example remote: `docker-compose -H "ssh://user@your.domain.com" up -d --build --force-recreate`

 Running this will create and start all containers included in the docker-compose.yml file. To stop all containers, run: `docker stop $(docker ps -a -q)`

# MongoDB access
 - Run `docker exec -it mongo mongo` to connect to "mongo" container (name as defined in docker-compose) and access mongo shell
 - Authenticate e.g. using the root-user and root-password defined in docker-compose.yml

# Setup script (setup.sh)
 - A convenience setup script performing pre-config operations
 - Executed by the mongo-init container / service in docker-compose.yml file
 - Creates the MongoDB user used by Spring to access the database
 - *Optional: Can populate the database with preconfigured data (folders & .json files) specified in (mongo-init/data-setup)*
 
 # Misc
 This project uses (among others) the following packages:
  - @angular/cli
  - @angular/core
  - @angular/cdk
  - @angular/material
  
 To update, check out the [Angular update guide](https://update.angular.io/).
