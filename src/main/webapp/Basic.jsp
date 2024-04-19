<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang JSP thuần HTML</title>
</head>
<body>

	<%
	//Thiết lập tập ký tự cần lấy
	request.setCharacterEncoding("utf-8");
	// Tìm tham số lấy giá trị
	String name = request.getParameter("txtName");
	if (name != null) {
		out.append("Xin chào: ").append(name);
	} else {
	%>

	<form action="Basic.jsp" method="post">
		Nhập vào tên của bạn: <input type="text" name="txtName">
		<button type="submit">Gửi</button>

	</form>
	<%
	}
	%>
</body>
</html>