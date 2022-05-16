<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/include/header.jsp" />
	<h3>Q & A 상세</h3>
	
	<!-- 상세보기 -->
	<table class='w-px1000'>
		<tr>
			<th class='w-px120'>제목</th>
			<td colspan='3' class='text-left'>${question.title }</td>
			<th class='w-px80'>답변여부</th>
			<td class='w-px80'>${question.complete }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td class='text-left'>${question.writer }</td>
			<th class='w-px120'>작성일자</th>
			<td class='w-px120'><%-- ${ question.writedate } --%></td>
			<th class='w-px80'>조회수</th>
			<td class='w-px80'>${ question.readcnt }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan='5' class='text-left'>${question.content}</td>
		</tr>
	</table>
	
	<!-- 버튼 -->
	<!-- 댓글 -->
	
	<jsp:include page="/include/footer.jsp" />
</body>
</html>