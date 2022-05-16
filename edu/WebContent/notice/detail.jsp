<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>공지글 안내</h3>
<table class='w-px1000'>
<tr><th class='w-px120'>제목</th>
	<td class='text-left' colspan='5'>${dto.title}</td>
</tr>
<tr><th>작성자</th>
	<td>${dto.name}</td>
	<th class='w-px120'>작성일자</th>
	<td class='w-px120'>${dto.writedate}</td>
	<th class='w-px80'>조회수</th>
	<td class='w-px80'>${dto.readcnt}</td>
</tr>
<tr><th>내용</th>
	<td class='text-left' colspan='5' 
			style='padding-top:10px;padding-bottom:10px;'>
		${fn: replace(dto.content, crlf, '<br>') }</td>
</tr>
<tr><th>첨부파일</th>
	<td class='text-left' colspan='5'>${dto.filename }
	<c:if test='${not empty dto.filename}'>
	<a href='download.no?id=${dto.id}'><i class="font-b fas fa-download"></i></a>
	</c:if>
	</td>
</tr>
</table>
<div class='btnSet'>
	<a class='btn-fill' href='list.no?curPage=${param.curPage}&keyword=${param.keyword}&search=${param.search}'>목록으로</a>
	<!-- 변경/삭제 는 관리자만 권한을 갖는다 -->
	<c:if test='${loginInfo.admin eq "Y"}'>
	<a class='btn-fill' href='modify.no?id=${dto.id}&curPage=${param.curPage}&keyword=${param.keyword}&search=${param.search}'>정보변경</a>
	<a class='btn-fill' 
	onclick="if( confirm('정말 삭제?') ){ href='delete.no?id=${dto.id}&curPage=${param.curPage}&keyword=${param.keyword}&search=${param.search}' }" >정보삭제</a>
	</c:if>
	<!-- 로그인된 경우 답글쓰기 가능 -->
	<c:if test='${!empty loginInfo}'>
	<a class='btn-fill' href='reply.no?id=${dto.id}&curPage=${param.curPage}&keyword=${param.keyword}&search=${param.search}'>답글쓰기</a>
	</c:if>
</div>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>








