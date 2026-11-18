# Banking Portal API

A secure REST API for banking operations, built with **Java 17** and **Spring Boot 3.3.1**. It supports user authentication, account and PIN management, deposits and withdrawals, fund transfers, transaction history, password recovery, email notifications, and bank statement delivery.

## Maintainer

**Mahesh Ganesh Kajale**  
**Email:** maheshkajale2112@gmail.com

## Features

- User registration and login
- Password-based and OTP-based login
- JWT authentication and logout
- Password reset through email OTP
- Automatic account creation
- Account PIN creation and updates
- Cash deposits and withdrawals
- Fund transfers between accounts
- Transaction history
- Dashboard user and account details
- Bank statement delivery by email
- Redis-backed caching and idempotency support
- Swagger/OpenAPI documentation
- Spring Boot Actuator endpoints

## Technology Stack

| Area | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3.1 |
| Security | Spring Security, JWT |
| Data | Spring Data JPA, MySQL 8 |
| Cache | Spring Cache, Redis 7, Caffeine |
| Email | Spring Mail |
| API documentation | Springdoc OpenAPI / Swagger |
| Mapping | MapStruct |
| Utilities | Lombok, Google libphonenumber |
| Testing | JUnit 5, Mockito, H2, GreenMail |
| Build | Maven and Maven Wrapper |
| Containers | Docker and Docker Compose |
| CI/CD | GitHub Actions and Jenkins |

## Repository Structure

```text
.github/
  workflows/ci.yml       GitHub Actions build and publish workflow
.mvn/wrapper/            Maven Wrapper configuration
docker/
  docker-compose.yml     API, MySQL, and Redis stack
docs/
  Banking-Portal.postman_collection.json
src/
  main/
    java/com/webapp/bankingportal/
      config/            Spring, security, CORS, Swagger, and cache configuration
      controller/        REST controllers
      dto/               Request and response data objects
      entity/            JPA entities
      exception/         Application exceptions
      mapper/            Entity and DTO mapping
      repository/        Spring Data repositories
      security/          JWT authentication filter and entry point
      service/           Business and integration services
      type/              Cache and enum types
      util/              Shared application utilities
    resources/
      application.properties.sample
      logback-spring.xml
  test/java/             JUnit and Mockito tests
.dockerignore
.env.example
Dockerfile
Jenkinsfile
LICENSE
pom.xml
SECURITY.md
mvnw
mvnw.cmd
```

## API Overview

JWT-protected endpoints require the `Authorization: Bearer <token>` header.

### Authentication and Users

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `POST` | `/api/users/register` | Register a user and create an account | Public |
| `POST` | `/api/users/login` | Log in with credentials and receive a JWT | Public |
| `POST` | `/api/users/generate-otp` | Generate an OTP for login | Public |
| `POST` | `/api/users/verify-otp` | Verify an OTP and log in | Public |
| `POST` | `/api/users/update` | Update user details | Required |
| `GET` | `/api/users/logout` | Invalidate the current token and log out | Required |

### Password Recovery

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `POST` | `/api/auth/password-reset/send-otp` | Send a password-reset OTP | Public |
| `POST` | `/api/auth/password-reset/verify-otp` | Verify OTP and receive a reset token | Public |
| `POST` | `/api/auth/password-reset` | Set a new password | Public |

### Account Operations

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `GET` | `/api/account/pin/check` | Check whether an account PIN exists | Required |
| `POST` | `/api/account/pin/create` | Create an account PIN | Required |
| `POST` | `/api/account/pin/update` | Update an account PIN | Required |
| `POST` | `/api/account/deposit` | Deposit cash | Required |
| `POST` | `/api/account/withdraw` | Withdraw cash | Required |
| `POST` | `/api/account/fund-transfer` | Transfer funds to another account | Required |
| `GET` | `/api/account/transactions` | Get account transactions | Required |
| `GET` | `/api/account/send-statement` | Email the account bank statement | Required |

### Dashboard

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `GET` | `/api/dashboard/user` | Get the logged-in user's profile | Required |
| `GET` | `/api/dashboard/account` | Get the logged-in user's account details | Required |

## Configuration

Create a local application configuration:

```sh
cp src/main/resources/application.properties.sample \
  src/main/resources/application.properties
```

Replace the placeholders in `application.properties`:

```properties
server.port=8180

spring.datasource.url=jdbc:mysql://localhost:3306/banking_portal
spring.datasource.username=your-database-user
spring.datasource.password=your-database-password

jwt.secret=your-base64-encoded-jwt-secret
jwt.expiration=86400000
jwt.header=Authorization
jwt.prefix=Bearer

spring.mail.username=your-email
spring.mail.password=your-mail-app-password
geo.api.key=your-geolocation-api-key
```

Never commit real database credentials, JWT secrets, email credentials, or API keys.

## Local Development

### Prerequisites

- JDK 17
- Maven 3.9 or later, or the Maven Wrapper
- MySQL 8
- Redis 7

### Database

```sql
CREATE DATABASE banking_portal;
```

Run the application:

```sh
./mvnw spring-boot:run
```

The API runs at `http://localhost:8180`.

Swagger UI is available at:

- `http://localhost:8180/swagger-ui.html`
- `http://localhost:8180/swagger-ui/index.html`

OpenAPI JSON is available at `http://localhost:8180/v3/api-docs`.

## Docker

Copy the environment template and provide real secrets where needed:

```sh
cp .env.example .env
```

Start MySQL, Redis, and the API:

```sh
docker compose -f docker/docker-compose.yml up --build
```

Build the image directly:

```sh
docker build -t bank-api .
docker run --rm -p 8180:8180 bank-api
```

The Docker Compose stack exposes:

- API: `8180`
- MySQL: `3306`
- Redis: `6379`

## Testing

Tests use JUnit 5, Mockito, H2, and GreenMail.

```sh
./mvnw test
```

Maven writes test reports to `target/surefire-reports/`.

## CI/CD

### GitHub Actions

The workflow in `.github/workflows/ci.yml`:

- Runs for pushes to `main` and pull requests.
- Uses Java 17 and Maven caching.
- Runs the test suite.
- Packages the application JAR.
- Builds the Docker image.
- Pushes `latest` and commit-tagged images to GHCR for pushes to `main`.

### Jenkins

`Jenkinsfile` provides stages for checkout, test, package, Docker build, and optional registry push.

Configure a Jenkins username/password credential named `docker-registry-credentials`. Configure pipeline parameters for:

- `DOCKER_IMAGE`
- `DOCKER_REGISTRY_HOST`
- `DOCKER_REGISTRY_PUSH`

## Postman Collection

A ready-to-import Postman collection is provided at:

[`docs/Banking-Portal.postman_collection.json`](docs/Banking-Portal.postman_collection.json)

## Security

- Never commit `.env`, `application.properties`, credentials, or private keys.
- Use a strong, Base64-encoded JWT secret.
- Use environment variables or a secret manager in deployed environments.
- Prefer application passwords rather than personal email passwords.
- Use HTTPS and restrict database and Redis network access in production.
- Review [SECURITY.md](SECURITY.md) for vulnerability reporting.

## License

Licensed under the Apache License 2.0. See [LICENSE](LICENSE).
