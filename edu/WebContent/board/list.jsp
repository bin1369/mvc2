<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/include/header.jsp" />
	<h3>방명록 목록</h3>

	<form method='post' action='list.bo'>
		<div class='list-top w-px1200'>
			<ul>
				<li><select name='search' class='w-px90'>
						<option value='all' ${page.search eq 'all' ? 'selected' : ''}>전체</option>
						<option value='title' ${page.search eq 'title' ? 'selected' : ''}>제목</option>
						<option value='content'
							${page.search eq 'content' ? 'selected' : ''}>내용</option>
						<option value='writer'
							${page.search eq 'writer' ? 'selected' : ''}>작성자</option>
				</select></li>
				<li><input type='text' name='keyword' value='${page.keyword}'
					class='w-px300'></li>
				<li><a class='btn-fill' onclick="$('form').submit()">검색</a></li>
			</ul>
			<ul>
				<li><select name='pageList' class='w-px90'
					onchange="$('form').submit()">
						<option value='10' ${page.pageList eq 10 ? 'selected' : ''}>10개씩</option>
						<option value='15' ${page.pageList eq 15 ? 'selected' : ''}>15개씩</option>
						<option value='20' ${page.pageList eq 20 ? 'selected' : ''}>20개씩</option>
						<option value='25' ${page.pageList eq 25 ? 'selected' : ''}>25개씩</option>
						<option value='30' ${page.pageList eq 30 ? 'selected' : ''}>30개씩</option>
				</select></li>
				<li><select name='viewType' class='w-px110'
					onchange="$('form').submit()">
						<option value='list' ${page.viewType eq 'list' ? 'selected' : ''}>리스트보기</option>
						<option value='grid' ${page.viewType eq 'grid' ? 'selected' : ''}>그리드보기</option>
				</select></li>
				<!-- 로그인한 경우 글쓰기 가능 -->
				<c:if test="${!empty loginInfo}">
					<li><a class='btn-fill' href='new.bo'>글쓰기</a></li>
				</c:if>
			</ul>
		</div>
		<input type='hidden' name='curPage' value='1'> <input
			type='hidden' name='id'>
	</form>

	<c:if test="${page.viewType eq 'grid'}">
		<ul class='grid'>
			<c:forEach items="${page.list}" var="dto">
				<li>
					<div>
						<a onclick='go_detail(${dto.id})'>${dto.title}</a>
					</div>
					<div>${dto.name}</div>
					<div>${dto.writedate}<span style='float: right;'>
							${empty dto.filename ? '' 
								: '<i class="font-g fas fa-paperclip"></i>'}</span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:if>

	<c:if test="${page.viewType eq 'list'}">
		<table class='w-px1200 tb_list'>
			<tr>
				<th class='w-px90'>번호</th>
				<th>제목</th>
				<th class='w-px120'>작성자</th>
				<th class='w-px120'>작성일자</th>
				<th class='w-px80'>첨부파일</th>
			</tr>
			<c:forEach items="${page.list}" var="dto">
				<tr>
					<td>${dto.no}</td>
					<td class='text-left'><a onclick='go_detail(${dto.id})'>${dto.title}</a></td>
					<td>${dto.name}</td>
					<td>${dto.writedate}</td>
					<td>${empty dto.filename ? '' 
			: '<i class="font-g fas fa-paperclip"></i>'}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<script>
function go_detail(id){
	$('[name=id]').val( id );
	$('[name=curPage]').val(${page.curPage});
	$('form').attr('action', 'detail.bo');
	$('form').submit();
}
</script>

	<div class='btnSet'>
		<jsp:include page="/include/page.jsp" />
	</div>

	<jsp:include page="/include/footer.jsp" />
</body>
</html>