<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="conPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${conPath}/css/style.css" rel="stylesheet">
<style>
#content_form {
	height: 450px;
	margin: 50px auto 0px;
}
</style>
</head>
<body>
	<jsp:include page="../main/header.jsp" />
	<div id="content_form">
		<table>
			<caption>${board.fid }번글상세보기</caption>
			<tr>
				<th>작성자</th>
				<td>${board.mname }(${board.mid })님</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${board.ftitle }</td>
			</tr>
			<tr>
				<th>본문</th>
				<td>${board.fcontent }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${board.fhit }</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td><c:if test="${not empty board.ffileName }">
						<a href="${conPath }/fileBoardUp/${board.ffileName}"
							target="_blank">${board.ffileName}</a>
					</c:if> <c:if test="${empty board.ffileName }">
						첨부파일 없음
					</c:if></td>
			</tr>
			<tr>
				<td colspan="2"><c:if test="${member.mid eq board.mid }">
						<button
							onclick="location='${conPath}/boardModifyView.do?fid=${board.fid }&pageNum=${param.pageNum }'">수정
						</button>
					</c:if> <c:if test="${member.mid eq board.mid or not empty admin}">
						<button
							onclick="location='${conPath}/boardDelete.do?fid=${board.fid }&pageNum=${param.pageNum }'">삭제
						</button>
					</c:if> <c:if test="${not empty member }">
						<button
							onclick="location='${conPath}/boardReplyView.do?fid=${board.fid }&pageNum=${param.pageNum }'">답변</button>
					</c:if>
					<button
						onclick="location='${conPath}/boardList.do?pageNum=${param.pageNum }'">목록</button>
			</tr>
		</table>
	</div>
	<jsp:include page="../main/footer.jsp" />
</body>
</html>