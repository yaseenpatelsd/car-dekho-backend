🚗 CarDekho Backend API

A Spring Boot REST API for a car marketplace application where users can register, log in, and manage car listings.
It includes JWT authentication, role-based access (Admin/User), and dynamic search filters to find cars by name, brand, or price range.

🧩 Features

🔐 User Authentication – Register, login, and verify users using JWT tokens.

🧑‍💼 Role-Based Access – Admins can manage all car listings, while users can manage their own.

🚙 Car Management – Add, edit, delete, and view car posts.

🔍 Smart Search – Filter cars by brand, name, and price range.

⚙️ Exception Handling – Centralized error management with clean JSON responses.

🧾 Postman Collection – Included for easy API testing.

🛠️ Tech Stack
Layer	Technology
Backend Framework	Spring Boot
Security	Spring Security with JWT
Database	MySQL / PostgreSQL (configurable)
ORM	Spring Data JPA
Build Tool	Maven
Language	Java 17+
🔑 API Overview
🔐 Authentication
Method	Endpoint	Description
POST	/register	Register a new user
POST	/login	Login and receive JWT token
POST	/account-verification	Verify account via OTP
🚗 Car Management
Method	Endpoint	Description
GET	/car/all	Fetch all car listings
GET	/car/{id}	Fetch a single car by ID
GET	/car/search?brand=&name=&minPrice=&maxPrice=	Search cars dynamically
POST	/car/add	Add new car listing (requires token)
DELETE	/car/remove/{id}	Delete car (admin only)
🧑‍💼 Admin
Method	Endpoint	Description
GET	/admin/all-users	View all registered users
DELETE	/admin/remove/{id}	Delete a user (admin only)
POST	/admin/post	Add car post as admin
PUT	/admin/edit/{id}	Edit car post
📦 Setup & Run
1️⃣ Clone the repo
git clone https://github.com/<your-username>/car-dekho-backend.git
cd car-dekho-backend

2️⃣ Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/cardekho
spring.datasource.username=root
spring.datasource.password=yourpassword

secret.Key=yourSecretKeyHere1234567890

3️⃣ Run the app
mvn spring-boot:run


App will start at: http://localhost:8080

🧪 Postman Collection

Import the included file:
CarDekho_API_Collection.postman_collection.json

Set your environment variable:

{{base_url}} = http://localhost:8080
{{token}} = your_JWT_token_here

🧠 Future Enhancements

Add image upload for car posts.

Integrate payment gateway for booking/reservations.

Add email verification and password reset.

🧑‍💻 Author

Yaseen Patel
📧 [your-email@example.com
]
💼 github.com/yourusername
