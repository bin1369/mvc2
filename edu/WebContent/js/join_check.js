/**
 * 아이디, 비번, 비번확인, 이메일을 유효하게 입력하고 있는지 체크 
 */

var join =
{
	common: {
		empty: { code: 'invalid', desc: '입력하세요' },
		space: { code: 'invalid', desc: '공백없이 입력하세요' },
		min: { code: 'invalid', desc: '최소 5자 이상 입력하세요' },
		max: { code: 'invalid', desc: '최대 10자 이하 입력하세요' }
	},
	
	tag_status: function( tag ){
		var name = tag.attr('name');
		if( name=='id' )  return this.id_status( tag.val() );
		else if( name=='pw' )	return this.pw_status( tag.val() );
		else if( name=='pw_ck' )	return this.pw_ck_status( tag.val() );
		else if( name=='email' )	return this.email_status( tag.val() );
	},
	
	email: {
		valid: { code: 'valid', desc: '유효한 이메일입니다' },
		invalid: { code: 'invalid', desc: '유효하지 않은 이메일입니다'}
	},
	email_status: function( email ){
		var reg = /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/i;
//		/[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i
		if( email=='' )		return this.common.empty;
		else if( email.match(this.space) )	return this.common.space;
		else if ( reg.test(email) ) 		return this.email.valid;
		else				return this.email.invalid;
	},
	
	pw_ck_status: function( pw_ck ){
		if( pw_ck=='' )			return this.common.empty;
		else if( pw_ck==$('[name=pw]').val() )	return this.pw.equal;
		else					return this.pw.notEqual;
	},
	
	pw: {
		valid: { code:'valid', desc:'사용가능한 비밀번호입니다' },
		invalid: { code: 'invalid', desc: '비밀번호는 영문대/소문자,숫자만 입력하세요'},
		lack: { code: 'invalid', desc: '영문대/소문자,숫자를 모두 포함해야 합니다' },
		equal: { code:'valid', desc: '비밀번호가 일치합니다' },
		notEqual: { code:'invalid', desc: '비밀번호가 일치하지 않습니다' }
	},
	pw_status: function( pw ){
		var reg = /[^a-zA-Z0-9]/g;
		var upper = /[A-Z]/g, lower=/[a-z]/g, digit = /[0-9]/g;
		if( pw=='' )		return this.common.empty;
		else if( pw.match(this.space) )	return this.common.space;
		else if( reg.test(pw) )			return this.pw.invalid;
		else if( pw.length < 5 )		return this.common.min;
		else if( pw.length > 10 )		return this.common.max;
		else if( !upper.test(pw) || !lower.test(pw) 
						|| !digit.test(pw) )		return this.pw.lack;
		else							return this.pw.valid;	
	},
	
	id: {
		valid: { code:'valid', desc: '아이디 중복확인하세요'},
		invalid: { code: 'invalid', desc: '아이디는 영문 소문자, 숫자만 입력하세요' },
		usable: { code:'valid', desc: '사용가능한 아이디입니다' },
		unusable: { code:'invalid', desc: '이미 사용중인 아이디입니다' },
	},
	id_usable: function( usable ){
		if( usable )	return this.id.usable;
		else			return this.id.unusable;
	},
	id_status: function( id ){
		var reg = /[^a-z0-9]/g;
		if( id=='' ) 					return this.common.empty;
		else if( id.match(this.space) ) return this.common.space;
		else if( reg.test(id) )			return this.id.invalid;
		else if( id.length < 5 )		return this.common.min;
		else if( id.length > 10 )		return this.common.max;
		else 							return this.id.valid;
	}, 
	space:  /\s/g
}










