package common;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class EmailSend {
	public void send(String email, String name, HttpServletRequest request) {
		HtmlEmail mail = new HtmlEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true);
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("아이디", "비번");
		mail.setSSLOnConnect(true);  //로그인버튼 클릭
		
		try {
		mail.setFrom("아이디@naver.com", "edu관리자"); //메일 송신자 지정
		mail.addTo(email, name); //메일 수신자 지정
		
		mail.setSubject("edu 스프링 5회차 과정");
		
		StringBuilder msg = new StringBuilder();
		msg.append("<html>");
		msg.append("<body>");
		msg.append("<h3><a href='http://naver.com'>네이버</a></h3>");
		msg.append("<a href='https://n.news.naver.com/article/081/0003245638'><img width='60%' src='https://imgnews.pstatic.net/image/081/2022/01/18/0003245638_001_20220118142704484.jpg?type=w647'></a>");
		msg.append("<hr>");
		msg.append("<div>과정에 가입하심을 축하합니다</div>");
		msg.append("</body>");
		msg.append("</html>");
		mail.setHtmlMsg(msg.toString());
		
		//파일첨부
		EmailAttachment file = new EmailAttachment();
		file.setURL( new URL("https://imgnews.pstatic.net/image/081/2022/01/18/0003245539_001_20220118050821144.jpg?type=w647"));
		mail.attach(file);
		
		
		file = new EmailAttachment();
		file.setPath( request.getServletContext().getRealPath("css/common.css") );
		mail.attach(file);
		
		mail.send(); //메일보내기 클릭
		
		}catch(Exception e) {
		}finally {
		}
	}
}
