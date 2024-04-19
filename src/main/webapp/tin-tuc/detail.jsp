<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../header.jsp" flush="true"></jsp:include>
	<main id="main">
		<%
			String detail = (String)session.getAttribute("detail");
			if(detail!=null){
				out.append(detail);
			}
		%>
	</main>
<!-- End #main -->
<jsp:include page="../footer.jsp" flush="true"></jsp:include>