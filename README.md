# Java developer home assignment
This is a simple RESTful web service with single endpoint that provides user current weather conditions using devices IP address.
Client should provide IP address as Header parameter in request to **/weather** endpoint
### Used technologies
- Java 17
- Spring boot 2.7.5
- Gradle
- Redis
- Lettuce
- Liquibase
- H2 database
- Junit

### 3rd party services used for geolocation and current weather updates
- **IpGeolocation** (Library used to retrieve location from IP address) : https://app.ipgeolocation.io/
- **WeatherApi** (Service that provides current weather updates) : https://www.weatherapi.com/

### Non-functional requirements description

Data retrieved from 3rd party services are stored in database two different tables. Tables for data storage are created using 'Liquibase' scripts.
'Redis' is used as in memory locationCache for storing IP addresses and city associated  with that address as first layer data retrieval.
Exception handling is used to handle unavailability of 3rd party services and also if client provides invalid IP address.
Unfortunately I don't think I have managed to achieve 80% test coverage, but managed to show my general approach in test writing.

### NOTE
Redis should be running on machine for application to run properly on port '6379' ('localhost') 