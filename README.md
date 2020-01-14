# SpringAngularServer
Pre-configured **Spring** implementation with out of the box support for OAuth2, MongoDB, REST endpoints, ... with frontend written in **Angular**.

# General Features
 - Docker 🐳 (docker-compose in place for "one-click" deployment on local and remote host)
 - Frontend using Angular
 - Backend using Spring 🍃
 - MongoDB database
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
 - Authenticating with the backend (OAuth2 password & authorization_code)
 - UI using Angular Material
 - Components for Login, User creation, User listing / deletion
 - Run using `ng serve` during development, or `nginx` in production

# Run locally
 - Start your local MongoDB instance
 - Run the Spring application from your IDE (e.g. IntelliJ)
 - Make sure you have Angular CLI installed, then run the Angular application via `ng serve` in terminal (optionally add `--configuration=production` to run with production environment parameters)

# Run via Docker (local or remote)
 - Package the Spring application using Maven (navigate to folder first): `./mvnw package` or `mvn package`
 - *Optional: (Create a docker image: `docker build -t philjay/springangular .` - optional because this is also done by 'build:'' in docker-compose in this case)*
 - Run docker-compose: `docker-compose up -d` (remove -d for showing log info)
 - Force build and recreation: add `--build --force-recreate` parameters to your docker-compose command
 - To run e.g. on remote host: add `-H "ssh://youruser@your.host"` parameter (SSH access required, docker & docker-compose must be installed on remote machine, make sure you have your user permissions configured correctly - user should be in docker group, SSH MaxSessions can also interfere - configure via sshd_conf - set e.g. to 30)

 Running this will create and start all containers included in the docker-compose.yml file. To stop all containers, run: `docker stop $(docker ps -a -q)`

# MongoDB access
 - Run `docker exec -it mongo mongo` to connect to "mongo" container (name as defined in docker-compose) and access mongo shell
 - Authenticate e.g. using the root-user and root-password defined in docker-compose.yml

# Setup script (setup.sh)
 - Executed by the mongo-init container / service in docker-compose.yml file
 - Creates the MongoDB user used by Spring to access the database
 - *Optional: Can populate the database with preconfigured data (folders & .json files) specified in (mongo-init/data-setup)*