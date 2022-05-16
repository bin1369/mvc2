<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table td { text-align: left }
[name=address] { width:calc(100% - 24px) }
.addr { height: 120px }
.addr input:not(:last-child) { margin-bottom: 2px }
.ui-datepicker table tr { height: inherit; }
.desc { height: 66px; }
.valid { font-size:13px; color: #008000; font-weight: bold; }
.invalid { font-size:13px; color: #bf0000; font-weight: bold; }
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>회원가입</h3>
<p class='w-px500 text-right' style='margin:15px auto;'>* 는 필수입력항목입니다</p>
<form method='post' action='join.mb'>
<table class='w-px500'>
<tr><th class='w-px120'>* 성명</th>
	<td><input type='text' name='name'></td>
</tr>
<tr><th>* 아이디</th>
	<td class='desc'><input type='text' name='id' class='chk'>
		<a class='btn-fill-s' id='btn-id'>아이디중복확인</a><br>
		<div class='valid'>아이디를 입력하세요(영문소문자,숫자만 가능)</div>
	</td>
</tr>
<tr><th>* 비밀번호</th>
	<td class='desc'><input type='password' name='pw' class='chk'><br>
		<div class='valid'>비밀번호를 입력하세요(영문대/소문자,숫자를 모두 포함)</div>
	</td>
</tr>
<tr><th>* 비밀번호확인</th>
	<td class='desc'><input type='password' name='pw_ck' class='chk'><br>
		<div class='valid'>비밀번호를 다시 입력하세요</div>
	</td>
</tr>
<tr><th>* 성별</th>
	<td><label><input type='radio' name='gender' value='남'>남</label>
		<label><input type='radio' name='gender' checked value='여'>여</label>
	</td>
</tr>
<tr><th>* 이메일</th>
	<td class='desc'><input type='text' name='email' class='chk'><br>
		<div class='valid'>이메일을 입력하세요</div>
	</td>
</tr>
<tr><th>생년월일</th>
	<td><input type='text' name='birth' readonly>
		<a id='delete'><i class="font-r fas fa-times"></i></a>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><input type='text' name='phone' class='w-px30'>
		- <input type='text' name='phone' class='w-px40'>
		- <input type='text' name='phone' class='w-px40'>
	</td>
</tr>
<tr><th>주소</th>
	<td class='addr'><a class='btn-fill-s' onclick="daum_post()">우편번호찾기</a>
		<input type='text' name='post' class='w-px50' readonly><br>
		<input type='text' name='address' readonly>
		<input type='text' name='address'>
	</td>
</tr>
</table>
</form>
<div class='btnSet'>
	<a class='btn-fill' href='javascript:go_join()'>회원가입</a>
	<a class='btn-empty' href='javascript:history.go(-1)'>취소</a>
</div>


<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script src="js/join_check.js?<%=new java.util.Date().getTime()%>"></script>
<script>
function go_join(){
	if( $('[name=name]').val()=='' ){
		alert('성명을 입력하세요!');
		$('[name=name]').focus();
		return;
	}
	
	//아이디의 경우 
	var $id = $('[name=id]');
	//이미 중복확인 하여 사용중인 아이디라 사용불가인 경우
	if( $id.hasClass('chked') ){
		if( $id.siblings('div').hasClass('invalid') ){
			alert('회원가입 불가\n' +  join.id.unusable.desc );
			$id.focus();
			return;
		}
	}else{
	//중복확인하지 않은 경우
		if( ! item_check( $('[name=id]') ) ) return;
		else{
			alert('회원가입 불가\n' + join.id.valid.desc);
			$id.focus();
			return;
		}
	}
	
	
	if( ! item_check( $('[name=pw]') ) ) return;
	if( ! item_check( $('[name=pw_ck]') ) ) return;
	if( ! item_check( $('[name=email]') ) ) return;
	
	$("form").submit();
}

function item_check( tag ){
	var data = join.tag_status( tag );
	if( data.code=='invalid' ){
		alert('회원가입 불가\n' + data.desc);
		tag.focus();
		return false;
	}else
		return true;
}


$('#btn-id').on('click', function(){
	id_check();
});

//아이디 중복체크: DB 에 입력한 아이디가 존재하는지 판단 
function id_check(){
	var $id = $('[name=id]');
	if( $id.hasClass('chked') ) return; 
		
	var data = join.tag_status( $id );
	if( data.code=='invalid' ){
		alert( '아이디 중복확인 불필요\n' + data.desc );
		$id.focus();
		return;
	}

	//화면바뀜없이 처리를 하려면 ajax처리
	$.ajax({
		url: 'id_check.mb',
		data: { id: $id.val() },
		success: function( response ){
			$id.addClass('chked');  //중복확인했음을 지정
			response = join.id_usable( JSON.parse(response) );
			$id.siblings('div').text( response.desc )
						.removeClass().addClass( response.code );
				
		},error: function(req, text){
			alert(text + ':' + req.status);
		}
	});
	
	
}

$('.chk').on('keyup', function(e){
	//아이디인 경우  엔터시 중복확인처리하기
	if( $(this).attr('name')=='id' ){
		if( e.keyCode==13 ){
			id_check();
		}else{
			$(this).removeClass('chked');
		}
	}
	
	var data = join.tag_status( $(this) );
	$(this).siblings('div').text( data.desc )
				.removeClass().addClass( data.code );
	
});

$('[name=birth]').change(function(){
	$('#delete').css('display', 'inline');
});

$('#delete').click(function(){
	$('[name=birth]').val('');
	$('#delete').css('display', 'none');
});

function daum_post(){
    new daum.Postcode({
        oncomplete: function(data) {
        	$('[name=post]').val( data.zonecode ); //우편번호
        	//도로명(R)주소/지번(J)주소 선택에 따른 주소 가져오기
        	var address
        	= data.userSelectedType == 'R' ? data.roadAddress : data.jibunAddress;
        	
        	if( data.buildingName!='' ) address += ' ('+ data.buildingName + ')';
        	$('[name=address]').eq(0).val( address );
        }
    }).open();
}

$(function(){
	//만13세부터 생년월일 선택 가능하게 제한
	var today = new Date();
	today.setFullYear( today.getFullYear()-13 );
	today.setDate( today.getDate()-1 );
	var endDay = today;

	$('[name=birth]').datepicker({
		showMonthAfterYear: true
		, dateFormat: 'yy-mm-dd'
		, changeYear: true
		, changeMonth: true
		, monthNamesShort: [ '1월', '2월', '3월', '4월'
			, '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ]
		, dayNamesMin: [ '일', '월', '화', '수', '목', '금', '토' ]
		//, beforeShowDay: before
		, maxDate: endDay
	});
});


//특정날짜 이후의 날짜는 보이지 않게
function before(date){
	if( date > new Date() ) return [false];
	else return [true];
}

</script>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>







