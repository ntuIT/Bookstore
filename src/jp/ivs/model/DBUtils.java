package jp.ivs.model;

import jp.ivs.helper.Sharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static String jdbcURL="jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8";
    private static String jdbcUsername="thomc";
    private static String jdbcPassword="12345678";

    protected static Connection ConnectDB() throws SQLException
    {
        Connection jdbcConnection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        try {
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException ex)
        {
            System.err.println("Lỗi: " + ex.getMessage());
        }
        return jdbcConnection;
    }

    public static void insert(Book newbook) throws SQLException
    {
        Connection dbConnect = ConnectDB();
        // Chuẩn bị truy vấn SQL
        String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        // truyền tham số
        statement.setString(1, newbook.getTitle());
        statement.setString(2, newbook.getAuthor());
        statement.setFloat(3, newbook.getPrice());

        statement.executeUpdate();

        statement.close();
        dbConnect.close();
    }

    public static List<Book> getByAll() throws SQLException
    {
        List<Book> listBook = new ArrayList<>();
        Connection dbConnect = ConnectDB();
        String sql = " SELECT book_id, title, author, price FROM book ";
        Statement statement = dbConnect.createStatement();
        ResultSet bangKetQua = statement.executeQuery(sql); //get từng dòng dữ liệu bằng cái này
        //region parse các dòng data về list
        while (bangKetQua.next())   // khi vẫn còn next được (còn bản ghi)
        {   // lấy dữ liệu về
            int id = bangKetQua.getInt("book_id");  // Lấy mã
            String title = bangKetQua.getString("title");  // Lấy tiêu đề
            String author = bangKetQua.getString("author");  // lấy tác giả
            float price = bangKetQua.getFloat("price");	// lấy giá
            listBook.add(new Book(id, title, author, price));
        }
        bangKetQua.close(); //endregion
        statement.close();
        dbConnect.close();
        return listBook;
    }
    public static ArrayList<Book> getSameKeysearch(String para, int mode) throws SQLException
    {
        String sql = " SELECT book_id, title, author, price FROM book ";
        if (mode==1)
            sql += " WHERE title like '%"+para+"%' ";
        else sql += " WHERE author like '%"+para+"%' ";
        ArrayList<Book> listBook = new ArrayList<>();
        if (para.equals(""))
            listBook = (ArrayList<Book>) getByAll();
        else {
            Connection dbConnect = ConnectDB();

            Statement statement = dbConnect.createStatement();
            ResultSet dataLine = statement.executeQuery(sql);

            Sharing.parsingToBookList(dataLine, listBook);

            statement.close();
            dbConnect.close();
        } return listBook;
    }

    public static void delete(int idBook) throws SQLException
    {
        Connection dbConnect = ConnectDB();
        String sql = "DELETE FROM book where book_id = ? ";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, idBook);
        statement.executeUpdate();
        statement.close();
        dbConnect.close();
    }

    public static void update(Book bookUpdate) throws SQLException
    {
        Connection dbConnect = ConnectDB();

        String sql = "UPDATE book SET title = ?, author = ?, price = ? ";
        sql += " WHERE book_id = ? ";
        // truyền tham số
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setString(1, bookUpdate.getTitle());
        statement.setString(2, bookUpdate.getAuthor());
        statement.setFloat(3, bookUpdate.getPrice());
        statement.setInt(4, bookUpdate.getId());

        statement.executeUpdate();
        statement.close();
        dbConnect.close();
    }

    public static Book getBookByID(int id) throws SQLException
    {
        Connection dbConnect = ConnectDB();

        String sql = "SELECT * FROM book WHERE book_id = ? ";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Book book = null;
        if (resultSet.next())
        {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
            book = new Book(id, title, author, price);
        }
        resultSet.close();
        statement.close();
        dbConnect.close();
        return book;
    }
}
