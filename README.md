# Anthony Freely's attempt at the IAGL Java Take Home Test
Solution for the IAG Loyalty Java Take Home Test

The solution is Spring Boot application that exposes a ReST endpoint to the user.

The solution uses an SQL database to store the configuration for routes and cabin bonuses.

The database contains route and cabin bonus configuration as specified in the spec 

## Prerequisites
- Java 17
- Docker

## Building solution

1. Run Gradle file

Load gradle wrapper with either `gradlew.bat` or `gradlew.sh`

2. Run `gradlew build`

## Running solution

Run `docker-compose up -d`

Execute GET request on endpoint:

`http://localhost:8080/v1/avios-calculator-service?airportCodeArrival=ABC&airportCodeDeparture=XYZ&cabinCode=M`

Airport parameters must be provided and only contain letters

The cabin code endpoint can be left empty or ignored, altogether

Otherwise, you must supply a valid cabin code, else, the API will point out it's invalid