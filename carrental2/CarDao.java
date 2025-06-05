package carrental2;

import java.sql.*;

public class CarDao {
    public void displayinfo() throws SQLException {
        Connection con = Dbconnection.getConnection();
        Statement st = con.createStatement();
        String query = "Select * from car WHERE isavailable = TRUE";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " - " +
                    rs.getString(2) + " " +
                    rs.getString(3));
        }
    }

    public boolean rent(int carId) throws SQLException {
        String query = "UPDATE car SET isavailable = FALSE WHERE carid = ? AND isavailable = TRUE";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        int rows = ps.executeUpdate();
        return rows > 0;

    }

    public boolean returnCar(int carId) throws SQLException {
        String query = "UPDATE car SET isavailable = TRUE WHERE carid = ? AND isavailable = FALSE";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        int rows = ps.executeUpdate();
        return rows > 0;
    }


    public boolean addCar(Car car) throws SQLException {
        String query = "INSERT INTO car (carId, brand, model, basePricePerDay, isAvailable) VALUES (?, ?, ?, ?, ?)";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, car.getCarId());
        ps.setString(2, car.getBrand());
        ps.setString(3, car.getModel());
        ps.setDouble(4, car.getBasePricePerDay());
        ps.setBoolean(5, true);
        return ps.executeUpdate() > 0;
    }


    public boolean isCarAvailable(int carId) throws SQLException {
        String query = "SELECT isavailable FROM car WHERE carid = ?";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getBoolean("isavailable"); // returns true if available
        }


        return false; // if car not found or unavailable
    }
    public Car getAvailableCarById(int carId) throws SQLException {
        String query = "SELECT * FROM car WHERE carid = ? AND isavailable = TRUE";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Car(
                    rs.getInt("carid"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getDouble("basepriceperday")
            );
        }

        return null; // Car not found or not available
    }

    public Car getRentedCarById(int carId) throws SQLException {
        String query = "SELECT * FROM car WHERE carid = ? AND isavailable = FALSE";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Car(
                    rs.getInt("carid"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getDouble("basepriceperday")
            );
        }

        return null; // Car not found or is available
    }




}
