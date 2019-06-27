package jp.ivs.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ivs.model.Book;
import jp.ivs.model.DBUtils;
@WebServlet("/")
public class BookManageServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getServletPath(); // lấy hành động action gọi từ view
        try {
            switch (action)
            {
                case "/detail":
                    showBook(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBook(request, response);
                    break;
                case "/delete":
                    deleteBook(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateBook(request, response);
                    break;
                case "/":
                    listBook(request, response);
                    break;
                case "/list":
                    listBook(request, response);
                    break;
                case "/search_by_title":
                    searchBook(request, response, 1);
                    break;
                case "/search_by_author":
                    searchBook(request, response, 2);
                    break;
                // tìm theo tiêu đề thì đặt mode 1, theo tác giả thì mode 2
            }
        } catch (SQLException ex)
        {
            throw new ServletException(ex);
        }
        //đóng method
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        List<Book> listBook =DBUtils.getByAll();
        // nhảy đến trang istAllBook.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllBook.jsp");
        request.setAttribute("listBook", listBook);   // Truyền dữ liệu ra trang jsp
        dispatcher.forward(request, response);
    }

    private void showBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {
        // Lấy mã sách từ URL
        Book book2Show = DBUtils.getBookByID(Integer.parseInt(request.getParameter("id")));

        // nhảy đến BookDetails.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookDetails.jsp");
        request.setAttribute("book", book2Show);   // Truyền dữ liệu ra trang jsp
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // nhảy đến BookAdd.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookAdd.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException
    {
        Book existingBook = DBUtils.getBookByID(Integer.parseInt(request.getParameter("id")));

        // Truyền thông tin ngược ra trong JSP để hiện lên form cập nhật
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookEdit.jsp");
        request.setAttribute("book", existingBook);  // truyền dữ liệu qua jsp
        dispatcher.forward(request, response);    //nhảy đến trang BookEdit.jsp
    }

    void setBookFromRequest(HttpServletRequest request, Book book) //cài thông tin từ request vào 1 book
    {
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setPrice(Float.parseFloat(request.getParameter("price")));
    }

    // Thêm mới sách
    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        Book newBook = new Book();
        setBookFromRequest(request, newBook);
        DBUtils.insert(newBook);
        response.sendRedirect("list");
    }

    // Cập nhật sách
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {
        // tạo model book từ những thông tin request về:
        int id = Integer.parseInt(request.getParameter("id"));
        Book bookUpdate = new Book(id);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        setBookFromRequest(request, bookUpdate);
        DBUtils.update(bookUpdate);
        response.sendRedirect("list");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {
        int id = Integer.parseInt(request.getParameter("id")); // Lấy mã sách cần xóa từ URL
        DBUtils.delete(id);
        response.sendRedirect("list");

    }

    void searchBook(HttpServletRequest request, HttpServletResponse response, int mode)
            throws SQLException, IOException, ServletException
    {
        ArrayList<Book> list = DBUtils.getSameKeysearch(request.getParameter("txt_search"), mode);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllBook.jsp");
        request.setAttribute("listBook", list); 
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

}