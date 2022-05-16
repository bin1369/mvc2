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
<jsp:include page="/include/header.jsp"/>
<h3>공지글목록</h3>
<form method='post' action='list.no'>
<div class='w-px1200 list-top'>
	<ul>
		<li><select name='search' class='w-px90'>
			<option value='all' ${page.search eq 'all' ? 'selected' : ''}>전체</option>
			<option value='title' ${page.search eq 'title' ? 'selected' : ''}>제목</option>
			<option value='content'  <c:if test="${page.search eq 'content'}">selected</c:if> >내용</option>
			<option value='writer' ${page.search eq 'writer' ? 'selected' : ''}>작성자</option>
			</select>
		</li>
		<li>
			<input type='text' name='keyword' value='${page.keyword}' class='w-px300'>
		</li>
		<li><a class='btn-fill' onclick="$('form').submit()">검색</a></li>
	</ul>
	<ul>
		<c:if test='${loginInfo.admin eq "Y" }'>
		<li><a class='btn-fill' href='new.no'>글쓰기</a></li>
		</c:if>
	</ul>
</div>
<input type='hidden' name='curPage' value='1'>
</form>

<table class='w-px1200 tb_list'>
<thead>
	<tr><th class='w-px80'>번호</th>
		<th>제목</th>
		<th class='w-px120'>작성자</th>
		<th class='w-px120'>작성일자</th>
		<th class='w-px80'>첨부파일</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${page.list}" var="dto">
	<tr><td>${dto.no}</td>
		<td class='text-left'>
			<c:forEach var="i" begin="1" end="${dto.indent}">
			${ i eq dto.indent ? '<i class="font-b far fa-comment-dots"></i>' 
								: '&nbsp;&nbsp;&nbsp;' }
			</c:forEach>
			<a href='detail.no?id=${dto.id}&keyword=${page.keyword}&search=${page.search}&curPage=${page.curPage}'>${dto.title}</a>
		</td>
		<td>${dto.name}</td>
		<td>${dto.writedate}</td>
		<td>${ empty dto.filename ? '' : '<i class="font-g fas fa-paperclip"></i>' }</td>
	</tr>
	</c:forEach>
</tbody>
</table>

<div class='btnSet'>
<jsp:include page="/include/page.jsp"/>
</div>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>




