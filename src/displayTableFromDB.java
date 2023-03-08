/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;


public class displayTableFromDB {

    static final String DB_URL = "jdbc:mysql://localhost:3306/CSCE5350_Spring";
    static final String USER = "root";
    static final String PASS = "123456789";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database...");

            // Create a statement
            stmt = conn.createStatement();

            // Display main menu
            displayMenu();

            // Get user input for table to display
            int choice = getUserInput();
            String tableName = "";

            switch (choice) {
                case 1:
                    tableName = "instructor";
                    break;
                case 2:
                    tableName = "advisor";
                    break;
                case 3:
                    tableName = "student";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            if (!tableName.equals("")) {
                // Execute query and display results
                String sql = "SELECT * FROM " + tableName;
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    switch (tableName) {
                        case "instructor":
                            System.out.println(rs.getInt("id") + ", "
                                    + rs.getString("name") + ", "
                                    + rs.getString("dept_name") + ", "
                                    + rs.getFloat("salary"));
                            break;
                        case "advisor":
                            System.out.println(rs.getInt("s_id") + ", "
                                    + rs.getInt("i_id"));
                            break;
                        case "student":
                            System.out.println(rs.getInt("id") + ", "
                                    + rs.getString("name") + ", "
                                    + rs.getString("dept_name") + ", "
                                    + rs.getInt("tot_cred"));
                            break;
                    }
                }
                rs.close();
            }

