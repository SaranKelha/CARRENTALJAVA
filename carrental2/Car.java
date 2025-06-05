package carrental2;

public class Car {
    private int carId;
    private String brand;
    private String model;
    private double basePricePerDay;

    /*constructor*/

    public Car(int carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;

    }
    public int getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
    public double getBasePricePerDay() {   // ✅ Required for database insertion
        return basePricePerDay;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }
}
