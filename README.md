# Anthony Freely's attempt at the IAGL Java Take Home Test
Solution for the IAG Loyalty Java Take Home Test

The solution is Spring Boot application that exposes a ReST endpoint to the user.
The 

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
`http://localhost:8080/v1/avios-calculator-service?airportCodeArrival={}&airportCodeDeparture={}&cabinCode={}`

The cabin code endpoint can be left empty or ignored, altogether