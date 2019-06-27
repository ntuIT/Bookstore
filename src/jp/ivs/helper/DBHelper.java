package jp.ivs.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper
{
    private static String jdbcURL="jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8";
    private static String jdbcUsername="thomc";
    private static String jdbcPassword="12345678";
    static Connection connection;
    static Statement statement;
    public static Connection getConnectDB() throws SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException ex)
        {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return connection;
    }
    public static Connection connectDB(String string, String user, String pass) throws SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        try {
            connection = DriverManager.getConnection(string, user, pass);
        } catch (SQLException ex)
        {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return connection;
    }

}
