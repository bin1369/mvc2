<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>신규공지글</h3>

<form method='post' enctype='multipart/form-data' action='insert.no'>
<input type='hidden' name='writer' value='${loginInfo.id}'>
<table class='w-px1000'>
<tr><th class='w-px120'>제목</th>
	<td><input type='text' class='chk' title='제목' name='title'></td>
</tr>
<tr><th>작성자</th><td>${loginInfo.name}</td></tr>
<tr><th>내용</th>
	<td><textarea name='content' title='내용' class='chk'></textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td class='text-left'>
		<label>
			<input type='file' name='file' id='attach-file'>
			<i class="font-b fas fa-upload"></i>
		</label>
		<span id='file-name'></span>
		<a id='delete-file'><i class="font-r far fa-trash-alt"></i></a>
	</td>
</tr>
</table>
</form>
<div class='btnSet'>
	<a class='btn-fill' onclick='$("form").submit()'>저장</a>
	<a class='btn-empty' href='list.no'>취소</a>
</div>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>









