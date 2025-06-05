package carrental2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao {
    public void addCustomer(Customer customer) throws SQLException, SQLException {
        String query = "INSERT INTO Customer (customerId, cusname) VALUES (?, ?)";
        Connection con =Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, customer.getCustomerId());
        ps.setString(2, customer.getName());
        ps.executeUpdate();
    }

    public int getCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customer";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public Customer getCustomerByName(String name) throws SQLException {
        String query = "SELECT customerid, cusname FROM customer WHERE cusname = ?";
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Customer(rs.getString("customerid"), rs.getString("cusname"));
        }
        return null;
    }



}
