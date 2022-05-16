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
<h3>공지글변경</h3>
<form method='post' action='update.no' enctype='multipart/form-data'>
<table class='w-px1000'>
<tr><th class='w-px120'>제목</th>
	<td><input type='text' name='title' class='chk' title='제목' value='${dto.title}'></td>
</tr>
<tr><th>내용</th>
	<td><textarea name='content' title='내용' class='chk'>${dto.content}</textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td class='text-left'>
		<label>
			<input type='file' name='file' id='attach-file'>
			<a><i class='font-b fas fa-upload'></i></a>
		</label>
		<span id='file-name'>${dto.filename}</span>
		<a id='delete-file' 
			style='display: ${empty dto.filename ? "none" : "inline"}'><i class='font-r far fa-trash-alt'></i></a>
	</td>
</tr>
</table>
<input type='hidden' name='id' value='${dto.id}'>
<input type='hidden' name='filename'>
<input type='hidden' name='curPage' value='${param.curPage}'>
<input type='hidden' name='search' value='${param.search}'>
<input type='hidden' name='keyword' value='${param.keyword}'>
</form>
<div class='btnSet'>
	<a class='btn-fill' 
	onclick='$("[name=filename]").val( $("#file-name").text() ); $("form").submit()'>저장</a>
	<a class='btn-empty' href='javascript:history.go(-1)'>취소</a>
</div>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>