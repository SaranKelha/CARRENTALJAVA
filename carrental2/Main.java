package carrental2;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        rentalSystem.menu();
    }
}
