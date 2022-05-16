package member.command;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Command;

public class NaverLogin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
//		https://nid.naver.com/oauth2.0/authorize
//		?response_type=code
//		&client_id=CLIENT_ID
//		&state=STATE_STRING
//		&redirect_uri=CALLBACK_URL
		String client_id = "iyDf6t9F2FONXW4yK_MB";
		//String callback = "http://localhost/edu/navercallback.mb";
		String callbackUrl 
		= request.getRequestURL().substring(0
				, request.getRequestURL().length() - request.getServletPath().length());
		
		//url > http://localhost/edu/login.mb 
		//uri > /edu/login.mb
		
		
		//랜덤한 문자데이터 생성
		String state = UUID.randomUUID().toString();
		request.getSession().setAttribute("state", state);
		
		StringBuffer url = new StringBuffer(
			"https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(client_id);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append(callbackUrl + "/navercallback.mb");
		
		request.setAttribute("url", url.toString());
	}

}







