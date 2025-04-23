# Online Store Backend

This is the backend system of an online store developed in Java using Spring Boot. The project supports user authentication, product management, ordering, reviewing, and coupon-based discounts. It serves as a RESTful API for a web-based frontend.

## Features

### Users
- Registration and login with JWT-based authentication
- User roles: `CUSTOMER`, `ADMIN`
- Passwords securely hashed with BCrypt

### Products
- CRUD operations for products (admin only)
- Product listing, filtering by category
- Product details including available stock and reviews

### Orders
- Authenticated users can place orders
- Supports optional discount coupons
- Order history available per user
- Automatic discount price calculation

### Coupons
- Coupons with expiration dates, usage limits, and minimum order values
- Coupon usage tracked per user

### Reviews
- Users can submit, view, and delete product reviews

## Technologies Used

| Layer         | Technologies                                  |
|---------------|-----------------------------------------------|
| Core Backend  | Java 20, Spring Boot                          |
| Security      | Spring Security, JWT                          |
| Database      | MySQL, Spring Data JPA (Hibernate)            |
| Build Tool    | Maven                                         |
| Testing       | JUnit 5, Mockito                              |
