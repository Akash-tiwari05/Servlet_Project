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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public static String LOAD_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/userdb";
    public static String USERNAME = "root";
    public static String PASSWORD = "Akash@00005";
    Connection connection;

    public LoginServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            // Load MySQL JDBC Driver
            Class.forName(LOAD_DRIVER);
            // Create the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (connection == null) {
            throw new ServletException("Database connection is not initialized.");
        }
        
        String Uname = request.getParameter("Uname");
        String pword = request.getParameter("pword");
        PrintWriter pw = response.getWriter();
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement("SELECT * FROM unfo WHERE Uname=?");
            ps.setString(1, Uname);
            rs = ps.executeQuery();
            pw.println("<html><body bgcolor='black' text='white'><center>");
            
            if (rs.next()) {
                pw.println("Welcome " + Uname);
            } else {
                pw.println("User Not Found.");
            }
            pw.println("</center></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
