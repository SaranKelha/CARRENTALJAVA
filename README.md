Car Rental System 🚗

A simple Java-based Car Rental System that allows customers to rent and return cars, with data stored in a MySQL database.

📌 Features
- Add, rent, and return cars
- Track customer information
- Store rentals in MySQL
- Console-based menu system

 🛠 Technologies Used
- Java
- MySQL
- JDBC

📂 Project Structure
carrental2/
├── Car.java
├── Customer.java
├── Rental.java
├── CarDao.java
├── CustomerDao.java
├── RentalDao.java
├── Dbconnection.java
└── CarRentalSystem.java



 🚀 How to Run

1. Clone the repository
   git clone https://github.com/your-username/car-rental-system.git
   cd car-rental-system

2.  Set up MySQL database
Create a database and run the provided schema.sql file (if you have one)
Update your database credentials in Dbconnection.java

3. Compile and run
javac carrental2/*.java
java carrental2.CarRentalSystem
