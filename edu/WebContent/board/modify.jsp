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
<h3>방명록 변경</h3>
<form method="post" action='update.bo' enctype='multipart/form-data'>
<table class='w-px1200'>
<tr><th class='w-px120'>제목</th>
	<td><input type='text' name='title' title='제목' class='chk' value='${dto.title}'></td>
</tr>
<tr><th>내용</th>
	<td><textarea name='content' title='내용' class='chk'>${dto.content}</textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td class='text-left'>
	<div class='img-box'>
		<label>
			<input type='file' name='file' id='attach-file'>
			<a><i class='font-b fas fa-upload'></i></a>
		</label>
		<span id='file-name'>${dto.filename}</span>
		<span id='preview'></span>
		<a id='delete-file' 
		style="display:${empty dto.filename ? 'none' : 'inline'}"><i class='font-r far fa-trash-alt'></i></a>
	</div>
	</td>
</tr>
</table>
<input type='hidden' name='id' value='${dto.id}'>
<input type='hidden' name='filename' id="filename">
<input type="hidden" name="curPage" value="${page.curPage}">
<input type="hidden" name="search" value="${page.search}">
<input type="hidden" name="keyword" value="${page.keyword}">
<input type="hidden" name="pageList" value="${page.pageList}">
<input type="hidden" name="viewType" value="${page.viewType}">
</form>

<div class='btnSet'>
	<a class='btn-fill' 
	onclick='$("#filename").val( $("#file-name").text() );   $("form").submit()'>저장</a>
	<a class='btn-empty' onclick='history.go(-1)'>취소</a>
</div>

<script>
//첨부파일이 있고, 그 파일이 이미지파일이면 미리보기되게
if( JSON.parse('${!empty dto.filename}') ){
	if(  isImage('${dto.filename}')  ){
		$('#preview').html( '<img src="${dto.filepath}" id="preview-img">' );
	}
}
</script>


<jsp:include page="/include/footer.jsp"/>
</body>
</html>