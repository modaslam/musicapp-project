# Developer Notes for MusicApp

## Project Overview
The MusicApp is designed to allow users to manage their favorite songs. The application consists of a Spring Boot backend and a React frontend.

## System Design and Decisions
- **Backend Framework:** Spring Boot was chosen for its simplicity in creating RESTful services and its extensive support for data access, security, and testing.
- **Frontend Library:** React was required for the task and used for the development of a dynamic and responsive UI.
- **Database:** PostgreSQL is used for data persistence, due to its robustness and support for complex queries.
- **Deployment:** Docker containers are utilized for both frontend and backend for ease of deployment and scalability.
### Implemented Features

1. **Dynamic Song Retrieval:**
    - Implemented the SongService class to manage the retrieval and manipulation of song data.
    - Introduced the getSongs method, allowing users to filter, sort, and paginate songs based on artist, year, page number, size, sort property, and direction.

2. **Custom Filtering and Sorting:**
    - Utilized Specification in SongRepository to dynamically build queries based on the provided filters (artist and year).
    - Enabled sorting and pagination by integrating Spring Data JPA's Pageable interface.

3. **Specifications for Advanced Queries:**
    - Extended JpaRepository and JpaSpecificationExecutor in SongRepository to leverage standard CRUD operations and execute dynamic queries with specifications.
    - Developed SongSpecifications class with static methods hasArtist and hasYear to create Specification instances for filtering songs by artist name and year.

4. **Error Handling:**
    - Created the `ErrorHandler` class to handle various exceptions and provide meaningful error responses.
    - Implemented custom exceptions such as NotFoundException for handling resource not found scenarios.

5. **Input Validation:**
   - Applied validation constraints in DTOs and used Spring's @Valid annotation to ensure the integrity of incoming data.

6. **RESTful API Design:**
    - Designed and documented REST API endpoints to interact with the frontend, ensuring seamless data flow and user experience.

7. **Testing:**
   - Developed unit tests for SongService, focusing on the logic for filtering, sorting, and pagination.
   - Used MockMvc for testing controller endpoints and validating responses.
   - Developed some unit tests for the frontend as well, `SongService.test.js` to test the logic for the service.

8. **Swagger Integration:**
    - Integrated Swagger for API documentation, providing an interactive UI for exploring and testing the available endpoints.


## Running the Application
- Ensure Docker is installed and running.
- From the project root directory, run `docker-compose up -d --build` to build and start both frontend and backend services, including db.
- Access the frontend at `http://localhost:3000`

## Stopping the Application
- From the project root directory, run `docker-compose down`.

## Deployment
- The application can be deployed on any platform that supports Docker, such as AWS ECS, Azure Container Instances, or Kubernetes.
- Environment variables can be used to configure database connections and other settings in production environments.

## Challenges and Solutions
- **Performance:** To ensure the application is performant even with a large number of songs, pagination was implemented on the backend.
- **UI Responsiveness:** The use of Material-UI in the frontend helps in achieving a responsive design that adapts to various screen sizes. It also helped reduce development time.

## Potential System Design Overview
- **MusicApp Frontend:** Serves the user interface, where users can add, view, filter, and sort their music catalog. It's built and served by a Docker container, accessible on port 3000 (chose port 3000 as there was a time-consuming issue regarding port 80).
- **MusicApp Backend:** Handles business logic, processes API requests from the frontend, and communicates with the database and cache. It's also containerized, with communication set on port 8080.
- **Database (PostgreSQL)**: Stores data about the songs. It's run in a Docker container, with its data volume persisted and exposed on port 5432 for direct database access.
- **Cache Layer (Redis/Memcached):** Incorporating a cache layer could enhance performance by caching frequent queries and results.
- **CDN/Edge Server:** Distributes static content of the frontend globally, enhancing load times and reducing bandwidth usage.

## Future Improvements
- Implement authentication and authorization for user-specific song catalogs.
- Add more comprehensive tests for both frontend and backend components.
- Design and implement a more aesthetic and functional UI, with proper theming and brand colors.
- Explore service mesh options for better service-to-service communication and monitoring in a microservices architecture.
- Set up CI/CD pipelines for easy updates to the frontend and backend.
- For scalability and management, Kubernetes or Docker Swarm may be considered, especially if the application scales out.

## Further Reading:
- For more information on the backend setup, see the [backend help documentation](./musicapp-backend/HELP.md).
- For more information on the frontend setup, see the [frontend help documentation](./musicapp-frontend/README.md).
- To view an example system design: [System design diagram](system-design.png).
