# Banking Portal API

A secure REST API for banking operations built with Spring Boot, Spring Security, JWT, JPA, MySQL, Redis, and Java 17.

## Features

- User registration and login
- JWT-based authentication
- Password reset with OTP verification
- Email OTP authentication
- Account creation
- PIN creation and update
- Cash deposit and withdrawal
- Fund transfers
- Transaction history
- Bank statement email delivery
- User profile updates
- Request validation and global exception handling
- Redis caching and idempotency support
- Swagger/OpenAPI documentation

## Technology Stack

- Java 17+
- Spring Boot 3.3.1
- Spring Security
- Spring Data JPA
- Spring Data Redis
- MySQL
- JWT
- Maven
- JUnit 5 and Mockito
- H2 for automated tests
- Docker support

## Project Structure

```text
BankingPortal-API-main/
├── src/
│   ├── main/
│   │   ├── java/com/webapp/bankingportal/
│   │   └── resources/
│   └── test/
├── docker/
├── pom.xml
├── Dockerfile
├── mvnw
└── application.properties.sample


Requirements
Install the following software:

JDK 17 or later
MySQL 8+
Redis
Maven, or use the included Maven Wrapper
Docker and Docker Compose, optionally
Configuration
Copy the sample configuration file:

cp src/main/resources/application.properties.sample \
   src/main/resources/application.properties




spring.datasource.url=jdbc:mysql://localhost:3306/banking_portal
spring.datasource.username=your-database-user
spring.datasource.password=your-database-password

jwt.secret=your-base64-encoded-jwt-secret
jwt.expiration=86400000

spring.mail.username=your-email
spring.mail.password=your-mail-app-password

geo.api.key=your-geolocation-api-key


Database Setup
CREATE DATABASE banking_portal;





Security
Never commit application.properties.
Use environment variables or a secret manager for credentials.
Use a strong Base64-encoded JWT secret.
Use a mail provider application password instead of a personal password.
Rotate credentials if they are accidentally eThe API provides consistent responses for:

Error Handling
The API provides consistent responses for:

Invalid user details
Invalid credentials
Invalid or expired JWT tokens
Invalid OTPs
Invalid PINs
Insufficient account balance
Missing accounts
Invalid transaction amounts
Duplicate email or phone numbers



Maintainer
Mahesh Kajale
Email: maheshkajale2112@gmail.com
License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.
