Book lovers often accumulate books they’ve read and are eager to explore new reading material. Traditional methods of book exchanges, such as local swaps or lending among friends, are limited in scope and accessibility. This project aims to develop a full-stack web application to serve as a centralised platform for book exchanges, enabling users to trade, lend, and borrow books efficiently.

Features
User Authentication
Secure registration and login system.
Password encryption and recovery options.
Users can manage their accounts and log out.
User and change there password, if forget 

Book Listing
Users can list books with details such as:
Title
Author
Genre
Condition
Availability status
Options to edit or delete listings.

Book Search
Advanced search and filtering features to find books based on:
Title, Author, Genre, Location, and Availability.
Paginated or incremental loading of results.

Exchange Requests
Users can send, receive, and negotiate exchange requests.
Notifications for request status (Pending, Accepted, Rejected, Modified).

User Profiles
Users can maintain profiles with:
Reading preferences.
Favorite genres.
Books they own or wish to acquire.

Tech Stack
Frontend: React.js
Backend: Spring Boot with Hibernate
Database: MySQL
Testing - Postman 


How to Run the Project

Backend
Navigate to the backend directory:  cd book-exchange-backend  
Build and run the Spring Boot application: mvn spring-boot:run

Ensure the MySQL database is running.  Use the database name: book_exchange.


Frontend

Navigate to the frontend directory: cd book-exchange-frontend

Install dependencies: npm install

Start the React development server: npm start

Setup & Configuration

Create a MySQL database named book_exchange.
Configure the database connection in application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/book_exchange
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>

SMTP for Notifications

Update application.properties for email configuration:

spring.mail.host=smtp.wilp.bits-pilani.ac.in
spring.mail.port=587
spring.mail.username=<your-email>
spring.mail.password=<your-password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


Contact
For queries, contact:
Name:Harshada Anil Bhole



