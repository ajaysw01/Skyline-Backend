
# Skyline-Hotel 

Skyline-Hotel is a comprehensive hotel booking web application designed for secure and efficient room reservations. The app provides an easy-to-use interface for users to book rooms, manage bookings, and for admins to manage rooms and bookings. The platform uses modern technologies like Spring Boot, Spring Security, JWT, and MySQL to ensure secure user authentication and robust data handling.

## Features

- **User Registration and Authentication**: Users can register, log in, and receive JWT tokens for secure access.
- **Role-Based Access Control**: Admin and User roles with different permissions.
- **Room Management**: Admins can add, update, and delete hotel rooms.
- **Room Booking**: Users can book rooms based on availability and receive a confirmation code.
- **Booking Management**: View and cancel bookings, track bookings using confirmation codes.
- **Secure Payments**: Integrated with Razorpay for handling payments.
- **Room Availability Check**: Users can search for available rooms based on dates and room types.
- **Admin Controls**: Manage bookings and roles in the system.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Security, JWT for authentication, MySQL for database
- **APIs**: RESTful APIs for user, room, booking, and role management
- **Database**: MySQL
- **Security**: Spring Security with JWT for authentication and authorization

## Setup Instructions

### Prerequisites

- Java 8 or later
- Maven
- MySQL or Docker for running MySQL
- Postman (for API testing)
- Razorpay API credentials (for payments)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ajaysw01/Skyline-Backend
   ```

2. **Navigate to the project directory:**

   ```bash
   cd Skyline-Backend
   ```

3. **Configure MySQL Database:**

   Set up your MySQL database and update the `application.properties` file with your MySQL connection details:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hotel_booking
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run the application:**

   Use Maven to build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the application:**

   The application will be accessible at `http://localhost:8080`.

### Testing the API

You can use Postman to test the APIs. Some sample endpoints are listed below.

## API Endpoints

### Authentication

- **Register User**: `POST /auth/register-user`
- **Login User**: `POST /auth/login`

### Room Management (Admin Only)

- **Add Room**: `POST /rooms/add/new-room`
- **Update Room**: `PUT /rooms/update/{roomId}`
- **Delete Room**: `DELETE /rooms/delete/room/{roomId}`
- **Get All Rooms**: `GET /rooms/all-rooms`
- **Get Room by ID**: `GET /rooms/room/{roomId}`
- **Get Room Types**: `GET /rooms/room/types`

### Booking Management

- **Get All Bookings** (Admin only): `GET /bookings/all-bookings`
- **Book a Room**: `POST /bookings/room/{roomId}/booking`
- **Get Booking by Confirmation Code**: `GET /bookings/confirmation/{confirmationCode}`
- **Get Bookings by User Email**: `GET /bookings/user/{email}/bookings`
- **Cancel Booking**: `DELETE /bookings/booking/{bookingId}/delete`

### Role Management (Admin Only)

- **Get All Roles**: `GET /roles/all-roles`
- **Create New Role**: `POST /roles/create-new-role`
- **Delete Role**: `DELETE /roles/delete/{roleId}`
- **Assign User to Role**: `POST /roles/assign-user-to-role`
- **Remove User from Role**: `POST /roles/remove-user-from-role`
- **Remove All Users from Role**: `POST /roles/remove-all-users-from-role/{roleId}`

## Future Enhancements

- **Front-end Integration**: Connect the backend with an Angular or React-based frontend for a complete full-stack application.
- **Notifications**: Add email or SMS notifications for booking confirmations and cancellations.
- **Analytics**: Provide an admin dashboard with booking analytics.
- **Reviews**: Allow users to leave reviews for rooms they have booked.

## Author

- **Ajay Wankhade** - [LinkedIn](https://www.linkedin.com/in/ajaysw01)

---

Feel free to open issues or contribute to the project by submitting pull requests.
```

This `README.md` is designed to provide a quick overview of the project, instructions for setup, and details about the API. You can modify the details as per your project and add further sections like "Contributing" or "License" if needed.
