# Task Management System

This project is a requirement for CSYE 6200 Enterprise Software Design course at Northeastern University. It is a task management system that enables users to authenticate themselves and assign tasks, post comments, and view tasks across all users or that specific user depending on role-based access control mechanisms. The system is designed to be similar to Jira and is implemented using Java, Spring Boot, Spring MVC, and Hibernate. The system supports CRUD operations on tasks.

## Features

- User authentication: Users can log in to the system using their credentials.
- Task assignment: Users can create tasks and assign them to other users.
- Commenting: Users can post comments on tasks.
- Role-based access control: Different users have different roles, and access to certain functionalities is restricted based on their roles.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Hibernate

## Getting Started

### Prerequisites

To run this project, you need to have the following installed on your machine:

- Java 11 or later
- Maven

### Installation

To install the project, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the following command:

```
mvn spring-boot:run
```

This will start the application on your local machine.

## Usage

To use the application, open a web browser and navigate to `http://localhost:8080`. You should see the login page. Log in using your credentials to access the system.
