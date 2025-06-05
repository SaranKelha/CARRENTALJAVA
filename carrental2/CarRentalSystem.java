package carrental2;

import java.sql.SQLException;
import java.util.Scanner;

public class CarRentalSystem {
    private CarDao carDao;
    private CustomerDao customerDao;
    private RentalDao rentalDao;
    public CarRentalSystem() {
        carDao = new CarDao();
        customerDao = new CustomerDao();
        rentalDao = new RentalDao();
    }
    public void addCar(Car car) throws SQLException {
        boolean success = carDao.addCar(car); // Calls DB method
        if (success) {
            System.out.println("Car added to the database.");
        } else {
            System.out.println("Failed to add car.");
        }
    }
    public void addCustomer(Customer customer) throws SQLException {
        customerDao.addCustomer(customer);
    }
    public void rentCar(int carId, String customerId, int days) throws SQLException {
        if (carDao.isCarAvailable(carId)) {

            // Step 2: Try to rent the car (update isavailable = false in DB)
            boolean rented = carDao.rent(carId);

            if (rented) {
                // Step 3: Add rental entry to rental table
                rentalDao.addRental(carId, customerId, days);

                System.out.println("Car rented successfully.");
            } else {
                System.out.println("Failed to rent the car. It may have just been rented by someone else.");
            }
        } else {
            System.out.println("Car is not available.");
        }
    }
    public void returnCar(Car car) throws SQLException {
        boolean success = carDao.returnCar(car.getCarId());
        if (success) {
            // Step 2: Optionally, delete or update the rental entry in DB
            boolean removed = rentalDao.removeRentalByCarId(car.getCarId());

            if (removed) {
                System.out.println("Car returned successfully.");
            } else {
                System.out.println("Car return processed, but rental record not found.");
            }
        } else {
            System.out.println("Car was not rented or already returned.");
        }



    }

    public void menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                carDao.displayinfo();
                System.out.print("\nEnter the car ID you want to rent: ");
                int carId = scanner.nextInt();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer customer = customerDao.getCustomerByName(customerName);

                if (customer == null) {
                    int customerCount = customerDao.getCustomerCount();
                    String customerId = "CUS" + (customerCount + 1);
                    customer = new Customer(customerId, customerName);
                    customerDao.addCustomer(customer);
                } else {
                    System.out.println("Welcome back, " + customer.getName() + "! (Customer ID: " + customer.getCustomerId() + ")");
                }

                Car selectedCar = carDao.getAvailableCarById(carId);
                if (selectedCar != null) {
                    // proceed with renting
                } else {
                    System.out.println("Car not available or invalid ID.");
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + customer.getCustomerId());
                    System.out.println("Customer Name: " + customer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        // 1. Rent the car in DB (set isavailable = false)
                        boolean rented = carDao.rent(selectedCar.getCarId());

                        if (rented) {
                            // 2. Add rental record to rentals table
                            rentalDao.addRental(selectedCar.getCarId(), customer.getCustomerId(), rentalDays);

                            System.out.println("\nCar rented successfully.");
                        } else {
                            System.out.println("\nFailed to rent the car. It might be already rented.");
                        }
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }


            }
            else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                int carId = scanner.nextInt();
                Car carToReturn = carDao.getRentedCarById(carId);

                if (carToReturn != null) {
                    Customer customer = rentalDao.getCustomerByCarId(carId);

                    if (customer != null) {
                        returnCar(carToReturn);  // <-- call the method that handles everything
                    } else {
                        System.out.println("Rental information not found for this car.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not currently rented.");
                }
            }

            else if (choice == 3) {
                    break;
                }
                else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

            System.out.println("\nThank you for using the Car Rental System!");
        }





}





