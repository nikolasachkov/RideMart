# RideMart

RideMart is a Spring Boot-based backend application designed for a motorbike marketplace platform. It allows users to register, log in, create advertisements, **filter listings by make, model, year, price, and other criteria**, and manage their listings securely with JWT authentication.

## Technologies

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT (JSON Web Token)
* MapStruct
* Lombok
* MySQL

## Getting Started

### Prerequisites

* Java 21
* Maven
* MySQL

### Running the App

1. Clone the repository:

   ```bash
   git clone https://github.com/nikolasachkov/RideMart.git
   cd RideMart
   ```

2. Configure database credentials in `application.properties` or via environment variables.

3. Build and run:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Open your browser at `http://localhost:8080`

## REST Endpoints

### Authentication

* `POST /api/auth/register` – Register a new user
* `POST /api/auth/login` – Authenticate and receive a JWT

### Users

* `GET /api/users` – Retrieve all users
* `GET /api/users/{id}` – Retrieve a specific user
* `PUT /api/users/{id}` – Update a user
* `DELETE /api/users/{id}` – Delete a user

### Makes

* `GET /api/makes` – Retrieve all available motorbike makes

### Models

* `GET /api/makes/{make}/models` – Retrieve models for a given make

### Advertisements

* `POST /api/advertisements` – Create a new advertisement
* `GET /api/advertisements` – Retrieve all advertisements
* `GET /api/advertisements/{id}` – Retrieve a specific advertisement
* `PUT /api/advertisements/{id}` – Update an advertisement
* `DELETE /api/advertisements/{id}` – Delete an advertisement
* `GET /api/advertisements/filter` – Filter advertisements by criteria:

  * `city`
  * `make` and dependent `model`
  * `minYear`, `maxYear`
  * `minPrice`, `maxPrice`
  * `engineType`, `motorbikeType`, `fuelSystemType`

### Saved Advertisements

* `GET /api/saved` – Retrieve saved advertisements for the authenticated user
* `POST /api/saved/{advertisementId}` – Save an advertisement
* `DELETE /api/saved/{advertisementId}` – Remove a saved advertisement

## License

This project is licensed under the MIT License.
