<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" href="resource/style.css">
    <title>Ứng dụng quản lý kho sách</title>
    <style>
        tr:nth-child(even) {background-color: #f2f2f2;}
    </style>
</head>
<body>
<jsp:include page="resource/header.jsp"></jsp:include>
<div style="overflow-x:auto;" align="center">
    <table  class="id_table" border="1" cellpadding="5">
        <caption>
            <h2>Danh mục sách</h2>
        </caption>
        <tr>
            <th>Tên sách</th>
            <th>Tác giả</th>
            <th>Giá bán (VNđồng)</th>
            <th>Thao tác</th>
        </tr>
        <c:forEach var="book" items="${listBook}">
            <tr>
                <td><c:out value="${book.title}" /></td>
                <td><c:out value="${book.author}" /></td>
                <td><c:out value="${book.price}" /></td>
                <td><a href="/Bookstore/edit?id=<c:out value='${book.id}' />">Sửa</a>
                    &nbsp;&nbsp;&nbsp;&nbsp; <a
                            href="/Bookstore/delete?id=<c:out value='${book.id}' />">Xóa</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/Bookstore/detail?id=<c:out value='${book.id}' />">Chi tiết</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
<jsp:include page="resource/footer.jsp"></jsp:include>
</html>