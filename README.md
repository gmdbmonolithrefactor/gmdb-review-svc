# gmdb-review-svc 
Reference implementation for the review service, for the gMDb Monolith Refactor exercise

## Configuration: Environment Variables
* EUREKA_CLIENT_ENABLED=false  # Default
* EUREKA_HOST=gmdb-discovery:8761  
* DB_HOST_AND_PORT=localhost:3306 # Default 
* DB_USER=gmdb #Default
* DB_PWD=someGoodSecret

## Endpoint Examples
* All reviews for a user - GET: `/gmdb/api/reviews/{userScreenName}`
* All reviews for a movie - GET: `/gmdb/api/reviews/{imdbid}` note: service wil not validate the imdbid
* Add a review for a movie - POST: `/gmdb/api/reviews` Body, TBD, should include userid, title, and details of the review.  Should also require token to confirm user.

## Docker Instructions
````
$ docker build -t gmdb/review .

$ docker run -d -p [localport]:8080 \
        -e EUREKA_CLIENT_ENABLED=true \
        -e EUREKA_HOST=gmdb-discovery:8761 \ 
        -e DB_HOST_AND_PORT=gmdb-devdb:3306 \
        -e DB_USER=gmdb \
        -e DB_PWD=someGoodSecret \
        --network gmdb-bridge \
        gmdb/review
```` 

## PCF Instructions
1. Build project as normal `$ ./gradlew clean build`
1. Push from root directory `$ cf push`

NOTE: Requires a mysql database service named gmdb-devdb (ex: ClearDb MySQL Database free-spark DB).  Name of service can be changed on command line, or in the included manifest.yml. 

