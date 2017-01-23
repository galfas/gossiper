# Gossiper

This project was built in java 8 with spring. It exposes an API that will retrieve data from its on database(MongodDB), as I have a limited number of requests per second I designed based on the premise that the user's requests couldn't consume direct the Riot API. 

###Start the application###
in order to start the application, it is necessary to have <b>Mongo</b> and <b>RabbitMQ</b> running. There are 2 options you can install the dependencies by yourself or if you have docker you can just run the following command: 

- *docker-compose up*

<b>After you have the dependencies running you just have to execute the follow command:</b> 

- *./gradlew clean build run* 

To execute the test, you have to execute: 

*./gradlew clean build*


## Using ##

Sending a post request to the api:

http://{host}:{port}/gossiper/account/
```javascript

{
	"name": "{userName}",
	"region": "{userRegion}"
}
```
<i>**replace the {} for the value you are searching for.</i>

####Execute this request:
    - The WebsService will receive the request and then insert a services to insert it into a Queue;
    - The account listener will receive the message an fetch the data from Riot's api;
    - A message will be post into the stats queue;
    - The StatsListener will receive the message fetch the data from Riot's api and then save it into the database
     
and then you will be able access the API via: 

<i>http://localhost:9090/gossiper/account/?name={userName}&region={userRegion}</i>

###The product
Based on the User's stats, I want to create a service that provide information like:
 - The best partner (friend that make the user wins more battle)
 - The worst enemy (friend that make the user looses more battle)
 - User best champion
 - User worst champion
 and so on 
 (this would be the MVP)


## Next steps:
####Product:
1. Create the model for Consolidate.
2. Create the algorithm to generate Consolidate
3. Create an Angular 2 application for the frontend. 
    2.1 I thought about render server side, so far indexing is not requirement. So I will create a SPA

####Technical
1. Implement Swagger to document the api;
2. Improving the api error handle;
3. Prepare the entire application to run with Docker-compose;
4. Create health check;
5. Create tests for DAO
6. Improve the API security.
7. Improving the services resilience
8. Implement a layer of cache with Redis
9. Using mongo with replica set
