# MusicApp Backend

### [Main documentation](../DeveloperNotes.md).

## Introduction
This is the backend service for the MusicApp, a system designed for users to keep track of their favorite songs. It's built with Spring Boot and provides APIs for song management including adding, fetching, filtering, and sorting songs.

### Technologies and Key Dependencies

- **Java 17**
- **Spring Boot 3.2.3** for rapid development
- **PostgreSQL**
- **Spring Data JPA** for database interactions
- **Springdoc OpenAPI** for dynamic API documentation generation
- **Lombok** to reduce boilerplate code
- **JUnit & Mockito** for testing

## Getting Started

### Prerequisites
- Java 17
- Maven
- PostgreSQL

### Configuration
Update `src/main/resources/application.properties` to set your database connection properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=user
spring.datasource.password=pass
```

### Running the Application Locally
1. Build the project using Maven:
```
mvn clean install
```
2. Run the application:
```
java -jar target/musicapp-backend-0.0.1-SNAPSHOT.jar
```
Alternatively, you can run the application directly using Maven:
```
mvn spring-boot:run
```
The application will start on http://localhost:8080/api/musicapp.

## API Documentation
Access the API documentation at http://localhost:8080/api/musicapp/swagger-ui.html

### API Endpoints

* `POST /api/songs`: Add a new song.
* `GET /api/songs`: Retrieve all songs.
* `GET /api/songs?artist={artist}&year={year}`: Filter songs by artist and/or year.
* `GET /api/songs/{id}`: Get details of a specific song.
* `GET /api/songs?sortProperty={property}&sortDir={direction}`: Sorts the list of songs based on a specified property (`name` or `year`) and direction (asc or desc). Pagination parameters (`page` and `size`) can also be included.
* `DELETE /api/songs/{id}`: Delete a specific song.

#### Special Query Parameters for Pagination and Sorting
- `page`: Specifies the page number for pagination (0-indexed).
- `size`: Specifies the number of items per page.
- `sortProperty`: Specifies the property to sort by (`name` or `year`).
- `sortDir`: Specifies the sort direction (`asc` for ascending or `desc` for descending).

### Example API calls

1. GET all songs: `http://localhost:8080/api/musicapp/songs`
2. GET songs by artist: `http://localhost:8080/api/musicapp/songs?artist=John Lennon`
3. GET songs by year: `http://localhost:8080/api/musicapp/songs?year=1971`
4. GET songs by artist and year: `http://localhost:8080/api/musicapp/songs?artist=John Lennon&year=1971`
5. GET songs by page and page size: `http://localhost:8080/api/musicapp/songs?page=0&size=20`
6. GET songs sorted by name in DESC order: `http://localhost:8080/api/musicapp/songs?sortProperty=name&sortDir=desc`
7. GET songs sorted by year in ASC order: `http://localhost:8080/api/musicapp/songs?sortProperty=year&sortDir=asc`
8. GET songs by artist sorted by year in DESC order: `http://localhost:8080/api/musicapp/songs?artist=Queen&sortProperty=year&sortDir=desc`
9. GET song by ID: `http://localhost:8080/api/musicapp/songs/13`
10. DELETE song by ID: `http://localhost:8080/api/musicapp/songs/13`
11. ADD song: `http://localhost:8080/api/musicapp/songs`

#### Sample POST inputs:

1. ```
   {
    "name": "Billie Jean",
    "artist": "Michael Jackson",
    "album": "Thriller",
    "year": 1982,
    "length": 5.24,
    "genre": "Pop"
    }
   ```
2. ```
   {
    "name": "Stairway to Heaven",
    "artist": "Led Zeppelin",
    "album": "Led Zeppelin IV",
    "year": 1971,
    "length": 8.43,
    "genre": "Rock"
    }

## Testing:
Run the following command to execute the unit tests:
```
mvn test
```


