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
	<h3>방명록 글쓰기</h3>

	<form action='insert.bo' method='post' enctype='multipart/form-data'>
		<table class='w-px1000'>
			<tr>
				<th class='w-px120'>제목</th>
				<td><input type='text' name='title' title='제목' class='chk'></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name='content' title='내용' class='chk'></textarea></td>
			</tr>
			<tr>
				<th>파일첨부</th>
				<td class='text-left'>
					<div class='img-box'>
						<label> <input type='file' name='file' id='attach-file'>
							<a><i class='font-b fas fa-upload'></i></a>
						</label> <span id='file-name'></span> <span id='preview'></span> <a
							id='delete-file'><i class="font-r far fa-trash-alt"></i></a>
					</div>
				</td>
			</tr>
		</table>
		<input type='hidden' name='writer' value="${loginInfo.id}">
	</form>

	<div class='btnSet'>
		<a class='btn-fill' onclick='$("form").submit()'>저장</a> <a
			class='btn-empty' onclick='history.go(-1)'>취소</a>
	</div>
	<jsp:include page="/include/footer.jsp" />
</body>
</html>





