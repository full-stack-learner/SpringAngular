# SpringAngularServer
Pre-configured **Spring** implementation with out of the box support for OAuth2, MongoDB, REST endpoints, ... with frontend written in **Angular**.

# General Features
 - Docker (docker-compose in place for "one-click" deployment)
 - Frontend using Angular
 - Backend using Spring

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
 - Maven

# Angular Features (Frontend)
 - Authenticating with the backend (OAuth2 password & authorization_code)
 - UI using Angular Material
 - Components for Login, User creation, User listing / deletion

# Run
 - Package the application: `./mvnw package`
 - Create a docker image: `docker build -t philjay/springangular .` (optional because this is also done by 'build:'' in docker-compose in this case)
 - Run docker-compose: `docker-compose up -d` (remove -d for showing log info)
 - Force build and recreation: add `--build --force-recreate` parameters to your docker-compose command
 - To run e.g. on remote host: add `-H "ssh://youruser@your.host"` parameter (SSH access required, docker & docker-compose must be installed on remote machine, make sure you have your user permissions configured correctly - user should be in docker group, SSH MaxSessions can also interfere - configure via sshd_conf - set e.g. to 30)

 Running this will create and start all required containers. In addition to that, the database will be populated with all required data and users. To stop all containers (mongo / angular / spring), run: `docker stop $(docker ps -a -q)`

# MongoDB access
 - Run `docker exec -it mongo mongo` to connect to "mongo" (name as defined in docker-compose) container and access mongo shell
