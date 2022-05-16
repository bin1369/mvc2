<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fm"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function go_detail(writer){
		if ( (writer == '${loginInfo.id}')|| ('Y' == '${loginInfo.admin}') ) {
			location.href = 'detail.qa?id='+writer;
			
		} else {
			alert('작성자만 확인 할 수 있습니다.');
		}
		
	}
</script>
</head>
<body>
	<jsp:include page="/include/header.jsp" />

	<h3>Q & A 목록</h3>
	<!-- 글쓰기 버튼 -->
	<div class='list-top w-px1200'>
		<ul>
			<c:if test='${!empty loginInfo}'>
				<li><a class='btn-fill' href='new.qa'>글쓰기</a></li>
			</c:if>
		</ul>
	</div>

	<!-- 질문 목록 -->
	<table class='w-px1200 tb_list'>
		<tr>
			<th class='w-px90'>번호</th>
			<th>제목</th>
			<th class='w-px120'>작성자</th>
			<th class='w-px120'>작성일자</th>
			<th class='w-px80'>답변</th>
		</tr>
		<c:forEach items="${question_list}" var="question">
			<tr>
				<td>${question.idx}</td>
				<%-- 				<td class='text-left'><a href='detail.qa?id='$(question.id)>${question.title}</a></td> --%>
				<td class='text-left'><a
					onclick="go_detail('${ question.writer }','${loginInfo.id}' );">${question.title}</a></td>
					
				<td>${ question.writer }</td>
				<td>${ question.writeDate }</td>
				<td>${ question.complete ? '작성완료': '대기중'}</td>
			</tr>
		</c:forEach>
	</table>

	<jsp:include page="/include/footer.jsp" />

</body>
</html>