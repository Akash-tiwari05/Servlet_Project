package login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static String LOAD_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/userdb";
    public static String USERNAME = "root";
    public static String PASSWORD = "Akash@00005";
    Connection connection;

    public RegServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            // Load MySQL JDBC Driver
            Class.forName(LOAD_DRIVER);
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Failed to establish database connection", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the connection is properly initialized
        if (connection == null) {
            throw new ServletException("Database connection is not initialized.");
        }

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String Uname = request.getParameter("Uname");
        String pword = request.getParameter("pword");

        PrintWriter pw = response.getWriter();

        try {
            // Prepare the SQL statement
            PreparedStatement ps = connection.prepareStatement("INSERT INTO unfo (fname, lname, Uname, pword) VALUES (?, ?, ?, ?)");
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, Uname);
            ps.setString(4, pword);

            int result = ps.executeUpdate();

            pw.println("<html><body bgcolor='black' text='white'><center>");
            if (result > 0) {
                pw.println("<h3>Registration Successful</h3>");
                pw.println("<a href=\"login.html\">Login</a>");
            } else {
                pw.println("<h3>Registration Failed</h3>");
            }
            pw.println("</center></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h3>SQL Error occurred. Please try again later.</h3>");
        }
    }
}
