package carrental2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalDao {
    public void addRental(int carId, String customerId, int days) throws SQLException {
        String query = "INSERT INTO Rental (carId, customerid, days) VALUES (?, ?, ?)";
        Connection con =Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        ps.setString(2, customerId);
        ps.setInt(3, days);
        ps.executeUpdate();

    }
    public boolean removeRentalByCarId(int carId) throws SQLException {
        String query = "DELETE FROM rental WHERE carid = ?";
        Connection con =Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        int rows = ps.executeUpdate();
        return rows > 0;

    }
    public Customer getCustomerByCarId(int carId) throws SQLException {
        String query = "SELECT c.customerid, c.cusname FROM rental r JOIN customer c ON r.customerid = c.customerid WHERE r.carid = ?";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, carId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Customer(rs.getString("customerid"), rs.getString("cusname"));
        }

        return null;
    }

}
