<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
<<<<<<< HEAD
	<link href="<%=conPath %>/css/ex.css" rel="stylesheet" type="text/css">
=======
>>>>>>> 634167439382932c285b4c7ab32bb31f61de1cbf
</head>
<body>
	<h1>쿠키 리스트</h1>
	<%
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for(int idx=0; idx<cookies.length; idx++) {
				String name = cookies[idx].getName();
				String value = cookies[idx].getValue(); // idx번째 쿠키 값
				out.println("<h2>" + idx +"번째 쿠키 이름:" + name + ", 값:" + value+"</h2>");
			}
		}else {
			out.println("<h2>해당서버로부터 생성된 쿠키가 없습니다</h2>");
		}

		if(cookies !=null) {
		for(int idx=0; idx<cookies.length; idx++) {
			String name = cookies[idx].getName(); // idx번째 쿠키의 이름
			String value = cookies[idx].getValue(); // idx번째 쿠키 값
			out.println("<h2>" + idx + "번째 쿠키 이름 : " + name+ ", 값 : " + value + "</h2>");
		}
	}else {
		out.println("<h2>해당서버로부터 생성된 쿠키가 없어욘</h2>");
	}

	%>
	<hr>
	<button onclick="history.go(-1)">이전페이지</button>
	<button onclick="location.href='welcome.jsp'">메인페이지</button>
</body>
</html>