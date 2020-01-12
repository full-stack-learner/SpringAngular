# SpringAngularServer
Pre-configured Spring implementation with out of the box support for OAuth2, MongoDB, REST endpoints, ...

# Features
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
 - Docker (docker-compose)

# Run
 - Package the application: './mvnw package'
 - Create a docker image: 'docker build -t philjay/springangular .' (optional because this is also done by 'build:'' in docker-compose in this case)
 - Run docker-compose: 'docker-compose up -d --build --force-recreate' (remove -d for showing log info)
 - Running this will create and start all required containers. In addition to that, the database will be populated with all required data and users.

# MongoDB access
 - Run 'docker exec -it mongo mongo' to connect to "mongo" (name as defined in docker-compose) container and access mongo shell
