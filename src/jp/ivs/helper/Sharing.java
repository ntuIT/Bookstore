package jp.ivs.helper;

import jp.ivs.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Sharing
{
    public static void parsingToBookList(ResultSet dataLine, ArrayList<Book> list) throws SQLException
    {
        while (dataLine.next())    {
            int id = dataLine.getInt("book_id");
            String title = dataLine.getString("title");
            String author = dataLine.getString("author");
            float price = dataLine.getFloat("price");
            list.add(new Book(id, title, author, price));
        }   dataLine.close();
    }
}
