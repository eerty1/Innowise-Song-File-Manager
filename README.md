# Innowise-Song-File-Manager

A song file travelling across microservices

## Task Requirements

Implement the following microservice structure according to the picture below

1. Upload a song file through File API. Store the file in **S3**. If S3 is not available, then store the file locally. Save the file name, storage type (S3/local) and path in the storage in database.
2. As the file is saved, send its ID in **SQS**, Enricher Service consumes this ID and acquires data from database. Using this data, the file has to be downloaded from S3 and S3 metadata parsed. Then go to **Spotify** and fetch from its API additional information, like: author, alternative track name, duration and etc. All that data has to be sent to the next microservice.
3. Song Service consumes the data from Enricher Service and persists it in database. Song Service exposes CRUD endpoints based on the received data
4. All the requests to the services are made via **Spring Cloud Gateway**, use **Eureka** for service discovery. Deploy everything in docker compose.
5. Authentication/authorization via Auth API - use valid, signed **JWT**, which is sent to the client, all the rights are checked in the corresponding services. The rights could be decomposed as you like, for example usual users can read data, while admins also have possibility to modify it.
6. Use **Apache Camel** to work with queues in Enricher Service and Song API
7. Frontend **Angular**/**React**/**Vue**.
8. In addition to unit and integration tests, end-to-end tests have to be implemented.

Stack: **Spring Boot**, any **SQL Database** (NoSQL also possible) , **localstack**, **Apache Camel**, and further feel free to choose any technology you want

Patterns to use:
circuit breaker
distributed tracing(logs to be stored in **cloudwatch**)
externalized configuration (**spring cloud config**)

![This is an image](https://i.ibb.co/RBhJ4xJ/image.png)

## Installation

You are proposed to use Java 11.

![This is an image](https://i.ibb.co/f1HC8RZ/image.png)

Current Maven version.

![This is an image](https://i.ibb.co/5M5bxcm/image.png)

While developing this project I used **PostgreSQL** Database.

There're the steps to unpack my project: 

* git clone Innowise-Song-File-Manager

* Open it via your IDE

## Usage

Run application from command line: 

* Open the folder in which you cloned the project

* run docker-compose.yml from FileAPI to access localstack 

* run **mvn install** in a folder of a specific microservice 

* java -jar **microservice-name.jar**

## API endpoints
FileAPI: 
* http://localhost:8080/api/swagger-ui/index.html

SongAPI
* http://localhost:8082/api/swagger-ui/index.html

AuthAPI
* http://localhost:8083/api/swagger-ui/index.html

## Peculiarities

1) You have to run the microsevicies in a certain order:
**EurekaRegistry** -> **FileAPI** -> **EnricherService** -> **SongAPI** -> **AuthAPI**
In the further iterations of the project this problem will be solved with the help of docker-compose file.

2) Some CRUD endpoins in the application are a little bit redundant. They are not deleted yet, since I plan to refine them in the future.
