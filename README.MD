## Controll Access API Project

This project is an API project that contains a CRUD for the User entity, so it's possible to add, remove, update and get all the users that you want.

On this project too, I also created an endpoint to consume quotas, that will check if the user is blocked or not, and if no, will make the request but
will increase the access count attribute, if achieve the max number will return an error on the next request.

For the question about the change from the datasource, I created a mechanism were we can change the repository, based on the time. And the 
structure it's easy to add, change or remove a datasource without the needed to change all the core from the system.

## Project Techonologies
- Java 17
- Gradle 8.5
- MySQL

## Instruction to run the API
I created a Docker-compose file where i put the mySql configurations, so it's basically to run the container, and after that run the application.

To run the container it's only to run 
* docker-compose up

If you are not using an IDE, you can run the code directly from the built jar with the command
* java -jar build/libs/vicarius-api-0.0.1-SNAPSHOT.jar  

## Problems with this solution
- As we have two or more datasources switching, we could have problems that data it's not present in both, for instance,
  if we have an user blocke for many attempts in MySQL, when the datasource changes this user will not be blocked in the
  another datasource, to resolve this, if I would have more time, i would like to implement a cache mechanism on top
  of this to do like a lazy loading.
- For each new entity we need to create the repository classes for both, i would like to work in a QueryBuilder that 
  would reduce the number of code to write every time that we need to create a new entity.


## Tests
- To run the tests you need to start the mySql Container.
