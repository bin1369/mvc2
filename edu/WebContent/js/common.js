/**
 *  전 어플리케이션에 적용할 공통 js
 */

$(function(){
	$('input[type=text]').attr('autocomplete', 'off');
	
	/*form태그 서브밋 전에 필수입력항목 입력여부 체크*/
	$('form').on('submit', function(e){
		if( ! emptyCheck() ) 	e.preventDefault();
	});
	
	/*파일 첨부하기 위한 파일선택*/
	$('#attach-file').on('change', function(){
		console.log( this.files[0] )
		var attached = this.files[0];
		if( attached ){
			$('#file-name').text( attached.name );
			$('#delete-file').css('display', 'inline');
			
			//선택한 파일이 이미지파일인 경우 미리보기되게 img 태그를 넣는다
			if( $('#preview').length > 0 ){
				if( isImage( attached.name ) ){
					$('#preview').html( '<img id="preview-img">' );
					
					var reader = new FileReader();
					reader.onload = function(e){
						$('#preview-img').attr('src', e.target.result);
					}
					reader.readAsDataURL( attached );
				}else{
					$('#preview').html( '' );
				}
			}
		}else{
			$('#file-name').text('');
			$('#delete-file').css('display', 'none')
		}
	});
	
	/*첨부파일로 선택한 파일 첨부하지 않도록*/
	$('#delete-file').on('click', function(){
		$('#file-name').text('');	/*파일명 삭제*/
		$('#attach-file').val(''); /*file태그 초기화*/
		$('#delete-file').css('display', 'none'); /*파일삭제이미지 안보이게*/
		if( $('#preview').length > 0 ) $('#preview').html( '' );
	});
	
});

//파일이 이미지파일인지 확인
function isImage( filename ){
	// abc.txt, abc.hwp, abc.jpg, abc.PNG
	var ext = filename.substring( filename.lastIndexOf('.')+1 ).toLowerCase();
	var imgs = [ 'jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp' ];
	if( imgs.indexOf( ext ) > -1 ) return true;
	else return false;
}


//필수입력항목 입력여부 체크
function emptyCheck(){
	var ok = true;
	$('.chk').each(function(){
		if( $(this).val()=='' ){
			alert( $(this).attr('title') + ' 입력하세요' );
			$(this).focus();
			ok = false;
			return ok;
		}
	});
	return ok;
}