            // Clean-up environment
            stmt.close();
            conn.close();
            System.out.println("Disconnected from database...");
        } catch (SQLException se) {
            // Handle errors
            se.printStackTrace();
        }
    }

    public static void displayMenu() {
        System.out.println("Choose table to display:");
        System.out.println("1. Instructor");
        System.out.println("2. Advisor");
        System.out.println("3. Student");
    }

    public static int getUserInput() {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter choice (1-3): ");
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        }
        scanner.close();
        return choice;
    }
}
*/
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class displayTableFromDB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/CSCE5350_Spring";
    static final String USER = "root";
    static final String PASS = "123456789";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            int choice = 0;
            while (choice != 15) {
                displayMenu();
                choice = getUserInput();

                switch (choice) {
                    case 1:
                        displayTable(stmt, "instructor");
                        break;
                    case 2:
                        displayTable(stmt, "advisor");
                        break;
                    case 3:
                        displayTable(stmt, "student");
                        break;
                    case 4:
                        displayTable(stmt, "classroom");
                        break;
                    case 5:
                        displayTable(stmt, "course");
                        break;
                    case 6:
                        displayTable(stmt, "department");
                        break;
                    case 7:
                        displayTable(stmt, "prereq");
                        break;
                    case 8:
                        displayTable(stmt, "section");
                        break;
                    case 9:
                        displayTable(stmt, "takes");
                        break;
                    case 10:
                        displayTable(stmt, "teaches");
                        break;
                    case 11:
                        displayTable(stmt, "timeslot");
                        break;
                    case 12:
                        modifyAdvisor(stmt,conn);
                        break;
                    case 13:
                        insertRecord(stmt);
                        break;
                    case 14:
                        deleteRecord(stmt,conn);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void displayMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("1. Display Instructor table");
        System.out.println("2. Display Advisor table");
        System.out.println("3. Display Student table");
        System.out.println("4. Display classroom table");
        System.out.println("5. Display course table");
        System.out.println("6. Display Department table");
        System.out.println("7. Display prereq table");
        System.out.println("8. Display Section table");
        System.out.println("9. Display Takes table");
        System.out.println("10. Display Teaches table");
        System.out.println("11. Display Timeslot table");
        System.out.println("12. Modify advisor of a student");
        System.out.println("13. Insert a record into Student table");
        System.out.println("14. Delete a record from Student table");
        System.out.println("15. Exit program");
    }

    public static int getUserInput() {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter choice (1-14): ");
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        }
        scanner.nextLine(); // consume the remaining newline character
        return choice;
    }

    public static void displayTable(Statement stmt, String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        ResultSet rs = stmt.executeQuery(sql);

        int numColumns = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= numColumns; i++) {
            System.out.print(rs.getMetaData().getColumnLabel(i) + "\t");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= numColumns; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }

        rs.close();
    }

    public static void insertRecord(Statement stmt) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student department name: ");
        String deptName = scanner.nextLine();

        int totCred = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter student total credits: ");
                totCred = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume the remaining newline character
            }
        }
        scanner.nextLine(); // consume the remaining newline character

        String sql = "INSERT INTO student (ID, name, dept_name, tot_cred) VALUES ('" + id + "', '" + name + "', '" + deptName + "', " + totCred + ")";
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected == 1) {
            System.out.println("Record inserted successfully.");
        } else {
            System.out.println("Record not inserted. Please try again.");
        }
    }

    public static void modifyAdvisor(Statement stmt, Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        // Get the current advisor of the student
        String sql = "SELECT i.name FROM instructor i " +
                "JOIN advisor a ON i.ID = a.i_id " +
                "JOIN student s ON a.s_id = s.ID " +
                "WHERE s.ID = '" + studentId + "'";
        ResultSet rs = stmt.executeQuery(sql);

        String currentAdvisor = null;
        if (rs.next()) {
            currentAdvisor = rs.getString(1);
        }
        rs.close();

        System.out.println("Current advisor: " + (currentAdvisor != null ? currentAdvisor : "NULL"));

        // Display available instructors
        System.out.println("\nAvailable instructors:");
        sql = "SELECT ID, name FROM instructor";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("ID") + "\t" + rs.getString("name"));
        }
        rs.close();

        // Display all students
        System.out.println("\nAll students:");
        sql = "SELECT ID, name FROM student";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("ID") + "\t" + rs.getString("name"));
        }
        rs.close();

        // Prompt user to select a new advisor for the student
        System.out.print("\nEnter ID of new advisor: ");
        String newAdvisorId = scanner.nextLine();

        // Prompt user to select the student
        System.out.print("Enter ID of student: ");
        String studentId2 = scanner.nextLine();

        // Remove the current advisor of the student
        if (currentAdvisor != null) {
            sql = "DELETE FROM advisor WHERE s_id = '" + studentId + "'";
            stmt.executeUpdate(sql);
        }

        // Assign the new advisor to the student
        sql = "INSERT INTO advisor (s_id, i_id) VALUES ('" + studentId2 + "', '" + newAdvisorId + "')";
        stmt.executeUpdate(sql);

        System.out.println("Advisor modified successfully.");
    }

    /*
public static void modifyAdvisor(Statement stmt) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter new advisor ID: ");
        String newAdvisorId = scanner.nextLine();

        String sql = "UPDATE advisor SET i_id='" + newAdvisorId + "' WHERE s_id='" + id + "'";
        int numRowsAffected = stmt.executeUpdate(sql);

        if (numRowsAffected == 1) {
        System.out.println("Advisor modified successfully.");
        } else {
        System.out.println("Advisor modification failed.");
        }
        }
        */
   /* public static void deleteRecord(Statement stmt) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // consume remaining newline character

        String sql = "DELETE FROM student WHERE id = " + studentId;
        int rowsAffected = stmt.executeUpdate(sql);
        System.out.println(rowsAffected + " row(s) deleted.");
    }*/
    public static void deleteRecord(Statement stmt, Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the student to delete:");
        int id = sc.nextInt();

        try {
            // Check if the student exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM student WHERE id = ?");
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found.");
                return;
            }

            // Confirm deletion with the user
            System.out.println("Are you sure you want to delete this student? (yes/no)");
            String confirm = sc.next();

            if (confirm.equalsIgnoreCase("yes")) {
                // Delete the student from the student table
                PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM student WHERE id = ?");
                deleteStmt.setInt(1, id);
                deleteStmt.executeUpdate();

                // Remove the advisor relationship
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE advisor SET s_id = NULL WHERE s_id = ?");
                updateStmt.setInt(1, id);
                updateStmt.executeUpdate();

                System.out.println("Student deleted.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

}