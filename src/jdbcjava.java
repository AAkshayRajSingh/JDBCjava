import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcjava {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            String url = "jdbc:mysql://localhost:3306/CSCE5350_Spring";
            String user = "root";
            String password = "123456789";
            conn = DriverManager.getConnection(url, user, password);

            // Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT id, name, dept_name FROM student";
            rs = stmt.executeQuery(sql);

            // Iterate through the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept_name = rs.getString("dept_name");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + dept_name);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                // Close resources
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
