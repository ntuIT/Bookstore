<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Ứng dụng quản lý kho sách</title>
</head>
<body>
<jsp:include page="resource/header.jsp"></jsp:include>
<hr>
<div align="center">
    <h2>Chi tiết sách</h2>

    <b>Mã sách</b>&nbsp;&nbsp;&nbsp;<c:out value="${book.id}" /> <br>
    <b>Tên sách</b>&nbsp;&nbsp;&nbsp;<c:out value="${book.title}" />  </br>
    <b>Tác giả</b>&nbsp;&nbsp;&nbsp;<c:out value="${book.author}" /> </br>
    <b>Giá bán</b>&nbsp;&nbsp;&nbsp;<c:out value="${book.price}" /> </br>

    <a href="/Bookstore/edit?id=<c:out value='${book.id}' />">Sửa</a>
    &nbsp;&nbsp;&nbsp;&nbsp; <a
        href="/Bookstore/delete?id=<c:out value='${book.id}' />">Xóa</a>
</div>
</body>
</html>