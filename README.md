# Online Book Exchange Platform

A web-based university book exchange platform built with Spring Boot, allowing students to buy, exchange, and manage university books through a secure and structured system.

## Features

### User Management
- User registration and login
- Secure password encryption
- Profile management
- Role-based access control
- User blocking and unblocking

### Book Listings
- Create Sell or Exchange listings
- Add book information and images
- Edit and delete personal listings
- Automatic listing expiration after 30 days
- Listing status management
- Image upload with a 5MB size limit

### Buying
- Reserve available books
- Prevent concurrent reservations
- Cancel reservations
- Mark reserved books as sold

### Book Exchange
- Create exchange proposals
- Accept or reject exchange requests
- Cancel accepted exchanges
- Confirmation from both users
- Automatically complete the exchange after both users confirm

### Search & Filtering
- Search by book title
- Filter by course code
- Filter by category
- Filter by condition
- Filter by listing type

### Messaging
- Send messages to listing owners
- Inbox
- Sent messages
- Messages linked to specific listings

### Admin Panel
- View registered users
- Block and unblock users
- View all listings
- Delete listings

## Technologies

- Java 24
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- Spring Security
- Thymeleaf
- MySQL
- Maven
- HTML
- Tailwind CSS
- JavaScript

## Design Patterns

This project implements several design patterns:

- **Factory Method Pattern** — used to create different listing types.
- **Strategy Pattern** — used for searching and filtering listings.

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/BookStore/OnlineBookExchange/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repo/
│   │       ├── entity/
│   │       ├── DTOs/
│   │       ├── security/
│   │       ├── strategy/
│   │       ├── factory/
│   │       └── excption/
│   │
│   └── resources/
│       ├── static/
│       │   └── js/
│       └── templates/
│
└── test/
Database

The application uses MySQL.

Create a database named:

CREATE DATABASE book_exchangedb;

Database credentials are provided through environment variables:

DB_USERNAME
DB_PASSWORD

The credentials are intentionally not stored in the repository.

Running the Project
1. Clone the repository
git clone https://github.com/Bataynehcmd/OnlineBookExchange.git
2. Configure database credentials

Set the following environment variables:

DB_USERNAME=your_username
DB_PASSWORD=your_password
3. Start the application

Using Maven:

./mvnw spring-boot:run

On Windows:

.\mvnw.cmd spring-boot:run

The application will be available at:

http://localhost:8080
Security
Passwords are encrypted using BCrypt.
Authentication is handled using Spring Security.
Role-based authorization is implemented for administrative functionality.
CSRF protection is enabled.
Database credentials are stored outside the source code.
Project Status

Completed and tested as a full Spring Boot web application.

Author

Ahmad Batayneh
