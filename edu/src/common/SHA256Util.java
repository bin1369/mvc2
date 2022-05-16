package common;

import java.security.MessageDigest;
import java.security.SecureRandom;


public class SHA256Util {
	//솔트 생성
	public String generateSalt() {
		//암호화 랜덤 수 생성 
		SecureRandom random = new SecureRandom();
		byte salt[] = new byte[24];
		random.nextBytes(salt);
		
		//16진수 변환하여 한 개의 문자열로 만든다
		StringBuffer buf = new StringBuffer();
		for( byte b : salt ) {
			buf.append( String.format("%02x", b) );
		}
		return buf.toString();
	}
	
	//솔트를 사용한 비밀번호 암호화
	public String getEncrypt(String pw, String salt) {
		String salt_pw = pw + salt;
		try {
			//암호화 해쉬 함수: MessagDigest		
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update( salt_pw.getBytes() );
			byte[] digest = md.digest();
			
			StringBuffer buf = new StringBuffer();
			for( byte b : digest ) {
				buf.append( String.format("%02x", b));
			}
			salt_pw = buf.toString();
			
		}catch(Exception e) {
		}
		return salt_pw;
	}
	
}







